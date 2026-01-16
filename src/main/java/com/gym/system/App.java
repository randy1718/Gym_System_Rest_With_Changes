package com.gym.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gym.system.config.AppConfig;

public class App {
    public static void main( String[] args ){

        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Initializing GYM System...");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
