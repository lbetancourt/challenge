package com.mercadolibre.challengue.shared.fixer;

import com.mercadolibre.challengue.ip.infrastructure.redis.CurrencyExchange;
import com.mercadolibre.challengue.shared.CacheRepository;
import com.mercadolibre.challengue.shared.Fixer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FixerTest {

    @Autowired
    private Fixer fixer;

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private CacheRepository cacheRepository;

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerAndNoCacheThenResponseCurrenciesList() {
        Map<String, Object> rates = new HashMap<>();
        rates.put("COP", 4503.799995);
        rates.put("CRC", 731.369999);
        rates.put("MDL", 21.2264);

        FixerResponseDTO expectedFixerResponseDTO = new FixerResponseDTO();
        expectedFixerResponseDTO.setSuccess(true);
        expectedFixerResponseDTO.setTimestamp(1626539525L);
        expectedFixerResponseDTO.setBase("EUR");
        expectedFixerResponseDTO.setDate("2021-07-17");
        expectedFixerResponseDTO.setRates(rates);

        Optional<CurrencyExchange> currencyExchange =
                Optional.of(new CurrencyExchange("COP", 4503.799995));

        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(FixerResponseDTO.class)))
                .thenReturn(expectedFixerResponseDTO);
        Mockito
                .doNothing()
                .when(cacheRepository)
                .saveRates(rates);

        CurrencyExchange currencyExchangeExpected = fixer.getForeignExchangeFrom("COP");

        assertAll("CurrencyExchange",
                () -> assertEquals(currencyExchangeExpected.getCodeIso(),
                        currencyExchange.get().getCodeIso()),
                () -> assertEquals(currencyExchangeExpected.getExchange(),
                        currencyExchange.get().getExchange())
        );
    }

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerAndCacheThenResponseCurrenciesList() {
        Map<String, Object> rates = new HashMap<>();
        rates.put("COP", 4503.799995);
        rates.put("CRC", 731.369999);
        rates.put("MDL", 21.2264);

        FixerResponseDTO expectedFixerResponseDTO = new FixerResponseDTO();
        expectedFixerResponseDTO.setSuccess(true);
        expectedFixerResponseDTO.setTimestamp(1626539525L);
        expectedFixerResponseDTO.setBase("EUR");
        expectedFixerResponseDTO.setDate("2021-07-17");
        expectedFixerResponseDTO.setRates(rates);

        Optional<CurrencyExchange> currencyExchange =
                Optional.of(new CurrencyExchange("COP", 4503.799995));

        Mockito
                .when(cacheRepository.getCurrencyExchange(anyString()))
                .thenReturn(currencyExchange);

        CurrencyExchange currencyExchangeExpected = fixer.getForeignExchangeFrom("COP");

        assertAll("CurrencyExchange",
                () -> assertEquals(currencyExchangeExpected.getCodeIso(),
                        currencyExchange.get().getCodeIso()),
                () -> assertEquals(currencyExchangeExpected.getExchange(),
                        currencyExchange.get().getExchange())
        );
    }

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerThenException() {
        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(FixerResponseDTO.class)))
                .thenThrow(new RestClientException("no GET"));

        assertThrows(RestClientException.class, () -> fixer.getForeignExchangeFrom("COP"));
    }

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerThenReturnNull() {
        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(FixerResponseDTO.class)))
                .thenReturn(null);
        assertThrows(FixerException.class, () -> fixer.getForeignExchangeFrom("COP"));
    }
}
