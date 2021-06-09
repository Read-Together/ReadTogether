Feature: Comunidades

  Scenario: Usuario crea una comunidad
    Given un usuario logeado
    When crea una comunidad
    Then obtiene el id de la comunidad creada

  Scenario: Usuario se une a una comunidad
    Given un usuario logeado
    And una comunidad ya creada de nombre "los capos"
    When el usuario se une a la comunidad "los capos"
    Then es miembro del grupo

  Scenario: Usuario sale de una comunidad
    Given un usuario logeado
    And una comunidad ya creada de nombre "los capos"
    And el usuario pertenece a la comunidad "los capos"
    When el usuario sale de la comunidad "los capos"
    Then el usuario ya no pertenece a la comunidad "los capos"