package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;

    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService) {
        this.authorService = authorService;
    }
    @CommandHandler(value = 6)
    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorService.readAll();
    }

    @CommandHandler(value = 7)
    @Override
    public AuthorDtoResponse readById(@CommandParam Long id) {
        return authorService.readById(id);
    }

    @CommandHandler(value = 8)
    @Override
    public AuthorDtoResponse create(@CommandBody AuthorDtoRequest createRequest) {
        return authorService.create(createRequest);
    }

    @CommandHandler(value = 9)
    @Override
    public AuthorDtoResponse update(@CommandBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }

    @CommandHandler(value = 10)
    @Override
    public Boolean delete(@CommandParam Long id) {
        return authorService.delete(id);
    }
}
