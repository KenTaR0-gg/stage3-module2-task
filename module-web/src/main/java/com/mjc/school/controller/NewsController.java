package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
        this.newsService = newsService;
    }

    @CommandHandler(value = 1)
    @Override
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @CommandHandler(value = 2)
    @Override
    public NewsDtoResponse readById(@CommandParam Long id) {
        return newsService.readById(id);
    }

    @CommandHandler(value = 3)
    @Override
    public NewsDtoResponse create(@CommandBody NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @CommandHandler(value = 4)
    @Override
    public NewsDtoResponse update(@CommandBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @CommandHandler(value = 5)
    @Override
    public Boolean delete(@CommandParam Long id) {
        return newsService.delete(id);
    }
}