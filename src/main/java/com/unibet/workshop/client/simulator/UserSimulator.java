package com.unibet.workshop.client.simulator;

import com.unibet.workshop.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UserSimulator {
    private static final Logger log = LoggerFactory.getLogger(UserSimulator.class);

    private final RestTemplate restTemplate;

    public UserSimulator(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();;
    }

    public RestCar registerCars() {
        RestCar restCar = getRestCar();

        RestCar car = restTemplate.postForObject(
                "http://localhost:8080/car/register", restCar, RestCar.class);
        log.info("Car with numberplate {} has been registered successfully! ", Objects.requireNonNull(car).getNumberPlate());
        return car;
    }

    public void getCars() {
        RestCars restCars = restTemplate.getForObject(
                "http://localhost:8080/car", RestCars.class);
        log.info("All cars: {}",
                Objects.requireNonNull(restCars).getRestCars().stream().map(RestCar::getNumberPlate)
                        .collect(Collectors.joining(";")));
    }

    public RestCar getRestCar() {
        RestCar restCar = new RestCar();
        restCar.setNumberPlate("ABC" + new Random().nextInt());
        return restCar;
    }
}
