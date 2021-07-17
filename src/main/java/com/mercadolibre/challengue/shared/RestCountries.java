package com.mercadolibre.challengue.shared;

import com.mercadolibre.challengue.shared.restcountries.RestCountryResponseDTO;

public interface RestCountries {
    RestCountryResponseDTO getCurrenciesFrom(String codeCountry);
}
