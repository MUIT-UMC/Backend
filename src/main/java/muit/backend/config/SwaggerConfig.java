package muit.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI MUITAPI(){
        Info info = new Info()
                .title("MUIT API")
                .description("MUIT API 명세서")
                .version("1.0.0");

        String jwtSchemeName = "bearerAuth";

        // API 요청 헤더에 인증 정보 포함 -> admin을 위해
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization"));

        Server localServer = new Server().url("/").description("Local Server");

        return new OpenAPI()
                .addServersItem(localServer)
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components)
                .security(List.of(new SecurityRequirement().addList(jwtSchemeName)));

    }
}
