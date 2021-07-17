package com.mercadolibre.challengue.shared.iptocountry;

import com.mercadolibre.challengue.shared.IPToCountry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class IPToCountryImpl implements IPToCountry {
    @Value("${challenge.third.api.ip2country.url}")
    private String ipToCountryUrl;
    private final RestTemplate restTemplate;

    @Override
    public IPToCountryResponseDTO getCountryFrom(String ip) {
        return restTemplate.getForObject(ipToCountryUrl + ip, IPToCountryResponseDTO.class);
    }
}
