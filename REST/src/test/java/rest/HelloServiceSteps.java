package rest;
import io.cucumber.java.en.*;
import person.acme.HelloService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloServiceSteps {
    String result;
    HelloService service = new HelloService();

    @When("I call the hello service")
    public void iCallTheHelloService() {
        result = service.hello();
    }
    @Then("I get the answer {string}")
    public void iGetTheAnswer(String string) {
        assertEquals(string,result);
    }
    
}

