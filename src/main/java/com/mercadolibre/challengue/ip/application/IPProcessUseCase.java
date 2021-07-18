package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPForbiddenException;
import com.mercadolibre.challengue.ip.domain.IPForbiddenRepository;
import com.mercadolibre.challengue.ip.domain.IPInfo;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;
import com.mercadolibre.challengue.shared.Fixer;
import com.mercadolibre.challengue.shared.IPToCountry;
import com.mercadolibre.challengue.shared.RestCountries;
import com.mercadolibre.challengue.shared.fixer.FixerResponseDTO;
import com.mercadolibre.challengue.shared.restcountries.RestCountryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class IPProcessUseCase implements IPProcess {

    private final IPToCountry ipToCountry;
    private final RestCountries restCountries;
    private final Fixer fixer;
    private final IPForbiddenRepository ipForbiddenRepository;

    @Override
    public IPLocationResponseDTO getIPInfo(IPProcessQuery ipProcessQuery) {
        try {
            if (checkIPIsForbidden(ipProcessQuery))
                throw new IPForbiddenException(String.format("IP %s is Banned", ipProcessQuery.getIp()));

            var ipToCountryResponseDTO = ipToCountry
                    .getCountryFrom(ipProcessQuery.getIp());
            var restCountryResponseDTO = restCountries
                    .getCurrenciesFrom(ipToCountryResponseDTO.getCountryCode());
            var fixerResponseDTO = fixer.getForeignExchangeFrom();

            return IPLocationResponseDTO.buildFrom(
                    ipProcessQuery.getProcessIdentifier(),
                    IPInfo.buildFrom(
                            ipToCountryResponseDTO.getCountryName(),
                            ipToCountryResponseDTO.getCountryCode(),
                            getCodeCurrency(restCountryResponseDTO),
                            getRate(restCountryResponseDTO, fixerResponseDTO)
                    )
            );
        } catch (RuntimeException ex) {
            log.info("trace [{}] error processing IP [{}] due to {}", ipProcessQuery.getProcessIdentifier(), ipProcessQuery.getIp(), ex.getMessage());
            throw ex;
        }
    }

    private boolean checkIPIsForbidden(IPProcessQuery ipProcessQuery) {
        return ipForbiddenRepository.findByIp(ipProcessQuery).isPresent();
    }

    private String getRate(RestCountryResponseDTO restCountryResponseDTO, FixerResponseDTO fixerResponseDTO) {
        return fixerResponseDTO.getRateByCodeCurrency(getCodeCurrency(restCountryResponseDTO));
    }

    private String getCodeCurrency(RestCountryResponseDTO restCountryResponseDTO) {
        return restCountryResponseDTO.getSingleCurrency().getCode();
    }
}
