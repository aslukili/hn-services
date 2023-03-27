package org.loukili.hnnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HnNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnNotificationApplication.class, args);
    }

}
