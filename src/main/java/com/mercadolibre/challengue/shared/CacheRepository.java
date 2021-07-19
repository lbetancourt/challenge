package com.mercadolibre.challengue.shared;

import com.mercadolibre.challengue.ip.infrastructure.redis.CurrencyExchange;

import java.util.Map;
import java.util.Optional;

public interface CacheRepository {

    void saveRates(Map<String, Object> rates);

    Optional<CurrencyExchange> getCurrencyExchange(String codeCurrency);
}
