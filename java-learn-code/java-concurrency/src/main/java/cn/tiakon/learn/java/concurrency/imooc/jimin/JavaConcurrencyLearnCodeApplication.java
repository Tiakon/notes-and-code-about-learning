package cn.tiakon.learn.java.concurrency.imooc.jimin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Administrator
 */
@SpringBootApplication
public class JavaConcurrencyLearnCodeApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {

        SpringApplication.run(JavaConcurrencyLearnCodeApplication.class, args);


    }

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new HttpFilter());
        filterRegistrationBean.addUrlPatterns("/threanLocal/*");
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpIntercepter()).addPathPatterns("/**");
    }
}
