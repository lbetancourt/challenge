package com.mercadolibre.challengue.ip.infrastructure.controller;

import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ip")
@RequiredArgsConstructor
public class IPController {

    private final IPProcess ipProcess;

    @GetMapping("/{ip}")
    public ResponseEntity<IPLocationResponseDTO> getIPInfo(@PathVariable("ip") String ip) {
        return ResponseEntity.ok()
                .body(ipProcess.getIPInfo(IPProcessQuery.buildWithUUID(ip)));
    }
}
