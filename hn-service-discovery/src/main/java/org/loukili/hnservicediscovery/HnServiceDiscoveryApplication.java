package org.loukili.hnservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HnServiceDiscoveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(HnServiceDiscoveryApplication.class, args);
	}

}
