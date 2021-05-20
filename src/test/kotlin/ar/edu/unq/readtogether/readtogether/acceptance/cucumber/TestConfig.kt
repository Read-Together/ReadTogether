package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

@Configuration
class TestConfig {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Bean
    @Scope("prototype")
    fun mockMvc(): MockMvc? {
        return webAppContextSetup(webApplicationContext)
                .dispatchOptions<DefaultMockMvcBuilder>(true).build()
    }

}