package com.mercadolibre.challengue.ip.infrastructure;

import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;

public interface IPProcess {
    IPLocationResponseDTO getIPInfo(IPProcessQuery ip);
}
