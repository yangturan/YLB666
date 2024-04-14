package com.example.ylbweb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getSwagger(){
       return new OpenAPI().info(new Info().title("盈利宝web接口")
               .description("可以发邮件给我")
               .version("1.0.0")
               .contact(new Contact().name("YTR")
                       .email("ytry5732@gmail.com")));
    }
}
