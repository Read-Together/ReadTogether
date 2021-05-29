package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["../resources/cucumber/authentication.feature"]
        , plugin = ["pretty"])
class CucumberIntegrationTest {
}