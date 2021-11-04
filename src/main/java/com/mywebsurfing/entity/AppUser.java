package com.mywebsurfing.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class AppUser {
    @Id
    private Long id;
    private String nickname;
    private String login;
    private String password;
    private String email;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Realm> realms;
}
