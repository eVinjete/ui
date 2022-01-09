package si.evinjete.ui;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("ui")
public class UiResource {

    @GET
    public Response showUi() {
        return Response.ok().build();
    }

}
