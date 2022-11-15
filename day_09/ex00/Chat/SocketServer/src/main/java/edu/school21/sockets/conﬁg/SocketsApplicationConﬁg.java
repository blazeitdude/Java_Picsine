package edu.school21.sockets.conﬁg;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@PropertySource("classpath:db.properties")

@Configuration
@ComponentScan("edu.school21.sockets.services")
public class SocketsApplicationConﬁg {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.driver.name}")
    private String driverClassName;
    @Bean
    public HikariDataSource hikariDataSourceBean() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UsersRepository UsersRepositoryImplBean() {
        return new UsersRepositoryImpl(hikariDataSourceBean());
    }

    @Bean
    UsersService usersServiceBean() {
        return new UsersServiceImpl(UsersRepositoryImplBean(), passwordEncoderBean());
    }

    @Bean
    public JdbcTemplate jdbcTemplateBean() {
        return new JdbcTemplate(hikariDataSourceBean());
    }

}
