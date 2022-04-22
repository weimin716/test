package com.gdd.ylz;


import com.gdd.ylz.common.datasources.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@Import(DynamicDataSourceConfig.class)
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = "com.gdd.ylz")
public class YlzApplication {

    public static void main(String[] args) {
        SpringApplication.run(YlzApplication.class, args);
    }

}
