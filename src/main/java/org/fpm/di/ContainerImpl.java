package org.fpm.di;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContainerImpl implements Container {

    private Map<Class<?>, Object> singletons = new HashMap<>();

    private BinderImpl binder;

    public ContainerImpl(BinderImpl binder) {
        this.binder = binder;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        Class<T> specificClass = binder.getSpecificClass(clazz);
        Singleton singletonAnnotation = specificClass.getAnnotation(Singleton.class);
        if (singletonAnnotation != null) {
            return (T) singletons.computeIfAbsent(specificClass, (key) -> createObject(specificClass));
        } else {
            return createObject(specificClass);
        }
    }

    private <T> T createObject(Class<T> specificClass) {
        Constructor<?>[] constructors = specificClass.getDeclaredConstructors();

        Constructor constructor = getInjectConstructor(constructors);

        if (constructor != null) {
            Parameter[] parameters = constructor.getParameters();
            Object[] arguments = Arrays.stream(parameters)
                    .map(Parameter::getType)
                    .map(this::getComponent)
                    .toArray();

            return invokeConstructorWithParameters(constructor, arguments);
        } else {
            return invokeDefaultConstructor(specificClass);
        }
    }

    private Constructor getInjectConstructor(Constructor<?>[] constructors) {
        Constructor constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            if (constructors[i].getAnnotation(Inject.class) != null) {
                constructor = constructors[i];
                break;
            }
        }
        return constructor;
    }

    private <T> T invokeConstructorWithParameters(Constructor constructor, Object[] arguments) {
        try {
            return (T) constructor.newInstance(arguments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T invokeDefaultConstructor(Class<T> specificClass) {
        try {
            return specificClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No default constructor found", e);
        }
    }
}
