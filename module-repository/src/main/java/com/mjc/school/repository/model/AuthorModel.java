package com.mjc.school.repository.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthorModel implements BaseEntity<Long> {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public AuthorModel() {
    }

    public AuthorModel(String name, Long id, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.id = id;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public AuthorModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime now) {
    }

    public void setLastUpdateDate(LocalDateTime now) {
    }
}