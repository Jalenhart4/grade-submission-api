package com.ltp.gradesubmission.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(contact = @Contact(
        name = "Jalen",
        email = "Jalenhart4@gmail.com"),
        description = "API to manage students grades in courses",
        title = "Grade submission API"),
        servers = @Server(
                description = "Local ENV",
                url = "http://localhost:8080") ,
                security = {@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth, make a request to localhost:8080/user/register with username and password body (JSON), then localhost:8080/authenticate with same username and password combo (JSON) to receive JWT token in header.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
@Configuration
public class OpenAPIConfig {

//    @Bean
//    public OpenAPI openAPI(){
//        return new OpenAPI().info(new Info().title("Grade submission API").description("An API that can manage students grades in courses").version("v1.0"));
//    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI();
    }
}
