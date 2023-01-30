package com.numble.carot.common.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "당근마켓 샘플 서버 API",
                version = SwaggerConfig.API_VERSION,
                description = "당근마켓 프론트엔드 개발에 활용할 수 있는 간단한 당근마켓 샘플 서버 API"
        )
)
@SecurityScheme(
        name = SwaggerConfig.AUTHENTICATION,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    public static final String AUTHENTICATION = "Authentication";
    public static final String API_VERSION = "v1.0.0";
}
