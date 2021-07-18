package com.mercadolibre.challengue.ip.infrastructure.controller;

import com.mercadolibre.challengue.ip.domain.IPForbidden;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
public final class IPBannedResponseDTO {
    private final UUID requestIdentifier;
    private final IPForbidden ipForbidden;
}
