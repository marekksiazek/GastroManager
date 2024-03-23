package org.acme.conrollers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Company;
import org.acme.repository.CompanyRepository;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;


@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyController {

    @Inject
    CompanyRepository companyRepository;

    @GET
    public Response getAll(){
        List<Company> companies = companyRepository.listAll();
        return Response.ok(companies).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return companyRepository.findByIdOptional(id)
                .map(company -> Response.ok(company).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create (Company company){
        companyRepository.persist(company);
        if (companyRepository.isPersistent(company)){
            return Response.created(URI.create("/companies/" + company.getId())).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = companyRepository.deleteById(id);

        return deleted ? Response.noContent().build() :
                Response.status(NOT_FOUND).build();
    }

}
