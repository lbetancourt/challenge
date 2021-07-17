package com.mercadolibre.challengue.shared.restcountries;

import com.mercadolibre.challengue.shared.RestCountries;
import com.mercadolibre.challengue.shared.fixer.FixerResponseDTO;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestCountriesTest {
    @Autowired
    private RestCountries restCountries;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void givenCountryCodeWhenCallRestCountryThenGetCurrencies() {
        Currency currencyCOP = new Currency();
        currencyCOP.setCode("COP");
        currencyCOP.setName("Colombian peso");
        currencyCOP.setSymbol("$");

        RestCountryResponseDTO restCountryResponseDTO = new RestCountryResponseDTO();
        restCountryResponseDTO.setCurrencies(List.of(currencyCOP));

        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(RestCountryResponseDTO.class)))
                .thenReturn(restCountryResponseDTO);

        RestCountryResponseDTO expectedRestCountryResponseDTO = restCountries.getCurrenciesFrom("CO");

        assertAll("RestCountryResponseDTO",
                () -> assertEquals(1, expectedRestCountryResponseDTO.getCurrencies().size()),
                () -> assertEquals(expectedRestCountryResponseDTO.getCurrencies().get(0).getCode(), currencyCOP.getCode()),
                () -> assertEquals(expectedRestCountryResponseDTO.getCurrencies().get(0).getName(), currencyCOP.getName()),
                () -> assertEquals(expectedRestCountryResponseDTO.getCurrencies().get(0).getSymbol(), currencyCOP.getSymbol())
        );
    }

    @Test
    public void givenCountryCodeWhenCallRestCountryThenException() {
        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(RestCountryResponseDTO.class)))
                .thenThrow(new RestClientException("no GET"));

        assertThrows(RestClientException.class, () -> restCountries.getCurrenciesFrom("CO"));
    }
}
