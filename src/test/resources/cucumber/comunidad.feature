Feature: Comunidades
  Scenario: Usuario crea una comunidad
    Given un usuario logeado
    When crea una comunidad
    Then obtiene el id de la comunidad creada
    And es miembro del grupo