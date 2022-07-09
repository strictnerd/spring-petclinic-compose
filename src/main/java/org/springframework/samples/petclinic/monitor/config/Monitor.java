package org.springframework.samples.petclinic.monitor.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MonitorConfig.class)
@Documented
public @interface Monitor {
    String[] targets();
    String[] pkgs();
}
