package pl.marekksiazek.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="company")
@Schema(name = "Company", description = "Company representation")
public class Company{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(required = true)
    @Column(name="nip")
    private String nip;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "homeNumber")
    private String homeNumber;

    @Column(name = "localNumber")
    private int localNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "password")
    private String password;

    @Column(name = "workers")
    private String[] workers;


}
