package com.mercadolibre.challengue.shared.fixer;

import com.mercadolibre.challengue.shared.Fixer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class FixerImpl implements Fixer {
    @Value("${challenge.third.api.fixer.url}")
    private String fixerUrl;
    @Value("${challenge.third.api.fixer.key}")
    private String fixerKey;
    private final RestTemplate restTemplate;

    @Override
    public FixerResponseDTO getForeignExchangeFrom() {
        URI targetUrl = UriComponentsBuilder.fromUriString(fixerUrl)
                .queryParam("access_key", fixerKey)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(targetUrl, FixerResponseDTO.class);
    }
}
