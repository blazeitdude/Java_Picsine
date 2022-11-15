package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println("---------Hikari----------");
        UsersRepositoryJdbcImpl jdbc = context.getBean(UsersRepositoryJdbcImpl.class);
        System.out.println(jdbc.findAll());
        System.out.println("---------Template--------");
        UsersRepositoryJdbcTemplateImpl  template = context.getBean(UsersRepositoryJdbcTemplateImpl.class);
        System.out.println(template.findAll());
    }
}