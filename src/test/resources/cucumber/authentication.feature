Feature: Autenticacion de usuario
  Scenario: Usuario inicia sesión
    Given un usuario que ya se ha registrado en la aplicacion
    When ingresa su usuario y contraseña correctos
    Then obtiene un token

  Scenario: Usuario se registra
    Given un usuario que no se ha registrado en la aplicacion
    When se registra con usuario, mail y contraseña
    Then puede autenticarse con las credenciales con las que se registro

  Scenario: Usuario autenticado usa una funcionalidad que requiere autenticacion
    Given un usuario que ya se ha registrado en la aplicacion
    And esta autenticado
    When intenta crear una comunidad
    Then la aplicacion se lo permite

  Scenario: Usuario no autenticado no puede usar una funcionalidad que requiere autenticacion
    Given un usuario que no se ha registrado en la aplicacion
    When intenta crear una comunidad
    Then la aplicacion no se lo permite