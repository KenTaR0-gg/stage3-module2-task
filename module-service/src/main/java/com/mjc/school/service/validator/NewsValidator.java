package com.mjc.school.service.validator;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exception.ValidatorException;

public class NewsValidator {

    public void validateNewsDto(NewsDtoRequest request) {
        if (request.getTitle() == null || request.getTitle().length() < 5 || request.getTitle().length() > 30) {
            throw new ValidatorException("ERROR-001", "News title can not be less than 5 and more than 30 symbols.");
        }
        if (request.getContent() == null || request.getContent().length() < 5 || request.getContent().length() > 255) {
            throw new ValidatorException("ERROR-002", "News content can not be less than 5 and more than 255 symbols.");
        }
    }
}