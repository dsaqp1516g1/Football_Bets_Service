package edu.upc.eetac.dsa.football;


import edu.upc.eetac.dsa.football.Auth.Rol;
import edu.upc.eetac.dsa.football.DAO.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.football.DAO.UserAlreadyExistsException;
import edu.upc.eetac.dsa.football.DAO.UserDAO;
import edu.upc.eetac.dsa.football.DAO.UserDAOImpl;
import edu.upc.eetac.dsa.football.entity.AuthToken;
import edu.upc.eetac.dsa.football.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by marc on 10/03/16.
 */
@Path("usuario")
public class UserResource {
    @Context
    private SecurityContext securityContext;


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FootballMediaType.football_USER)
    public Response registerUser(@FormParam("loginid") String loginid, @FormParam("password") String password, @FormParam("email") String email, @FormParam("balance") Float balance, @Context UriInfo uriInfo) throws URISyntaxException {
        if(loginid == null || password == null || email == null || balance == null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authToken = null;
        try{
            user = userDAO.createUser(loginid, password, email, balance);
            authToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(FootballMediaType.football_USER).entity(authToken).build();
    }

    @Path("/{id}")
    @GET
    @Produces(FootballMediaType.football_USER)
    public User getUser(@PathParam("id") String id) {
        User user = null;

        if(!new Rol().permisoAdmin_Usuario(id,securityContext))
            throw new ForbiddenException("operation not allowed");

        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with id = "+id+" doesn't exist");
        return user;
    }

    @Path("/{id}")
    @PUT
    @Consumes(FootballMediaType.football_USER)
    @Produces(FootballMediaType.football_USER)
    public User updateUser(@PathParam("id") String id, User user) {
        if(user == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(user.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!new Rol().permisoAdmin_Usuario(id,securityContext))
            throw new ForbiddenException("operation not allowed");


        UserDAO userDAO = new UserDAOImpl();
        try {
            user = userDAO.updateProfile(id, user.getEmail(), user.getBalance());
            if(user == null)
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return user;
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") String id){


        if(!new Rol().permisoAdmin_Usuario(id,securityContext))
            throw new ForbiddenException("operation not allowed");

        UserDAO userDAO = new UserDAOImpl();
        try {
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
