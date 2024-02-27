package com.unibet.workshop.client.simulator;

import com.unibet.workshop.client.model.RestCar;
import com.unibet.workshop.client.model.RestCars;
import com.unibet.workshop.client.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserSimulatorWebClient {
    private static final Logger log = LoggerFactory.getLogger(UserSimulator.class);
    private final WebClient webClient;
    public UserSimulatorWebClient(HttpUtils httpUtils) {
        this.webClient = httpUtils.createNewWebClient("http://localhost:8080/");

    }
    public void getCars() {
        Optional<RestCars> restCars = webClient.get()
                .uri("car")
                .retrieve()
                .bodyToMono(RestCars.class)
                .subscribeOn(Schedulers.immediate())
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty())
                .blockOptional(Duration.ofSeconds(1));
        restCars.ifPresent(
                x-> log.info("All cars from the webclient: {}", x.getRestCars().stream().map(RestCar::getNumberPlate).collect(Collectors.joining(";")))
        );
    }

}
