package com.mercadolibre.challengue.ip.domain;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.application.IPProcessQuery;

import java.util.Optional;

public interface IPForbiddenRepository {
    IPForbidden save(IPProcessCommand ipProcessCommand);
    Optional<IPForbidden> findByIp(IPProcessQuery ipProcessQuery);
}
