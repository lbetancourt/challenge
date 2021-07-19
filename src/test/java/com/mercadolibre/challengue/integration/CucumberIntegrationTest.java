package com.mercadolibre.challengue.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {
                "pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json",
                "timeline:target/timeline"
        },
        glue = {
                "com.mercadolibre.challengue.integration.stepdefinition"
        }
)
public class CucumberIntegrationTest {

}
