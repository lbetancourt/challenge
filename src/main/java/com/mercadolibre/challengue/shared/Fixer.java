package com.mercadolibre.challengue.shared;

import com.mercadolibre.challengue.ip.infrastructure.redis.CurrencyExchange;

public interface Fixer {
    CurrencyExchange getForeignExchangeFrom(String currencyCode);
}
