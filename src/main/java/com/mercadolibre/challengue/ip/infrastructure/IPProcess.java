package com.mercadolibre.challengue.ip.infrastructure;

import com.mercadolibre.challengue.ip.domain.IPInfo;

public interface IPProcess {
    IPInfo getIPInfo(String ip);
}
