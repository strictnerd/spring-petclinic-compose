package org.springframework.samples.petclinic.monitor.config;

import io.prometheus.client.CollectorRegistry;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.samples.petclinic.monitor.interceptor.ControllerMonitor;


@Configuration
public class MonitorConfig implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        CollectorRegistry defaultRegistry = CollectorRegistry.defaultRegistry;
        ((DefaultListableBeanFactory)registry).registerSingleton("prometheus", defaultRegistry);
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
        Class<Monitor> monitorClass = Monitor.class;
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(monitorClass.getName(), false));
        String[] targets = annotationAttributes.getStringArray("targets");
        String[] pkgs = annotationAttributes.getStringArray("pkgs");

        for (int i=0; i<targets.length; i++) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultPointcutAdvisor.class);
            TargetType targetType = TargetType.valueOf(targets[i]);
            String pkgName = pkgs[i];
            switch (targetType) {
                case CONTROLLER:
                    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
                    filterRegistrationBean.setFilter(new ControllerMonitor(defaultRegistry));
                    filterRegistrationBean.addUrlPatterns("/*");
                    filterRegistrationBean.setName("controllerMonitor");
                    filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
                    ((DefaultListableBeanFactory)registry).registerSingleton("controllerMonitor", filterRegistrationBean);
                    break;
            }
        }

    }
}
