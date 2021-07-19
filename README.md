# Challenge Meli

## How to run
execute on the project root
```console
docker-compose up
```
execute silent mode
```console
docker-compose up -d
```

## Endpoints

*base url* http://localhost:8080

**IP info**
----
    obtain ip information
* **URL**

  /api/v1/ip/{ip}

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

`ip=[String]`

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{ "requestIdentifier": "11e5288f-e83a-11eb-a746-bdb01251fe1c", "ipInfo": { "nameCountry": "Germany", "isoCountry": "DE", "currency": "EUR", "exchangePrice": "1" } }`

* **Error Response:**

    * **Code:** 500 Internal server error <br />
      **Content:** `Internal server error`

* **Sample Call:**
    ```console
    http://localhost:8080/api/v1/ip/5.6.7.8
    ```

**IP Banned**
----
    banned ip
* **URL**

  /api/ip

* **Method:**

  `POST`

* **Data Params (payload)**

  **Required:**

  `{ip: "5.6.7.8"}`

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{ "requestIdentifier": "1edcb540-e83a-11eb-a746-c9328437f9c2", "ipForbidden": { "ip": "5.6.7.8" } }`

* **Error Response:**

    * **Code:** 500 Internal server error <br />
      **Content:** `Internal server error`


### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-redis)
* [Testcontainers](https://www.testcontainers.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

