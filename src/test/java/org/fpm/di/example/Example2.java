//package org.fpm.di.example;
//
//import com.google.inject.AbstractModule;
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import org.fpm.di.Container;
//import org.fpm.di.Environment;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertNotSame;
//import static org.junit.Assert.assertSame;
//
//class BasicModule extends AbstractModule {
//
//    @Override
//    protected void configure() {
//        bind(A.class).to(B.class);
//        binder().requireExplicitBindings();
//    }
//}
//
//public class Example2 {
//
//    private Container container;
//
//    public static void main(String[] args) {
//        Injector injector = Guice.createInjector(new BasicModule());
//        A comms = injector.getInstance(A.class);
//        System.out.println();
//    }
//}
