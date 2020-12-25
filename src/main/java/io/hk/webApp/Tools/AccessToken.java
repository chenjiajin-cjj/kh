package io.hk.webApp.Tools;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessToken {

    /**
     * 当 Token 为 false 不做判断
     */
    boolean Token() default true;
}