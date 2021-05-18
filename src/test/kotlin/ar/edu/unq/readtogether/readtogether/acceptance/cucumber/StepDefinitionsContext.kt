package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.*


@Component
class StepDefinitionsContext : ResultActions{

    @Autowired
    private lateinit var mockMvc : MockMvc

    protected lateinit var currentResultAction: ResultActions

    override fun andExpect(matcher: ResultMatcher): ResultActions {
        return currentResultAction.andExpect(matcher);
    }

    override fun andDo(handler: ResultHandler): ResultActions {
        return currentResultAction.andDo(handler);
    }

    override fun andReturn(): MvcResult {
        return currentResultAction.andReturn();
    }

    fun perform(requestBuilder: RequestBuilder): ResultActions {
        currentResultAction = (mockMvc.perform(requestBuilder))
        return this
    }

}
