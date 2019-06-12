package com.mrz.iheartaudio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.mrz.iheartaudio.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IheartaudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(IheartaudioApplication.class, args);
    }

}
