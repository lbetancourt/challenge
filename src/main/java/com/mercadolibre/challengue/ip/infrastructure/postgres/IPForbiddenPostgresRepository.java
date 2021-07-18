package com.mercadolibre.challengue.ip.infrastructure.postgres;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPForbiddenPostgresRepository extends CrudRepository<IPForbiddenPostgres, UUID> {
    IPForbiddenPostgres findByIp(String ip);
}
