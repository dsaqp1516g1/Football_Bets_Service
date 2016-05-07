package edu.upc.eetac.dsa.football;

import edu.upc.eetac.dsa.football.Auth.Rol;
import edu.upc.eetac.dsa.football.DAO.EquipoAlreadyExistsException;
import edu.upc.eetac.dsa.football.DAO.EquipoDAO;
import edu.upc.eetac.dsa.football.DAO.EquipoDAOImpl;
import edu.upc.eetac.dsa.football.entity.Equipo;
import edu.upc.eetac.dsa.football.entity.EquipoCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by mbmarkus on 1/05/16.
 */

@Path("equipo")
public class EquipoResource {

    @Context
    private SecurityContext securityContext;


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_EQUIPO)
    public Response createEquipo(@FormParam("nombre") String nombre, @FormParam("nomenclatura") String nomenclatura, @FormParam("valor") int valor, @Context UriInfo uriInfo) throws URISyntaxException {
        if(nombre == null || nomenclatura == null || valor == 0)
            throw new BadRequestException("all parameters are mandatory");
        EquipoDAO equipoDAO = new EquipoDAOImpl();
        Equipo equipo = new Equipo();

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try{
            equipo = equipoDAO.createEquipo(nombre, nomenclatura, valor);

        }catch (EquipoAlreadyExistsException e){
            throw new WebApplicationException("equipo already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + equipo.getId());
        return Response.created(uri).type(FootballMediaType.football_EQUIPO).entity(equipo).build();
    }

    @Path("/{id}")
    @GET
    @Produces(FootballMediaType.football_EQUIPO)
    public Equipo getEquipobyID(@PathParam("id") String id) {
        Equipo equipo = new Equipo();

        try {
            equipo = new EquipoDAOImpl().getEquipobyID(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(equipo == null)
            throw new NotFoundException("Equipo with id = "+id+" doesn't exist");
        return equipo;
    }
    @Path("/n/{nom}")
    @GET
    @Produces(FootballMediaType.football_EQUIPO)
    public Equipo getEquipobyNom(@PathParam("nom") String nomen) {
        Equipo equipo = new Equipo();
        try {
            equipo = new EquipoDAOImpl().getEquipobyNomen(nomen);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(equipo == null)
            throw new NotFoundException("Equipo with nomenclatura = "+nomen+" doesn't exist");
        return equipo;
    }

    @GET
    @Produces(FootballMediaType.football_EQUIPO_COLLECTION)
    public EquipoCollection getEquipos() {
        EquipoCollection equipoCollection = null;

        try {
            equipoCollection = new EquipoDAOImpl().getEquipos();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(equipoCollection == null)
            throw new NotFoundException("Equipos doesn't exist");
        return equipoCollection;
    }

    @Path("/{id}")
    @PUT
    @Consumes(FootballMediaType.football_EQUIPO)
    @Produces(FootballMediaType.football_EQUIPO)
    public Equipo updateEquipo (@PathParam("id") String id, Equipo equipo) {

        Equipo equipon = new Equipo();

        if(equipo == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(equipo.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            equipon = new EquipoDAOImpl().updateEquipo(equipo);
            if(equipon == null)
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return equipon;
    }

    @Path("/{id}")
    @DELETE
    public void deleteEquipo(@PathParam("id") String id){

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            if(!new EquipoDAOImpl().deleteEquipo(id))
                throw new NotFoundException("Equipo with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
