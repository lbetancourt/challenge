package com.mercadolibre.challengue.ip.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class IPInfo {
    private final String nameCountry;
    private final String isoCountry;
    private final String currency;
    private final String exchangePrice;
}
