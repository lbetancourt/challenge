package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import com.mercadolibre.challengue.ip.infrastructure.IPBanned;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPBannedResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class IPBannedUseCase implements IPBanned {

    private final IPForbiddenRepository ipForbiddenRepository;

    @Override
    public IPBannedResponseDTO recordIPAsBanned(IPProcessCommand ipProcessCommand) {
        try {
            var ipForbidden = ipForbiddenRepository.save(ipProcessCommand);
            return IPBannedResponseDTO.builder()
                    .withRequestIdentifier(ipProcessCommand.getProcessIdentifier())
                    .withIpForbidden(ipForbidden)
                    .build();
        } catch (RuntimeException ex) {
            log.error("trace [{}] error processing IP [{}] due to {}", ipProcessCommand.getProcessIdentifier(), ipProcessCommand.getIp(), ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("trace [{}] error processing IP [{}] due to {}", ipProcessCommand.getProcessIdentifier(), ipProcessCommand.getIp(), ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
