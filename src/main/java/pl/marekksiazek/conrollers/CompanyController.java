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
import pl.marekksiazek.repository.CompanyRepository;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;


@Path("/companies")
@Tag(name = "Company Controller", description = "Company REST API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyController {

    @Inject
    CompanyRepository companyRepository;

    @GET
    @Operation(
            operationId = "getCompanies",
            summary = "Get Company",
            description = "Get all companies in DB"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getAll(){
        List<Company> companies = companyRepository.listAll();
        return Response.ok(companies).build();
    }

    @GET
    @Path("{id}")
    @Operation(
            operationId = "getCompany",
            summary = "Get Company by Id",
            description = "Get company from DB using ID"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getById(@PathParam("id") Long id){
        return companyRepository.findByIdOptional(id)
                .map(company -> Response.ok(company).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @POST
    @Operation(
            operationId = "createCompany",
            summary = "Create a new Company in DB",
            description = "Create a new Company to add to DB"
    )
    @APIResponse(
            responseCode = "201",
            description = "Company created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Transactional
    public Response create (
            @RequestBody(
                    description = "Company to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Company.class))
            )
            Company company){
        companyRepository.persist(company);
        if (companyRepository.isPersistent(company)){
            return Response.created(URI.create("/companies/" + company.getId())).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(
            operationId = "deleteCompany",
            summary = "Delete existing Company from DB",
            description = "Delete a Company from DB"
    )
    @APIResponse(
            responseCode = "204",
            description = "Company deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Company not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = companyRepository.deleteById(id);

        return deleted ? Response.noContent().build() :
                Response.status(NOT_FOUND).build();
    }

}
