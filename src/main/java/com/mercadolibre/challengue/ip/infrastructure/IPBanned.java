package com.mercadolibre.challengue.ip.infrastructure;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPBannedResponseDTO;

public interface IPBanned {
    IPBannedResponseDTO recordIPAsBanned(IPProcessCommand ipProcessCommand);
}
