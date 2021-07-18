package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPForbidden;
import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import com.mercadolibre.challengue.ip.infrastructure.IPBanned;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPBannedResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IPBannedUseCaseTest {

    @Autowired
    private IPBanned ipBanned;

    @MockBean
    private IPForbiddenRepository ipForbiddenRepository;

    @Test
    public void givenIPWhenSendToBannedThenSaveIP() {
        var ipProcessCommand = IPProcessCommand.buildWithUUID("186.28.158.0");

        var ipForbidden = IPForbidden.buildFrom("186.28.158.0");

        Mockito
                .when(ipForbiddenRepository.save(any(IPProcessCommand.class)))
                .thenReturn(ipForbidden);

        var ipBannedResponseDTO = ipBanned.recordIPAsBanned(ipProcessCommand);

        assertAll("ipBannedResponseDTO",
                () -> assertEquals(ipBannedResponseDTO.getIpForbidden().getIp(), ipForbidden.getIp())
        );
    }
}
