Feature: Information IP

  Scenario Outline: Given an IP address, find the country it belongs to and display
    Given an IP Address "<ip>"
    When send IP
    Then get Information IP extended with "<nameCountry>" "<isoCountry>" "<currency>" "<exchangePrice>"

    Examples:
      | ip           | nameCountry | isoCountry | currency | exchangePrice |
      | 186.28.158.0 | Colombia    | CO         | COP      | 4503.799995   |