package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;
import org.fpm.di.example.tescclasses.*;

public class ContainerTestConfiguration implements Configuration {
    @Override
    public void configure(Binder binder) {
        binder.bind(AbstractClass.class, AbstractClassImpl.class);
        binder.bind(Interface.class, new InterfaceImpl());
        binder.bind(ParentClass.class, ChildClassSingleton.class);
        binder.bind(ChildClassSingleton.class);

        binder.bind(Holder.class);
    }
}
