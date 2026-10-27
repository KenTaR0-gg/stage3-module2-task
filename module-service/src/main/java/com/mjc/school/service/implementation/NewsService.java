package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final NewsMapper newsMapper;
    private final Validator validator;

    public NewsService(BaseRepository<NewsModel, Long> newsRepository, NewsMapper newsMapper, Validator validator) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.validator = validator;
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        validator.validateNewsDto(createRequest);
        NewsModel model = newsMapper.dtoToModel(createRequest);
        NewsModel savedModel = newsRepository.create(model);
        return newsMapper.modelToDto(savedModel);
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        List<NewsModel> models = new ArrayList<>(newsRepository.readAll());
        List<NewsDtoResponse> resp = new ArrayList<>();
        for (NewsModel nm : models) {
            NewsDtoResponse kot = newsMapper.modelToDto(nm);
            resp.add(kot);
        }
        return resp;
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        NewsModel nm = newsRepository.readById(id);
        if (nm != null) return newsMapper.modelToDto(nm);
        throw new ValidatorException("News with id " + id + " does not exist.", "котя-1.1");
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        validator.validateNewsDto(updateRequest);
        if (newsRepository.readById(updateRequest.getId()) == null) {
            throw new ValidatorException("News with id " + updateRequest.getId() + " does not exist.", "котя-1.1");
        }

        NewsModel model = newsMapper.dtoToModel(updateRequest);
        NewsModel savedModel = newsRepository.update(model);
        return newsMapper.modelToDto(savedModel);
    }

    @Override
    public Boolean delete(Long id) {
        if (newsRepository.readById(id) == null) {
            throw new ValidatorException("News with id " + id + " does not exist.", "котя-1.1");
        }
        return newsRepository.delete(id);
    }
}