package com.mercadolibre.challengue.shared.iptocountry;

import lombok.Data;

@Data
public class IPToCountryResponseDTO {
    private String countryCode;
    private String countryCode3;
    private String countryName;
    private String countryEmoji;
}
