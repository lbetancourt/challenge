package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.shared.config.IdentifierCreator;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
public final class IPProcessCommand {
    private final UUID processIdentifier;
    private final String ip;

    public static IPProcessCommand buildWithUUID(String ip) {
        return IPProcessCommand.builder()
                .withProcessIdentifier(IdentifierCreator.generateUUID())
                .withIp(ip)
                .build();
    }
}
