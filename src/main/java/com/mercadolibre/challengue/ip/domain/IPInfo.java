package com.mercadolibre.challengue.ip.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public final class IPInfo {
    private final String nameCountry;
    private final String isoCountry;
    private final String currency;
    private final String exchangePrice;

    public static IPInfo buildFrom(String nameCountry, String isoCountry, String currency, String exchangePrice) {
        return IPInfo.builder()
                .withNameCountry(nameCountry)
                .withIsoCountry(isoCountry)
                .withCurrency(currency)
                .withExchangePrice(exchangePrice)
                .build();
    }
}
