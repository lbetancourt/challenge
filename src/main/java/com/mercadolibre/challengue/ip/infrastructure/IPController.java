package com.mercadolibre.challengue.ip.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ip")
public class IPController {
    @GetMapping("/{ip}")
    public ResponseEntity<IPLocationResponseDTO> getIPInfo(@PathVariable("ip") String ip)
    {
        return null;
    }
}
