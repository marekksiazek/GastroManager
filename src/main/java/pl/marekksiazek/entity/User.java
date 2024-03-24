package pl.marekksiazek.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import pl.marekksiazek.enums.UserRoleEnum;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
@Schema(name = "Users", description = "Users representation")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Schema(required = true)
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "role")
    private UserRoleEnum role;

    public Long getId() {
        return user_id;
    }
}
