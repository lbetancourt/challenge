package com.mercadolibre.challengue.shared.fixer;

import com.mercadolibre.challengue.ip.infrastructure.redis.CurrencyExchange;
import com.mercadolibre.challengue.shared.CacheRepository;
import com.mercadolibre.challengue.shared.Fixer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FixerImpl implements Fixer {
    @Value("${challenge.third.api.fixer.url}")
    private String fixerUrl;
    @Value("${challenge.third.api.fixer.key}")
    private String fixerKey;
    private final RestTemplate restTemplate;
    private final CacheRepository cacheRepository;

    @Override
    public CurrencyExchange getForeignExchangeFrom(String currencyCode) {
        Optional<CurrencyExchange> currencyExchange = cacheRepository.getCurrencyExchange(currencyCode);
        if (currencyExchange.isPresent()) {
            return currencyExchange.get();
        } else {
            URI targetUrl = UriComponentsBuilder.fromUriString(fixerUrl)
                    .queryParam("access_key", fixerKey)
                    .build()
                    .encode()
                    .toUri();

            FixerResponseDTO fixerResponseDTO = restTemplate.getForObject(targetUrl, FixerResponseDTO.class);
            try {
                cacheRepository.saveRates(fixerResponseDTO.getRates());
            } catch (Exception ex) {
                throw new FixerException("Get fixer error due to " + ex.getMessage());
            }
            return getSingleFrom(fixerResponseDTO.getRates(), currencyCode);
        }
    }

    private CurrencyExchange getSingleFrom(Map<String, Object> rates, String currencyCode) {
        for (Map.Entry<String, Object> entry : rates.entrySet()) {
            if (entry.getKey().equals(currencyCode))
                return new CurrencyExchange(entry.getKey(), Double.parseDouble(entry.getValue().toString()));
        }
        return null;
    }
}
