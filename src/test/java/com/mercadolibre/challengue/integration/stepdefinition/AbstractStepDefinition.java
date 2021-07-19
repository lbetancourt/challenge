package com.mercadolibre.challengue.integration.stepdefinition;

import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AbstractStepDefinition {
    @Autowired
    private RestTemplate restTemplate;

    private final Map<String, Object> abstractContext;

    public AbstractStepDefinition() {
        abstractContext = new HashMap<>();
    }

    public void setContext(KeyTestContext key, Object value) {
        abstractContext.put(key.toString(), value);
    }

    public <T> T getContext(KeyTestContext key, Class<T> clazz) {
        return clazz.cast(getContext(key));
    }

    public Object getContext(KeyTestContext key) {
        return abstractContext.get(key.toString());
    }

    public IPLocationResponseDTO executeGet(String url) {
        return restTemplate.getForObject(url, IPLocationResponseDTO.class);
    }
}
