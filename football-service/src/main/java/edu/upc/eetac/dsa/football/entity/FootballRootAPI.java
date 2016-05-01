package edu.upc.eetac.dsa.football.entity;

import edu.upc.eetac.dsa.football.FootballMediaType;
import edu.upc.eetac.dsa.football.FootballRootAPIResource;
import edu.upc.eetac.dsa.football.LoginResource;
import edu.upc.eetac.dsa.football.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by mbmarkus on 1/05/16.
 */
public class FootballRootAPI {
    @InjectLinks({
            @InjectLink(resource = FootballRootAPIResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "self bookmark home", title = "Football Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "login", title = "Login",  type= FootballMediaType.football_AUTH_TOKEN),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "creacion-usuario", title = "Register", type= FootballMediaType.football_USER),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE,
                    rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE,
                    rel = "perfil-usuario", title = "Perfil usuario", condition="${!empty resource.userid}",
                    type= FootballMediaType.football_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
