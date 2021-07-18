package com.mercadolibre.challengue.ip.infrastructure.postgres;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.domain.IPForbidden;
import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IPForbiddenComponent implements IPForbiddenRepository {

    private final IPForbiddenPostgresRepository ipForbiddenPostgresRepository;

    @Override
    public IPForbidden save(IPProcessCommand ipProcessCommand) {
        IPForbiddenPostgres ipForbiddenPostgres = new IPForbiddenPostgres();
        ipForbiddenPostgres.setId(ipProcessCommand.getProcessIdentifier());
        ipForbiddenPostgres.setIp(ipProcessCommand.getIp());
        ipForbiddenPostgres.setForbiddenDate(LocalDateTime.now());

        ipForbiddenPostgresRepository.save(ipForbiddenPostgres);

        return IPForbidden.buildFrom(ipForbiddenPostgres.getIp());
    }

    @Override
    public Optional<IPForbidden> findByIp(IPProcessQuery ipProcessQuery) {
        IPForbiddenPostgres ipForbiddenPostgres = ipForbiddenPostgresRepository.findByIp(ipProcessQuery.getIp());
        return Optional.of(IPForbidden.buildFrom(ipForbiddenPostgres.getIp()));
    }
}
