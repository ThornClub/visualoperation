package cc.sgee.visualoperation.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: Thorn
 * @Date: 2021/3/3 17:49
 * @Blog: https://www.sgee.cc
 * @Description: Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swagger.enable}")
    private boolean swagger_enable;
    /*
     * Description:〈创建API应用〉
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     * @param
     * @return: springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger_enable)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage("cc.sgee.visualoperation.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /*
     * Description:〈创建API的基本信息〉
     * 访问地址：http://ip:端口/swagger-ui.html
     * @param
     * @return: springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo(){
        Contact contact = new Contact("thorn","https://www.sgee.cc","admin@sgee.cc");
        return new ApiInfoBuilder().title("API文档")
                .description("Linux可视化运维API文档")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
