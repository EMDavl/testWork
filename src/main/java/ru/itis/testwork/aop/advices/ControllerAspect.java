package ru.itis.testwork.aop.advices;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.itis.testwork.controllers.PostsController;

@Aspect
@Component
public class ControllerAspect {

    private Logger log = LoggerFactory.getLogger(PostsController.class);

    @Pointcut("execution(* ru.itis.testwork.controllers.PostsController.*(..))")
    public void controllerMethodsPointcut() {
    }

    @Pointcut("execution(* ru.itis.testwork.services.PostsService.*(..))")
    public void serviceMethodsPointcut() {
    }


    @Before("controllerMethodsPointcut()")
    public void logExecution(JoinPoint joinPoint) {
        log.info("Вызван метод: {}, аргументы: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterThrowing(value = "serviceMethodsPointcut()", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex){
        log.error("Ошибка исполнения. Метод: {}, ошибка: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}
