package com.mercadolibre.challengue.ip.infrastructure.controller;

import com.mercadolibre.challengue.ip.domain.IPInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
public final class IPLocationResponseDTO {
    private final UUID requestIdentifier;
    private final IPInfo ipInfo;

    public static IPLocationResponseDTO buildFrom(UUID requestIdentifier, IPInfo ipInfo) {
        return IPLocationResponseDTO.builder()
                .withRequestIdentifier(requestIdentifier)
                .withIpInfo(IPInfo.buildFrom(
                        ipInfo.getNameCountry(),
                        ipInfo.getIsoCountry(),
                        ipInfo.getCurrency(),
                        ipInfo.getExchangePrice()
                ))
                .build();
    }
}
