package pl.marekksiazek.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pl.marekksiazek.entity.User;

@ApplicationScoped
public class UsersRepository implements PanacheRepository<User> {
}
