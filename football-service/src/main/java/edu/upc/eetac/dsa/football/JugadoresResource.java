package edu.upc.eetac.dsa.football;

import edu.upc.eetac.dsa.football.Auth.Rol;
import edu.upc.eetac.dsa.football.DAO.JugadoresAlreadyExistsException;
import edu.upc.eetac.dsa.football.DAO.JugadoresDAO;
import edu.upc.eetac.dsa.football.DAO.JugadoresDAOimpl;
import edu.upc.eetac.dsa.football.entity.Jugadores;
import edu.upc.eetac.dsa.football.entity.JugadoresCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by toni on 9/06/16.
 */
@Path("jugadores")
public class JugadoresResource {
    @Context
    private SecurityContext securityContext;


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_JUGADORES)
    public Response createJugadores(@FormParam("equipoid") String equipoid, @FormParam("nombre") String nombre, @FormParam("dorsal") int dorsal, @FormParam("nacionalidad") String nacionalidad, @FormParam("valor") String valor, @FormParam("nacimiento") String nacimiento, @FormParam("fincontrato") String fincontrato, @Context UriInfo uriInfo) throws URISyntaxException {
        if(equipoid == null || nombre == null || nacionalidad == null)
            throw new BadRequestException("all parameters are mandatory");
        JugadoresDAO jugadoresDAO = new JugadoresDAOimpl();
        Jugadores jugadores = new Jugadores();

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try{
            jugadores = jugadoresDAO.createJugadores(equipoid, nombre, dorsal, nacionalidad, valor, nacimiento, fincontrato);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + jugadores.getNombre());
        return Response.created(uri).type(FootballMediaType.football_JUGADORES).entity(jugadores).build();
    }
    @Path("/{nombre}")
    @GET
    @Produces(FootballMediaType.football_JUGADORES)
    public Jugadores getJugadoresbyNOM(@PathParam("nombre") String nombre) {
        Jugadores jugadores = new Jugadores();

        try {
            jugadores = new JugadoresDAOimpl().getJugadoresbyNOM(nombre);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(jugadores == null)
            throw new NotFoundException("Jugador with nombre = "+nombre+" doesn't exist");
        return jugadores;
    }

    @Path("/listado/{equipoid}")
    @GET
    @Produces(FootballMediaType.football_JUGADORES_COLLECTION)
    public JugadoresCollection getJugadores(@PathParam("equipoid") String equipoid) {
        JugadoresCollection jugadoresCollection = new JugadoresCollection();

        try {
            jugadoresCollection = new JugadoresDAOimpl().getJugadores(equipoid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(jugadoresCollection == null)
            throw new NotFoundException("Jugador with id = "+equipoid+" doesn't exist");
        return jugadoresCollection;
    }

    @Path("/{nombre}")
    @PUT
    @Consumes(FootballMediaType.football_JUGADORES)
    @Produces(FootballMediaType.football_JUGADORES)
    public Jugadores updateJugadores (@PathParam("nombre") String nombre, Jugadores jugadores) {

        Jugadores jugadoresn = new Jugadores();

        if(jugadores == null)
            throw new BadRequestException("entity is null");
        if(!nombre.equals(jugadores.getNombre()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            jugadoresn = new JugadoresDAOimpl().updateJugadores(jugadores);
            if(jugadoresn == null)
                throw new NotFoundException("User with nombre = "+nombre+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return jugadoresn;
    }

    @Path("/{nombre}")
    @DELETE
    public void deleteJugadores(@PathParam("nombre") String nombre){

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            if(!new JugadoresDAOimpl().deleteJugadores(nombre))
                throw new NotFoundException("Jugador with nombre = "+nombre+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
