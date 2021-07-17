package com.mercadolibre.challengue.ip.application;

import com.fasterxml.uuid.Generators;
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
                .withProcessIdentifier(generateUUID())
                .withIp(ip)
                .build();
    }

    private static UUID generateUUID() {
        return Generators.timeBasedGenerator().generate();
    }
}
