package pl.marekksiazek.conrollers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import pl.marekksiazek.entity.Company;
import pl.marekksiazek.entity.User;
import pl.marekksiazek.repository.UsersRepository;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/users")
@Tag(name = "Users Controller", description = "Users REST API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersController {

    @Inject
    UsersRepository usersRepository;

    @GET
    @Operation(
            operationId = "getUsers",
            summary = "Get Users list",
            description = "Get all users from DB"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getAll(){
        List<User> users = usersRepository.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("{id}")
    @Operation(
            operationId = "getUserById",
            summary = "Get user by ID",
            description = "Get single user from DB"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getById(@PathParam("id") Long id) {
        return usersRepository.findByIdOptional(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @POST
    @Operation(
            operationId = "createUser",
            summary = "Create a new User in DB",
            description = "Create a new User to add to DB"
    )
    @APIResponse(
            responseCode = "201",
            description = "User created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Transactional
    public Response create(
            @RequestBody(
                    description = "User to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = User.class))
            )User user){
        usersRepository.persist(user);
        if (usersRepository.isPersistent(user)){
            return Response.created(URI.create("/users/" + user.getId())).build();
        }
        return Response.status(BAD_REQUEST).build();
    }


    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(
            operationId = "deleteUser",
            summary = "Delete existing User from DB",
            description = "Delete a User from DB"
    )
    @APIResponse(
            responseCode = "204",
            description = "User deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "User not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = usersRepository.deleteById(id);

        return Response.status(NOT_FOUND).build();
    }
}
