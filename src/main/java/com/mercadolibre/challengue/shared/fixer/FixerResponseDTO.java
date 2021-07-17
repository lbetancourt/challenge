package com.mercadolibre.challengue.shared.fixer;

import lombok.Data;

import java.util.Map;

@Data
public class FixerResponseDTO {
    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Object> rates;
}
