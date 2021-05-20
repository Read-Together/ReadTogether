package ar.edu.unq.readtogether.readtogether.acceptance

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["../resources/cucumber/authentication.feature"]
        , plugin = ["pretty"])
class RunCucumberTest {
}