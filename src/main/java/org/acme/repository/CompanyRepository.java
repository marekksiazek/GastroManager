package org.acme.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Company;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {
}
