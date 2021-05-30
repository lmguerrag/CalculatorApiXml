Feature: Suma
  As usuario
  I want sumar numeros

  Background:
    Given un "usuario" quiere hacer operaciones con dos numeros

  Scenario: Suma de dos numeros
    When suma los numeros
    Then la api entrega el resultado de la suma

  Scenario: Multiplicacion de dos numeros
    When multiplica los numeros
    Then la api entrega el resultado de la multiplicacion