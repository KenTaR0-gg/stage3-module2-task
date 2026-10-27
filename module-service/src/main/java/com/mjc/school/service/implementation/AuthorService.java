package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.mapper.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final AuthorMapper authorMapper;
    private final Validator validator;

    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository, AuthorMapper authorMapper, Validator validator) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.validator = validator;
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        validator.validateAuthorDto(createRequest);
        AuthorModel model = authorMapper.dtoToModel(createRequest);
        AuthorModel savedModel = authorRepository.create(model);
        return authorMapper.modelToDto(savedModel);
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        List<AuthorModel> models = new ArrayList<>(authorRepository.readAll());
        List<AuthorDtoResponse> resp = new ArrayList<>();
        for (AuthorModel am : models) {
            AuthorDtoResponse kot = authorMapper.modelToDto(am);
            resp.add(kot);
        }
     return resp;
    }

    @Override
    public AuthorDtoResponse readById(Long id) {

        AuthorModel am = authorRepository.readById(id);
        if (am != null) return authorMapper.modelToDto(am);
        throw new ValidatorException("Author with id " + id + " does not exist.", "котя-1.1");
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        validator.validateAuthorDto(updateRequest);
        if (authorRepository.readById(updateRequest.id()) == null) {
            throw new ValidatorException("Author with id " + updateRequest.id() + " does not exist.", "котя-1.1");
        }
        AuthorModel model = authorMapper.dtoToModel(updateRequest);
        AuthorModel updatedModel = authorRepository.update(model);
        return authorMapper.modelToDto(updatedModel);
    }

    @Override
    public Boolean delete(Long id) {
        if (authorRepository.readById(id) == null) {
            throw new ValidatorException("News with id " + id + " does not exist.", "котя-1.1");
        }
        return authorRepository.delete(id);
    }
}