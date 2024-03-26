package pl.marekksiazek.entity;


import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


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
    private String role;

    @Column(name = "company_id")
    private Long companyId;


    public Long getId() {
        return user_id;
    }

    public void setUserPwd(String userPwd){
        this.userPwd = BcryptUtil.bcryptHash(userPwd);
    }

}
