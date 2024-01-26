package person.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/person")
public class PersonResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greeting() {
        return "Hello person";
    }
}
