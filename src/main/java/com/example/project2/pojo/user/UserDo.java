package com.example.project2.pojo.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "account")
public class UserDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "account", length = 30, unique = true)
    private String account;

    @NotBlank
    @Column(name = "password", length = 100)
    private String password;

    @NotBlank
    @Column(name = "name", length = 50)
    private String name;

    /*@NotBlank
    @Column(name = "authorities", nullable = false)
    private List<UserAuthority> userAuthorities = new ArrayList<>();
     */
}
