package org.fpm.di.example;

import org.fpm.di.Container;
import org.fpm.di.Environment;
import org.fpm.di.EnvironmentImpl;
import org.fpm.di.example.tescclasses.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerTest {

    private Container container;

    @Before
    public void setUp() {
        Environment env = new EnvironmentImpl();
        container = env.configure(new ContainerTestConfiguration());
    }

    @Test
    public void shouldGetComponentByInterfaceType() {
        Interface component = container.getComponent(Interface.class);
        assertTrue(component instanceof InterfaceImpl);
    }

    @Test
    public void shouldGetComponentByAbstractClassType() {
        AbstractClass component = container.getComponent(AbstractClass.class);
        assertTrue(component instanceof AbstractClassImpl);
    }

    @Test
    public void shouldGetComponentByParentClassType() {
        ParentClass component = container.getComponent(ParentClass.class);
        assertTrue(component instanceof ChildClassSingleton);
    }

    @Test
    public void shouldGetComponentBySpecificType() {
        ChildClassSingleton component = container.getComponent(ChildClassSingleton.class);
        assertNotNull(component);
    }

    @Test
    public void shouldGetSingleton() {
        assertSame(container.getComponent(ChildClassSingleton.class), container.getComponent(ChildClassSingleton.class));
    }

    @Test
    public void shouldGetPrototype() {
        assertNotSame(container.getComponent(Interface.class), container.getComponent(Interface.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
            binder.bind(ParentClass.class, ChildClassSingleton.class);
            binder.bind(ChildClassSingleton.class);
         */
        final ChildClassSingleton childClassSingleton = container.getComponent(ChildClassSingleton.class);
        assertSame(container.getComponent(ParentClass.class), childClassSingleton);
        assertSame(container.getComponent(ChildClassSingleton.class), childClassSingleton);
    }

    @Test
    public void shouldInjectAllDependencies() {
        final Holder holder = container.getComponent(Holder.class);
        assertNotNull(holder.getAnInterface());
        assertNotNull(holder.getAbstractClass());
        assertNotNull(holder.getParentClass());
        assertNotNull(holder.getChildClassSingleton());
    }

    @Test
    public void shouldInjectSingleton() {
        final Holder holder = container.getComponent(Holder.class);
        assertSame(holder.getChildClassSingleton(), container.getComponent(ChildClassSingleton.class));
        assertSame(holder.getParentClass(), container.getComponent(ParentClass.class));
    }
}
