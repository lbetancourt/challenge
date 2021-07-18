package com.mercadolibre.challengue.ip.infrastructure.controller;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.infrastructure.IPBanned;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ip")
@RequiredArgsConstructor
public class IPController {

    private final IPProcess ipProcess;
    private final IPBanned ipBanned;

    @GetMapping("/{ip}")
    public ResponseEntity<IPLocationResponseDTO> getIPInfo(@PathVariable("ip") String ip) {
        return ResponseEntity.ok()
                .body(ipProcess.getIPInfo(IPProcessQuery.buildWithUUID(ip)));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<IPBannedResponseDTO> setIPBanned(IPBannedRequestDTO ipBannedRequestDTO) {
        return ResponseEntity.ok()
                .body(ipBanned.recordIPAsBanned(IPProcessCommand.buildWithUUID(ipBannedRequestDTO.getIp())));
    }
}
