package edu.upc.eetac.dsa.football;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by toni on 27/04/16.
 */
public class FootballResourceConfig extends ResourceConfig {
    public FootballResourceConfig() {
        packages("edu.upc.eetac.dsa.football");
    }
}
