package edu.upc.eetac.dsa.football;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by toni on 27/04/16.
 */
public class FootballResourceConfig extends ResourceConfig {
    public FootballResourceConfig() {
        packages("edu.upc.eetac.dsa.football");
        packages("edu.upc.eetac.dsa.football.auth");
        register(RolesAllowedDynamicFeature.class);
        packages("edu.upc.eetac.dsa.football.cors");
        register(JacksonFeature.class);
        //register(DeclarativeLinkingFeature.class);
    }
}
