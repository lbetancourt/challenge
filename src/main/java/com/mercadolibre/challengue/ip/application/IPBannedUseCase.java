package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import com.mercadolibre.challengue.ip.infrastructure.IPBanned;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPBannedResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IPBannedUseCase implements IPBanned {

    private final IPForbiddenRepository ipForbiddenRepository;

    @Override
    public IPBannedResponseDTO recordIPAsBanned(IPProcessCommand ipProcessCommand) {
        var ipForbidden = ipForbiddenRepository.save(ipProcessCommand);
        return IPBannedResponseDTO.builder()
                .withRequestIdentifier(ipProcessCommand.getProcessIdentifier())
                .withIpForbidden(ipForbidden)
                .build();
    }
}
