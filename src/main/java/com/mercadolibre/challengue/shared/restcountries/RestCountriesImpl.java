package com.mercadolibre.challengue.shared.restcountries;

import com.mercadolibre.challengue.shared.RestCountries;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestCountriesImpl implements RestCountries {
    @Value("${challenge.third.api.restcountries.url}")
    private String restCountryUrl;
    private final RestTemplate restTemplate;

    @Override
    public RestCountryResponseDTO getCurrenciesFrom(String codeCountry) {
        URI targetUrl = UriComponentsBuilder.fromUriString(restCountryUrl)
                .path(codeCountry)
                .queryParam("fields", "currencies")
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(targetUrl, RestCountryResponseDTO.class);
    }
}
