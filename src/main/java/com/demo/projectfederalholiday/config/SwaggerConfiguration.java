/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Prabhakaran
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI holidayOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Federal Holiday Management API")
                        .description("API to manage federal holidays for USA and Canada")
                        .version("1.0"));
    }
}
