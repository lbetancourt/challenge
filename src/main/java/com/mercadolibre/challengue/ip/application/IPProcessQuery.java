package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.shared.config.IdentifierCreator;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
public final class IPProcessQuery {
    private final UUID processIdentifier;
    private final String ip;

    public static IPProcessQuery buildWithUUID(String ip) {
        return IPProcessQuery.builder()
                .withProcessIdentifier(IdentifierCreator.generateUUID())
                .withIp(ip)
                .build();
    }
}
