package com.mercadolibre.challengue.integration.containers;

import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class IPToCountryTestContainer extends MockServerContainer {
    public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName.parse("jamesdbloom/mockserver:mockserver-5.10.0");
    private static IPToCountryTestContainer container;

    private final String jsonResponse="{" +
            "    \"countryCode\": \"CO\"," +
            "    \"countryCode3\": \"COL\"," +
            "    \"countryName\": \"Colombia\"," +
            "    \"countryEmoji\": \"\uD83C\uDDE8\uD83C\uDDF4\"" +
            "}";

    private IPToCountryTestContainer(){
        super(MOCKSERVER_IMAGE);
    }

    public static IPToCountryTestContainer getInstance() {
        if (container == null) {
            container = new IPToCountryTestContainer();

        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        new MockServerClient(container.getHost(), container.getServerPort())
                .when(request()
                        .withMethod("GET")
                ).respond(response()
                .withBody(jsonResponse));

        System.setProperty("challenge.third.api.ip2country.url", container.getEndpoint()+"/ip?");
    }
}
