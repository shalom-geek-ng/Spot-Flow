package com.spotflow.SpotFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	    exclude = { SystemMetricsAutoConfiguration.class }
	)
public class SpotFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotFlowApplication.class, args);
	}

}
