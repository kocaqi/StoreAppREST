package com.localweb.storeapp.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.localweb.storeapp")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {
	
	@Autowired
	private Environment environment;

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Bean
    public DataSource dataSource() {
    	
    	ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    	
    	try {
			comboPooledDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
    	
    	comboPooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
    	comboPooledDataSource.setUser(environment.getProperty("jdbc.user"));
    	comboPooledDataSource.setPassword(environment.getProperty("jdbc.password"));
    	
    	comboPooledDataSource.setInitialPoolSize(Integer.parseInt(
                environment.getProperty("connection.pool.initialPoolSize")));
    	comboPooledDataSource.setMinPoolSize(Integer.parseInt(
				environment.getProperty("connection.pool.minPoolSize")));
    	comboPooledDataSource.setMaxPoolSize(Integer.parseInt(
				environment.getProperty("connection.pool.maxPoolSize")));
    	comboPooledDataSource.setMaxIdleTime(Integer.parseInt(
				environment.getProperty("connection.pool.maxIdleTime")));

    	
    	return comboPooledDataSource;
    }

}














