package com.qomolangma.frameworks.test.container;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Containers<A extends Annotation> {

    private static final Map<Class<?>, TestContainer<?>> CONTAINERS = new ConcurrentHashMap<>();

    private final ExtensionContext context;
    private final Class<A> type;
    private final Function<A, Class<?>[]> containers;

    public Containers(ExtensionContext context, Class<A> type, Function<A, Class<?>[]> containers) {
        this.context = context;
        this.type = type;
        this.containers = containers;
    }

    public void startContainer() {
        var annotation = AnnotationUtils.findAnnotation(this.context.getRequiredTestClass(), this.type);
        var containerClasses = List.of(annotation.map(this.containers).orElse(new Class[0]));
        var newContainerClasses = containerClasses.stream().filter(x -> !CONTAINERS.containsKey(x));
        newContainerClasses.parallel().forEach(this::startContainer);
    }

    private void startContainer(Class<?> containerClass) {
        var container = (TestContainer<?>) ReflectionUtils.newInstance(containerClass);
        container.start();
        CONTAINERS.put(containerClass, container);
    }

}
