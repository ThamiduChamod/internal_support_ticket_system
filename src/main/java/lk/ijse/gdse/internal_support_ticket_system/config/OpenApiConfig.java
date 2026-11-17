package lk.ijse.gdse.internal_support_ticket_system.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpDeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelpDesk Lite API")
                        .description("Java Backend Practical Assessment 2026")
                        .version("1.0.0"));
    }
}