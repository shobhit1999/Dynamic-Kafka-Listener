package com.dynamic.kafkalistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableScheduling
@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class KafkalistenerApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkalistenerApplication.class, args);
  }

}
