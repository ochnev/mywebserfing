package com.mywebsurfing.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Data
@Entity
public class Recurrence {
    @Id
    private Long id;
    private Boolean started;
    private ZonedDateTime lastStart;
    private ZonedDateTime lastFinish;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;
    @OneToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;
}
