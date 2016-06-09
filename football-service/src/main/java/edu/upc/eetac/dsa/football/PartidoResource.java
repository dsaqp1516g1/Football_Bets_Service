package edu.upc.eetac.dsa.football;

import edu.upc.eetac.dsa.football.Auth.Rol;
import edu.upc.eetac.dsa.football.DAO.PartidoAlreadyExistsException;
import edu.upc.eetac.dsa.football.DAO.PartidoDAO;
import edu.upc.eetac.dsa.football.DAO.PartidoDAOimpl;
import edu.upc.eetac.dsa.football.entity.Partido;
import edu.upc.eetac.dsa.football.entity.PartidoCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by toni on 12/05/16.
 */
@Path("partido")
public class PartidoResource {

    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_PARTIDO)
    public Response createPartido(@FormParam("local") String local, @FormParam("visitante") String visitante, @FormParam("jornada") int jornada, @FormParam("fecha") String fecha, @FormParam("goleslocal") int goleslocal, @FormParam("golesvisitante") int golesvisitante, @FormParam("estado") String estado, @Context UriInfo uriInfo) throws URISyntaxException {
        if(local == null || visitante == null || jornada == 0 || fecha == null || estado ==null)
            throw new BadRequestException("all parameters are mandatory");
        PartidoDAO partidoDAO = new PartidoDAOimpl();
        Partido partido = new Partido();

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try{
            partido = partidoDAO.createPartido(local, visitante, jornada, fecha, goleslocal, golesvisitante, estado);

        }catch (PartidoAlreadyExistsException e){
            throw new WebApplicationException("partido already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + partido.getId());
        return Response.created(uri).type(FootballMediaType.football_PARTIDO).entity(partido).build();
    }

    @Path("/{id}")
    @GET
    @Produces(FootballMediaType.football_PARTIDO)
    public Partido getPartidoByID(@PathParam("id") String id) {
        Partido partido = new Partido();

        try {
            partido = new PartidoDAOimpl().getPartidoById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(partido == null)
            throw new NotFoundException("Equipo with id = "+id+" doesn't exist");
        return partido;
    }

    @GET
    @Produces(FootballMediaType.football_PARTIDO_COLLECTION)
    public PartidoCollection getPartidos() {
        PartidoCollection partidoCollection = null;

        try {
            partidoCollection = new PartidoDAOimpl().getPartidos();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(partidoCollection == null)
            throw new NotFoundException("Partidos doesn't exist");
        return partidoCollection;
    }

    @Path("/{id}")
    @PUT
    @Consumes(FootballMediaType.football_PARTIDO)
    @Produces(FootballMediaType.football_PARTIDO)
    public Partido updatePartido (@PathParam("id") String id, Partido partido) {

        Partido partidon = new Partido();
        PartidoDAO partidoDAO = new PartidoDAOimpl();

        if(partido == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(partido.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            partidon = partidoDAO.updatePartido(partido);
            if(partidon == null)
                throw new NotFoundException("partido with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return partidon;
    }
    @Path("/{id}")
    @DELETE
    public void deletePartido(@PathParam("id") String id){

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            if(!new PartidoDAOimpl().deletePartido(id))
                throw new NotFoundException("partido with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
