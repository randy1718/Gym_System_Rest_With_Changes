package com.gym.system.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import com.gym.system.filter.TransactionIdFilter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.FilterRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();

        context.register(WebConfig.class);

        DispatcherServlet dispatcherServlet =
                new DispatcherServlet(context);

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", dispatcherServlet);

        FilterRegistration.Dynamic transactionFilter =
            servletContext.addFilter("transactionIdFilter", new TransactionIdFilter());

        transactionFilter.addMappingForUrlPatterns(null, false, "/*");

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/api/*");
    }

}