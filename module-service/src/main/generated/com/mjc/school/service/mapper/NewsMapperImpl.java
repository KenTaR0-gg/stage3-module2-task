package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-31T01:34:30+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.5.0.2 (Microsoft)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDtoResponse modelToDto(NewsModel model) {
        if ( model == null ) {
            return null;
        }

        NewsDtoResponse newsDtoResponse = new NewsDtoResponse();

        newsDtoResponse.setId( model.getId() );
        newsDtoResponse.setTitle( model.getTitle() );
        newsDtoResponse.setContent( model.getContent() );
        newsDtoResponse.setCreateDate( model.getCreateDate() );
        newsDtoResponse.setLastUpdateDate( model.getLastUpdateDate() );
        newsDtoResponse.setAuthorId( model.getAuthorId() );

        return newsDtoResponse;
    }

    @Override
    public NewsModel dtoToModel(NewsDtoRequest request) {
        if ( request == null ) {
            return null;
        }

        NewsModel newsModel = new NewsModel();

        newsModel.setId( request.getId() );
        newsModel.setTitle( request.getTitle() );
        newsModel.setContent( request.getContent() );
        newsModel.setAuthorId( request.getAuthorId() );

        return newsModel;
    }
}
