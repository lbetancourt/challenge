package com.mercadolibre.challengue.ip.infrastructure.redis;

import com.mercadolibre.challengue.shared.CacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyExchangeComponent implements CacheRepository {

    private final CurrencyExchangeRedisRepository currencyExchangeRedisRepository;

    @Override
    public void saveRates(Map<String, Object> rates) {
        List<CurrencyExchange> currencyExchanges = new ArrayList<>();
        for (Map.Entry<String, Object> entry : rates.entrySet()) {
            currencyExchanges.add(new CurrencyExchange(entry.getKey(), Double.parseDouble(entry.getValue().toString())));
        }
        currencyExchangeRedisRepository.saveAll(currencyExchanges);
    }

    @Override
    public Optional<CurrencyExchange> getCurrencyExchange(String codeCurrency) {
        return currencyExchangeRedisRepository.findById(codeCurrency);
    }
}
