package com.mercadolibre.challengue.shared.restcountries;

import lombok.Data;

import java.util.List;

@Data
public class RestCountryResponseDTO {
    private List<Currency> currencies;

    public Currency getSingleCurrency(){
        return currencies.get(0);
    }
}
