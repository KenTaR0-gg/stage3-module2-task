package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class AuthorRepository implements BaseRepository <AuthorModel, Long> {

    private final DataSource dataSource;

    public AuthorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<AuthorModel> readAll() {
        return dataSource.getAuthors();
    }

    @Override
    public AuthorModel readById(Long id) {
        for(AuthorModel am : dataSource.getAuthors()){
            if(am.getId().equals(id)) {
                return am;
            }
        }
        return null;
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        long maxId = 0;
        for (AuthorModel am : dataSource.getAuthors()) {
            if (am.getId() > maxId) {
                maxId = am.getId();
            }
        }
        entity.setId(maxId + 1);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateDate(now);
        entity.setLastUpdateDate(now);

        dataSource.getAuthors().add(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel existingAuthor = readById(entity.getId());

        if (existingAuthor != null) {
            existingAuthor.setName(entity.getName());
            existingAuthor.setLastUpdateDate(LocalDateTime.now());
            return existingAuthor;
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return dataSource.getAuthors().removeIf(author -> author.getId().equals(id));
    }
}
