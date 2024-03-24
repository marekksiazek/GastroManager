package pl.marekksiazek.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pl.marekksiazek.entity.Company;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {
}
