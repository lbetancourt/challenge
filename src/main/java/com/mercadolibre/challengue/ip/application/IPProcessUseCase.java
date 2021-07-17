package com.mercadolibre.challengue.ip.application;

import com.mercadolibre.challengue.ip.domain.IPInfo;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import org.springframework.stereotype.Service;

@Service
public class IPProcessUseCase implements IPProcess {
    @Override
    public IPInfo getIPInfo(String ip) {
        return null;
    }
}
