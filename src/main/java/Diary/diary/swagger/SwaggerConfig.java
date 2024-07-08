package Diary.diary.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("springdoc-openapi")
                        .version("1.0")
                        .description("전재연이 짱이야"));
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }
}
