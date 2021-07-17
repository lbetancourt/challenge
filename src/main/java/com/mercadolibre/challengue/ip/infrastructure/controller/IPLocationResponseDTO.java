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
}
