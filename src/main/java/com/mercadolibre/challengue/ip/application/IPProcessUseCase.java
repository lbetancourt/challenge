package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPInfo;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import com.mercadolibre.challengue.shared.Fixer;
import com.mercadolibre.challengue.shared.IPToCountry;
import com.mercadolibre.challengue.shared.RestCountries;
import com.mercadolibre.challengue.shared.fixer.FixerResponseDTO;
import com.mercadolibre.challengue.shared.restcountries.RestCountryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IPProcessUseCase implements IPProcess {

    private final IPToCountry ipToCountry;
    private final RestCountries restCountries;
    private final Fixer fixer;

    @Override
    public IPLocationResponseDTO getIPInfo(IPProcessQuery ipProcessQuery) {
        var ipToCountryResponseDTO = ipToCountry
                .getCountryFrom(ipProcessQuery.getIp());
        var restCountryResponseDTO = restCountries
                .getCurrenciesFrom(ipToCountryResponseDTO.getCountryCode());
        var fixerResponseDTO = fixer.getForeignExchangeFrom();

        return IPLocationResponseDTO.builder()
                .withRequestIdentifier(ipProcessQuery.getProcessIdentifier())
                .withIpInfo(IPInfo.buildFrom(
                        ipToCountryResponseDTO.getCountryName(),
                        ipToCountryResponseDTO.getCountryCode(),
                        getCodeCurrency(restCountryResponseDTO),
                        getRate(restCountryResponseDTO, fixerResponseDTO)
                ))
                .build();
    }

    private String getRate(RestCountryResponseDTO restCountryResponseDTO, FixerResponseDTO fixerResponseDTO) {
        return fixerResponseDTO.getRateByCodeCurrency(getCodeCurrency(restCountryResponseDTO));
    }

    private String getCodeCurrency(RestCountryResponseDTO restCountryResponseDTO) {
        return restCountryResponseDTO.getSingleCurrency().getCode();
    }
}
