package edu.upc.eetac.dsa.football;

import edu.upc.eetac.dsa.football.Auth.Rol;
import edu.upc.eetac.dsa.football.DAO.ApuestaAlreadyExistsException;
import edu.upc.eetac.dsa.football.DAO.ApuestaDAO;
import edu.upc.eetac.dsa.football.DAO.ApuestaDAOImpl;
import edu.upc.eetac.dsa.football.entity.Apuesta;
import edu.upc.eetac.dsa.football.entity.ApuestaCollection;
import edu.upc.eetac.dsa.football.entity.ApuestaUsuario;
import edu.upc.eetac.dsa.football.entity.ApuestaUsuarioCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by toni on 4/05/16.
 */
@Path("apuesta")
public class ApuestaResource {


    /**
     *
     * General
     *
     */

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_APUESTA)
    public Response createApuesta(@FormParam("id") String id, @FormParam("cuota1") float cuota1, @FormParam("cuotax") float cuotax, @FormParam("cuota2") float cuota2, @FormParam("ganadora") String ganadora, @FormParam("estado") String estado, @Context UriInfo uriInfo) throws URISyntaxException{
        if(id == null || estado == null)
            throw new BadRequestException("all parameters are mandatory");
        ApuestaDAO apuestaDAO = new ApuestaDAOImpl();
        Apuesta apuesta = new Apuesta();

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");


        try{
            apuesta = apuestaDAO.createApuesta(id, cuota1, cuotax, cuota2, ganadora, estado);

        }catch (ApuestaAlreadyExistsException e){
            throw new WebApplicationException("apuesta already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + apuesta.getId());
        return Response.created(uri).type(FootballMediaType.football_APUESTA).entity(apuesta).build();


    }

    @Path("/{id}")
    @GET
    @Produces(FootballMediaType.football_APUESTA)
    public Apuesta getApuestaByID(@PathParam("id") String id){
        Apuesta apuesta = new Apuesta();

        try {
            apuesta = new ApuestaDAOImpl().getApuestaById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(apuesta == null)
            throw new NotFoundException("Apuesta with id = "+id+" doesn't exist");
        return apuesta;
    }

    @Path("/listado")
    @GET
    @Produces(FootballMediaType.football_APUESTA_COLLECTION)
    public ApuestaCollection getApuestas() {
        ApuestaCollection apuestaCollection = null;

        try {
            apuestaCollection = new ApuestaDAOImpl().getApuestas();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(apuestaCollection == null)
            throw new NotFoundException("Apuestas doesn't exist");
        return apuestaCollection;
    }

    @Path("/{id}")
    @PUT
    @Consumes(FootballMediaType.football_APUESTA)
    @Produces(FootballMediaType.football_APUESTA)
    public Apuesta updateApuesta (@PathParam("id") String id, Apuesta apuesta) {

        Apuesta apuestan = new Apuesta();

        if(apuesta == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(apuesta.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            apuestan = new ApuestaDAOImpl().updateApuesta(apuesta);
            if(apuestan == null)
                throw new NotFoundException("Apuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return apuestan;
    }

    @Path("/{id}")
    @DELETE
    public void deleteApuesta(@PathParam("id") String id){

        if(!new Rol().permisoAdmin(securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            if(!new ApuestaDAOImpl().deleteApuesta(id))
                throw new NotFoundException("Apuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    /**
     *
     * USUARIOS
     *
     */
    @Path("/usuarios/")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_APUESTAUSUARIO)
    public Response createApuestaUsuario(@FormParam("idusuario") String idusuario, @FormParam("idapuesta") String idapuesta,
                                         @FormParam("resultado") String resultado, @FormParam("valor") float valor,
                                         @Context UriInfo uriInfo) throws URISyntaxException
    {
        if(idusuario == null || idapuesta == null || resultado == null || valor == 0)
            throw new BadRequestException("all parameters are mandatory");
        ApuestaDAO apuestaDAO = new ApuestaDAOImpl();
        ApuestaUsuario apuestaUsuario = new ApuestaUsuario();

        if(!new Rol().permisoAdmin_Usuario(idusuario, securityContext))
            throw new ForbiddenException("operation not allowed");

        try{
            apuestaUsuario = apuestaDAO.createApuestaUsuario(idusuario, idapuesta, resultado, valor);
            apuestaDAO.actuCuotas(apuestaUsuario.getApuestaid());
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + apuestaUsuario.getId());
        return Response.created(uri).type(FootballMediaType.football_APUESTAUSUARIO).entity(apuestaUsuario).build();

    }

    @Path("/usuarios/{id}")
    @GET
    @Produces(FootballMediaType.football_APUESTAUSUARIO)
    public ApuestaUsuario getApuestaUsuarioByID(@PathParam("id") String id){
        ApuestaUsuario apuestaUsuario = new ApuestaUsuario();

        try {
            apuestaUsuario = new ApuestaDAOImpl().getApuestaUsuarioById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(apuestaUsuario == null)
            throw new NotFoundException("Apuesta with id = "+id+" doesn't exist");
        return apuestaUsuario;
    }

    @Path("/usuarioslistados/{id}")
    @GET
    @Produces(FootballMediaType.football_APUESTAUSUARIO_COLLECTION)
    public ApuestaUsuarioCollection getApuestasUsuarios(@PathParam("id") String id) {
        ApuestaUsuarioCollection apuestaCollection = null;

        try {
            apuestaCollection = new ApuestaDAOImpl().getApuestasUsuario(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(apuestaCollection == null)
            throw new NotFoundException("Apuestas doesn't exist");
        return apuestaCollection;
    }


}
