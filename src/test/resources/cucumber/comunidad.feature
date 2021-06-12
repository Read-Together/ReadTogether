Feature: Comunidades

  Scenario: Usuario crea una comunidad
    Given un usuario logeado
    When crea una comunidad
    Then obtiene el id de la comunidad creada
    And  puede ver la vista del nuevo grupo

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

  Scenario: Usuario carga un libro
    Given un usuario logeado
    And una comunidad ya creada de nombre "lecturas aburridas"
    And el usuario pertenece a la comunidad "lecturas aburridas"
    When el usuario carga un libro en la comunidad "lecturas aburridas"
    Then el usuario puede ver el libro en la biblioteca de "lecturas aburridas"
