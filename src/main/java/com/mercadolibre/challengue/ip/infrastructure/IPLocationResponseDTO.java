package com.mercadolibre.challengue.ip.infrastructure;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public final class IPLocationResponseDTO {
    private final UUID requestIdentifier;

    public static IPLocationResponseDTO buildFrom(){
    return null;
    }
}
