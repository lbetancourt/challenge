package com.mercadolibre.challengue.shared;

import com.mercadolibre.challengue.shared.iptocountry.IPToCountryResponseDTO;

public interface IPToCountry {
    IPToCountryResponseDTO getCountryFrom(String ip);
}
