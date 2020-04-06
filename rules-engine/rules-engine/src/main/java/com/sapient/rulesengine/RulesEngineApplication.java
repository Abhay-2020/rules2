package com.sapient.rulesengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.sapient")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
@EntityScan(basePackages = "com.sapient.model") 
public class RulesEngineApplication {
  public static void main(String[] args) {
    SpringApplication.run(RulesEngineApplication.class, args);
  }

}
