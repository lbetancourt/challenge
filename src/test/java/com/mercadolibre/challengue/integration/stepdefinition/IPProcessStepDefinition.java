package com.mercadolibre.challengue.integration.stepdefinition;

import com.mercadolibre.challengue.Application;
import com.mercadolibre.challengue.ip.infrastructure.controller.IPLocationResponseDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static com.mercadolibre.challengue.integration.stepdefinition.KeyTestContext.IP;
import static com.mercadolibre.challengue.integration.stepdefinition.KeyTestContext.IP_EXTENDED;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public class IPProcessStepDefinition {

    @Autowired
    AbstractStepDefinition abstractStepDefinition;

    @Given("an IP Address {string}")
    public void GetIP(String ip) {
        abstractStepDefinition.setContext(IP, ip);
    }

    @When("send IP")
    public void SendIP() {
        var ipLocationResponseDTO = abstractStepDefinition.executeGet(
                String.format("http://localhost:8080/api/v1/ip/%s",
                        abstractStepDefinition.getContext(IP, String.class)));

        abstractStepDefinition.setContext(IP_EXTENDED, ipLocationResponseDTO);
        assertThat(ipLocationResponseDTO, is(notNullValue()));
    }

    @Then("get Information IP extended with {string} {string} {string} {string}")
    public void validateInfo(String nameCountry, String isoCountry, String currency, String exchangePrice) {
        var ipLocationResponseDTO =
                abstractStepDefinition.getContext(IP_EXTENDED, IPLocationResponseDTO.class);

        assertThat(ipLocationResponseDTO.getIpInfo().getNameCountry(), is(equalTo(nameCountry)));
        assertThat(ipLocationResponseDTO.getIpInfo().getIsoCountry(), is(equalTo(isoCountry)));
        assertThat(ipLocationResponseDTO.getIpInfo().getCurrency(), is(equalTo(currency)));
        assertThat(ipLocationResponseDTO.getIpInfo().getExchangePrice(), is(equalTo(exchangePrice)));
    }
}
