package com.unibet.workshop.client.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HttpUtils {

    private final WebClient.Builder webClientBuilder;

    public HttpUtils(final WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public WebClient createNewWebClient(String baseUrl) {
        return webClientBuilder.build()
            .mutate()
            .baseUrl(StringUtils.trimTrailingCharacter(baseUrl, '/'))
            .build();
    }
}
