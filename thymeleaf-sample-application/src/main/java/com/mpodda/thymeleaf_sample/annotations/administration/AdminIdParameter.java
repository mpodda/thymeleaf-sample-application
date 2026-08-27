package com.mpodda.thymeleaf_sample.annotations.administration;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({PARAMETER, ElementType.TYPE_PARAMETER})
public @interface AdminIdParameter {

}
