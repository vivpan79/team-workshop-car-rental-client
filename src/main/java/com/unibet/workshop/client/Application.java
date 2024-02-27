package com.unibet.workshop.client;

import com.unibet.workshop.client.model.RestCar;
import com.unibet.workshop.client.simulator.UserSimulator;
import com.unibet.workshop.client.simulator.UserSimulatorWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import static java.lang.Thread.sleep;

@EnableAsync
@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	private final UserSimulator simulator;

	private final UserSimulatorWebClient webClient;

	public Application(UserSimulator simulator, UserSimulatorWebClient webClient) {
		this.simulator = simulator;
        this.webClient = webClient;
    }

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
			log.debug("Start registerCars");
			simulator.registerCars();
			sleep(1000);
			//simulator.getCars();
			webClient.getCars();
			log.debug("****THE END****");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
