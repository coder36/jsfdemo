package org.coder36.webdemo.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use on Class's and methods to mark as needing to be logged
 * @author Mark Middleton
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( value={ElementType.METHOD, ElementType.TYPE }  )
public @interface Logit {
	String value() default "";
}
