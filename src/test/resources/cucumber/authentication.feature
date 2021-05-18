Feature: Autenticacion de usuario
  Scenario: Usuario inicia sesión
    Given un usuario que ya se ha registrado en la aplicacion
    When ingresa su usuario y contraseña correctos
    Then obtiene un token con el que autenticarse