package com.serookie.movie.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment){
//        Profiles profiles=Profiles.of("dev");
//        boolean b=environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .enable(b)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.serookie.movie.controller"))
                .build();
//                .securityContexts(securityContextList())
//                .securitySchemes(securitySchemesList());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("kevintam项目接口文档").
                description("kevintam项目接口文档")
                .contact(new Contact("kevintam","https://serookie.com","843808107@qq.com"))
                .version("1.0")
                .build();
    }
//    private List<ApiKey> securitySchemesList(){
//        List<ApiKey> list=new ArrayList<>();
//        ApiKey apiKey = new ApiKey("Authorization","Authorization","header");
//        list.add(apiKey);
//        return list;
//    }
//    private List<SecurityContext> securityContextList(){
//        List<SecurityContext> list=new ArrayList<>();
//        list.add(getContextPath("/hello.*"));
//        return list;
//    }

//    private SecurityContext getContextPath(String path) {
//        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex(path))
//                .build();
//    }
//    private List<SecurityReference> defaultAuth() {
//        List<SecurityReference> list=new ArrayList<>();
//        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
//        AuthorizationScope[] authorizationScopes=new AuthorizationScope[1];
//        authorizationScopes[0]=authorizationScope;
//        list.add(new SecurityReference("Authorization",authorizationScopes));
//        return list;
//    }
}
