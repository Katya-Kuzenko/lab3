package org.fpm.di.example.tescclasses;

import javax.inject.Inject;

public class Holder {
    private Interface anInterface;
    private AbstractClass abstractClass;
    private ParentClass parentClass;
    private ChildClassSingleton childClassSingleton;

    @Inject
    public Holder(Interface anInterface, AbstractClass abstractClass, ParentClass parentClass, ChildClassSingleton childClassSingleton) {
        this.anInterface = anInterface;
        this.abstractClass = abstractClass;
        this.parentClass = parentClass;
        this.childClassSingleton = childClassSingleton;
    }

    public Interface getAnInterface() {
        return anInterface;
    }

    public AbstractClass getAbstractClass() {
        return abstractClass;
    }

    public ParentClass getParentClass() {
        return parentClass;
    }

    public ChildClassSingleton getChildClassSingleton() {
        return childClassSingleton;
    }
}
