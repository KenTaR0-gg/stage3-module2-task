package com.mjc.school.service.implementation;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final String AUTHOR_ID_DOES_NOT_EXIST = "Author with id %d does not exist.";

    public void validateAuthorId(Long id) {
        if (id == null || id < 1) {
            throw new ValidatorException("Author id cannot be null or less than 1.", "VAL-001");
        }
    }

    public void validateAuthorDto(AuthorDtoRequest request) {
        if (request.name() == null|| request.name().length() < 3 || request.name().length() > 15) {
            throw new ValidatorException("err", "кот-2,1");
        }
    }

    public void validateNewsDto(NewsDtoRequest request) {
        if (request.getTitle() == null|| request.getTitle().length() < 5 || request.getTitle().length() > 30) {
            throw new ValidatorException("err", "кот-2,1");
        }
        if (request.getContent() == null|| request.getContent().length() < 5 || request.getContent().length() > 255) {
            throw new ValidatorException("err", "кот-2,1");
        }
        if (request.getAuthorId() == null) {
            throw new ValidatorException("err", "кот-2,1");
        }
    }
}