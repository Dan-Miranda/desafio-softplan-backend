package br.com.danillo.desafiosoftplan.configurations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final Set<String> TIPOS_DE_CONTEUDO_QUE_API_SUPORTA = Set.of(
        MediaType.APPLICATION_JSON_VALUE
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("desafio-softplan")
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.danillo.desafiosoftplan"))
            .build()
            .ignoredParameterTypes(LocalDate.class, Pageable.class, CompletableFuture.class)
            .directModelSubstitute(LocalDate.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class, Optional.class)
            .tags(recursos()[0], recursos())
            .useDefaultResponseMessages(false)
            .consumes(TIPOS_DE_CONTEUDO_QUE_API_SUPORTA)
            .produces(TIPOS_DE_CONTEUDO_QUE_API_SUPORTA)
            .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
            .title("Desafio SoftPlan - Cadastro de pessoas")
            .description("Nesta API será possível realizar o cadastro de pessoas.")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .build();
    }

    private Tag[] recursos() {
		return new Tag[]{
            new Tag("Pessoas","Permite cadastrar novas pessoas."),
        };
	}
}
