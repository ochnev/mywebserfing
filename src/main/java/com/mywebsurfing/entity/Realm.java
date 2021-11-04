package com.mywebsurfing.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Realm {
    @Id
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;
    @OneToMany(mappedBy = "realm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders;
}
