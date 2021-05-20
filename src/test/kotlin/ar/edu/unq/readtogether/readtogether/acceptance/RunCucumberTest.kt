package ar.edu.unq.readtogether.readtogether.acceptance

import ar.edu.unq.readtogether.readtogether.ReadtogetherApplication
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration

@RunWith(Cucumber::class)
@CucumberOptions(features = ["../resources/cucumber/authentication.feature"]
        , plugin = ["pretty"])
@ContextConfiguration(classes = [ReadtogetherApplication::class])
class RunCucumberTest {
}