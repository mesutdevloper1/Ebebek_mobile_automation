package com.Ebebek.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {
    private static final Logger LOGGER = LogManager.getLogger(AnnotationTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (System.getProperty("enable.test.time.out", "true").equals("true")) {
            if (testMethod.getDeclaringClass().getSimpleName().contains("Clean")) {
                annotation.setTimeOut(600 * 1000);
            } else {
                annotation.setTimeOut(200 * 1000);
            }
        }else {
            LOGGER.debug("Test time out disabled");
        }
    }
}