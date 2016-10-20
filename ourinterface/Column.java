package com.assist.database.ourinterface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取 
@Documented
public @interface Column {
	public String value() default "";
	public String columnName() default "";
}
