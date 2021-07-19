package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPForbidden;
import com.mercadolibre.challengue.ip.domain.IPForbiddenException;
import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;
import com.mercadolibre.challengue.ip.infrastructure.redis.CurrencyExchange;
import com.mercadolibre.challengue.shared.Fixer;
import com.mercadolibre.challengue.shared.IPToCountry;
import com.mercadolibre.challengue.shared.RestCountries;
import com.mercadolibre.challengue.shared.fixer.FixerResponseDTO;
import com.mercadolibre.challengue.shared.iptocountry.IPToCountryResponseDTO;
import com.mercadolibre.challengue.shared.restcountries.Currency;
import com.mercadolibre.challengue.shared.restcountries.RestCountryResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IPProcessUseCaseTest {

    @Autowired
    private IPProcess ipProcess;

    @MockBean
    private IPToCountry ipToCountry;
    @MockBean
    private RestCountries restCountries;
    @MockBean
    private Fixer fixer;
    @MockBean
    private IPForbiddenRepository ipForbiddenRepository;

    @Test
    public void givenIPWhenSendToProcessThenGetInfoIPAggregate() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        var ipToCountryResponseDTO = new IPToCountryResponseDTO();
        ipToCountryResponseDTO.setCountryCode("CO");
        ipToCountryResponseDTO.setCountryCode3("COL");
        ipToCountryResponseDTO.setCountryName("Colombia");
        ipToCountryResponseDTO.setCountryEmoji("\uD83C\uDDE8\uD83C\uDDF4");

        var currency = new Currency();
        currency.setCode("COP");
        currency.setName("Colombian peso");
        currency.setSymbol("$");
        List<Currency> currencies = List.of(currency);

        var restCountryResponseDTO = new RestCountryResponseDTO();
        restCountryResponseDTO.setCurrencies(currencies);

        Map<String, Object> rates = new HashMap<>();
        rates.put("COP", 4503.799995);
        rates.put("CRC", 731.369999);
        rates.put("MDL", 21.2264);

        var expectedFixerResponseDTO = new FixerResponseDTO();
        expectedFixerResponseDTO.setSuccess(true);
        expectedFixerResponseDTO.setTimestamp(1626539525L);
        expectedFixerResponseDTO.setBase("EUR");
        expectedFixerResponseDTO.setDate("2021-07-17");
        expectedFixerResponseDTO.setRates(rates);

        var currencyExchange =new CurrencyExchange("COP", 4503.799995);

        Mockito
                .when(ipToCountry.getCountryFrom(anyString()))
                .thenReturn(ipToCountryResponseDTO);

        Mockito
                .when(restCountries.getCurrenciesFrom(anyString()))
                .thenReturn(restCountryResponseDTO);

        Mockito
                .when(fixer.getForeignExchangeFrom(anyString()))
                .thenReturn(currencyExchange);

        IPLocationResponseDTO ipLocationResponseDTO = ipProcess.getIPInfo(ipProcessQuery);

        assertAll("IPLocationResponseDTO",
                () -> assertEquals(ipLocationResponseDTO.getIpInfo().getIsoCountry(), ipToCountryResponseDTO.getCountryCode()),
                () -> assertEquals(ipLocationResponseDTO.getIpInfo().getNameCountry(), ipToCountryResponseDTO.getCountryName()),
                () -> assertEquals(ipLocationResponseDTO.getIpInfo().getCurrency(), currency.getCode()),
                () -> assertEquals(ipLocationResponseDTO.getIpInfo().getExchangePrice(), rates.get("COP").toString())
        );
    }

    @Test
    public void givenIPForbiddenWhenSendToProcessThenThrow() {
        Optional<IPForbidden> ipForbidden = Optional.of(IPForbidden.buildFrom("186.28.158.0"));
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        Mockito
                .when(ipForbiddenRepository.findByIp(any(IPProcessQuery.class)))
                .thenReturn(ipForbidden);

        assertThrows(IPForbiddenException.class, () -> ipProcess.getIPInfo(ipProcessQuery));
    }

    @Test
    public void givenIPWhenSendToProcessThenThrowIPToCountry() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        Mockito
                .when(ipToCountry.getCountryFrom(anyString()))
                .thenThrow(new RestClientException("no Get"));

        assertThrows(RuntimeException.class, () -> ipProcess.getIPInfo(ipProcessQuery));
    }

    @Test
    public void givenIPWhenSendToProcessThenThrowRestCountries() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        var ipToCountryResponseDTO = new IPToCountryResponseDTO();
        ipToCountryResponseDTO.setCountryCode("CO");
        ipToCountryResponseDTO.setCountryCode3("COL");
        ipToCountryResponseDTO.setCountryName("Colombia");
        ipToCountryResponseDTO.setCountryEmoji("\uD83C\uDDE8\uD83C\uDDF4");

        Mockito
                .when(ipToCountry.getCountryFrom(anyString()))
                .thenReturn(ipToCountryResponseDTO);

        Mockito
                .when(restCountries.getCurrenciesFrom(anyString()))
                .thenThrow(new RestClientException("no Get"));

        assertThrows(RuntimeException.class, () -> ipProcess.getIPInfo(ipProcessQuery));
    }

    @Test
    public void givenIPWhenSendToProcessThenThrowFixer() {
        var ipProcessQuery = IPProcessQuery.buildWithUUID("186.28.158.0");

        var ipToCountryResponseDTO = new IPToCountryResponseDTO();
        ipToCountryResponseDTO.setCountryCode("CO");
        ipToCountryResponseDTO.setCountryCode3("COL");
        ipToCountryResponseDTO.setCountryName("Colombia");
        ipToCountryResponseDTO.setCountryEmoji("\uD83C\uDDE8\uD83C\uDDF4");

        var currency = new Currency();
        currency.setCode("COP");
        currency.setName("Colombian peso");
        currency.setSymbol("$");
        List<Currency> currencies = List.of(currency);

        var restCountryResponseDTO = new RestCountryResponseDTO();
        restCountryResponseDTO.setCurrencies(currencies);

        Mockito
                .when(ipToCountry.getCountryFrom(anyString()))
                .thenReturn(ipToCountryResponseDTO);

        Mockito
                .when(restCountries.getCurrenciesFrom(anyString()))
                .thenReturn(restCountryResponseDTO);

        Mockito
                .when(fixer.getForeignExchangeFrom(anyString()))
                .thenThrow(new RestClientException("no Get"));

        assertThrows(RuntimeException.class, () -> ipProcess.getIPInfo(ipProcessQuery));
    }
}
