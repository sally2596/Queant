package com.ssafy.queant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class Workaround implements WebMvcOpenApiTransformationFilter {

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification();

        Server testServer = new Server();
        testServer.setDescription("test");
        testServer.setUrl("https://i7a201.p.ssafy.io");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8000");
        openApi.setServers(Arrays.asList(testServer, localServer));

        return openApi;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }
}
