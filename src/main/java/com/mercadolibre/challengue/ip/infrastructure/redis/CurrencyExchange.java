package com.mercadolibre.challengue.ip.infrastructure.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("CurrencyExchange")
public class CurrencyExchange {
    @Id
    private String codeIso;
    private Double exchange;
}
