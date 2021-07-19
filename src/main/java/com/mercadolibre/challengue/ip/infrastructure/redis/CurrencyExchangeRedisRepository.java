package com.mercadolibre.challengue.ip.infrastructure.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRedisRepository extends CrudRepository<CurrencyExchange,String> {
}
