package com.forgeessentials.core.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IModule
{
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface LoadMe
    {
        String name();
        boolean loadOnClient() default true;
        boolean loadOnServer() default true;
    }
}
