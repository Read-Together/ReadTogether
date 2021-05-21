package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.ReadtogetherApplication
import org.junit.Before
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [ReadtogetherApplication::class], loader = SpringBootContextLoader::class)
class CucumberSpringContextConfiguration {

    private val LOG: Logger = LoggerFactory.getLogger(CucumberSpringContextConfiguration::class.java)

    @Before
    fun setUp() {
        LOG.info{ "-------------- Spring Context Initialized For Executing Cucumber Tests --------------"}
    }
}