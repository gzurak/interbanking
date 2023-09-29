package org.zuratech.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Trace {
    public static final String NULL = "";
    String metricName() default NULL;
    boolean dispatcher() default false;
    String tracerFactoryName() default NULL;
}