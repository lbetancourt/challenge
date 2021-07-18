package com.mercadolibre.challengue.ip.infrastructure.postgres;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.domain.IPForbidden;
import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IPForbiddenRepositoryTest {

    @Autowired
    private IPForbiddenRepository ipForbiddenRepository;

    @MockBean
    private IPForbiddenPostgresRepository ipForbiddenPostgresRepository;

    @Test
    public void givenIPBannedWhenSaveIPBannedThenSave() {
        var ipProcessCommand = IPProcessCommand.buildWithUUID("8.8.8.8");

        var ipForbiddenPostgres = new IPForbiddenPostgres();
        ipForbiddenPostgres.setId(ipProcessCommand.getProcessIdentifier());
        ipForbiddenPostgres.setIp(ipProcessCommand.getIp());
        ipForbiddenPostgres.setForbiddenDate(LocalDateTime.now());

        Mockito
                .when(ipForbiddenPostgresRepository.save(any(IPForbiddenPostgres.class)))
                .thenReturn(ipForbiddenPostgres);

        IPForbidden ipForbidden = ipForbiddenRepository.save(ipProcessCommand);

        assertAll("ipForbidden",
                () -> assertEquals(ipForbidden.getIp(), ipProcessCommand.getIp())
        );

    }

    @Test
    public void givenIPBannedWhenSaveIPBannedThenThrow() {
        var ipProcessCommand = IPProcessCommand.buildWithUUID("8.8.8.8");

        Mockito
                .when(ipForbiddenPostgresRepository.save(any(IPForbiddenPostgres.class)))
                .thenThrow(new IllegalArgumentException("Don't Save"));

        assertThrows(IllegalArgumentException.class, () -> ipForbiddenRepository.save(ipProcessCommand));
    }

    @Test
    public void givenIPBannedWhenFindByIPBannedThenGetIPBanned() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        var ipForbiddenPostgres = new IPForbiddenPostgres();
        ipForbiddenPostgres.setId(ipProcessQuery.getProcessIdentifier());
        ipForbiddenPostgres.setIp(ipProcessQuery.getIp());
        ipForbiddenPostgres.setForbiddenDate(LocalDateTime.now());

        Mockito
                .when(ipForbiddenPostgresRepository.findByIp(anyString()))
                .thenReturn(ipForbiddenPostgres);

        Optional<IPForbidden> ipForbidden = ipForbiddenRepository.findByIp(ipProcessQuery);

        assertAll("ipForbidden",
                () -> assertEquals(ipForbidden.get().getIp(), ipProcessQuery.getIp())
        );
    }

    @Test
    public void givenIPBannedWhenFindByIPBannedThenThrow() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        Mockito
                .when(ipForbiddenPostgresRepository.findByIp(anyString()))
                .thenThrow(new IllegalArgumentException("Don't Query"));

        assertThrows(IllegalArgumentException.class, () -> ipForbiddenRepository.findByIp(ipProcessQuery));
    }
}
