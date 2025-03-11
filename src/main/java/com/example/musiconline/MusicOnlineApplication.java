package com.example.musiconline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MusicOnlineApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MusicOnlineApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }

}
