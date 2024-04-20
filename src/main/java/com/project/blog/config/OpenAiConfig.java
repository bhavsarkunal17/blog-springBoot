package com.project.blog.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
            title = "Blog APIs",
            description = "All the APIs for blog app",
            summary = "Blog",
            termsOfService = "T&C",
            contact = @Contact(
                    name = "Kunal Bhavsar",
                    email = "bhavsarkunal2103@gmail.com"
            ),
            license = @License(
                    name = "licenceNo"
            ),
            version = "v1"


    ),
        servers = {
                @Server(
                        description = "dev",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "test",
                        url = "http://localhost:8080"
                )
        }

)
public class OpenAiConfig {
}
