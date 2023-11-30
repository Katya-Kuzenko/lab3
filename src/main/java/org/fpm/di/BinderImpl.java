package org.fpm.di;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BinderImpl implements Binder {

    private Map<Class<?>, Class<?>> bindings = new HashMap<>();

    @Override
    public <T> void bind(Class<T> clazz) {
        validateSpecificClass(clazz);
        bindings.put(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        validateSpecificClass(implementation);
        bindings.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        bindings.put(clazz, instance.getClass());
    }

    private static <T> void validateSpecificClass(Class<T> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException("Expects specific class");
        }
    }

    public <T> Class<T> getSpecificClass(Class<T> clazz) {
        Class<T> specificClass = (Class<T>) bindings.get(clazz);
        if (specificClass == null) {
            throw new IllegalStateException("Binding not configured for type " + clazz.toString());
        }
        return specificClass;
    }
}
