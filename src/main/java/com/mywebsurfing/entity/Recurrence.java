package com.mywebsurfing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean started;
    private ZonedDateTime lastStart;
    private ZonedDateTime lastFinish;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    @OneToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;
}
