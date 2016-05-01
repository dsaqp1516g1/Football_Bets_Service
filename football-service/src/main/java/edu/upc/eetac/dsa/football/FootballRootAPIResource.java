package edu.upc.eetac.dsa.football;

import edu.upc.eetac.dsa.football.entity.FootballRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by mbmarkus on 1/05/16.
 */

@Path("/")
public class FootballRootAPIResource {

    @Context
    private SecurityContext securityContext;

    private String userid;

    @GET
    @Produces(FootballMediaType.football_ROOT)

    public FootballRootAPI getRootAPI(){
        if(securityContext.getUserPrincipal()!=null)
            userid = securityContext.getUserPrincipal().getName();
        FootballRootAPI footballRootAPI = new FootballRootAPI();
        return footballRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
