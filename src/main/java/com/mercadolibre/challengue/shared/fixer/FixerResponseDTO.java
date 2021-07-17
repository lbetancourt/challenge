package com.mercadolibre.challengue.shared.fixer;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class FixerResponseDTO {
    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Object> rates;

    public String getRateByCodeCurrency(String codeCurrency) {
        Optional<Map.Entry<String, Object>> singleRate = rates.entrySet().stream()
                .filter(r -> r.getKey().equals(codeCurrency))
                .findFirst();
        if (singleRate.isPresent()) {
            return singleRate.get().getValue().toString();
        } else {
            return "0";
        }
    }
}
