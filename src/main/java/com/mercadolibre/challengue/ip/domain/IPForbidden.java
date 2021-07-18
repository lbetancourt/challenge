package com.mercadolibre.challengue.ip.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public final class IPForbidden {
    private final String ip;

    public static IPForbidden buildFrom(String ip) {
        return IPForbidden.builder()
                .withIp(ip)
                .build();
    }
}
