package com.gdd.ylz.common.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	String name() default "";
}
