package org.kiirun.vertxeventbusplayground;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

@SpringBootApplication
public class Application implements BeanPostProcessor{
   @Autowired
   private Vertx vertx;

   public static void main(final String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Override
   public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
      return bean;
   }

   @Override
   public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
      if (bean instanceof Verticle ) {
         vertx.deployVerticle((Verticle) bean);
      }
      return bean;
   }
}
