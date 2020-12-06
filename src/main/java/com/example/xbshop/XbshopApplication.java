package com.example.xbshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableScheduling
public class XbshopApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(XbshopApplication.class, args);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisTemplate redisTemplate) {
        //key可见
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value可见
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;

    }

    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new GenerateImageCodeServlet(), "/generateCode");
        return registrationBean;
    }

    @Value("${file.requestPath}")
    private String requestPath;

    @Value("${file.dir}")
    private String dir;

    /**
     * 映射/upload下的路径取磁盘找
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(requestPath).addResourceLocations("file:///" + dir);
    }




}
