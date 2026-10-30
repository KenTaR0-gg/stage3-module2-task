package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-31T01:34:30+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.5.0.2 (Microsoft)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDtoResponse modelToDto(AuthorModel model) {
        if ( model == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        LocalDateTime createDate = null;
        LocalDateTime lastUpdateDate = null;

        id = model.getId();
        name = model.getName();
        createDate = model.getCreateDate();
        lastUpdateDate = model.getLastUpdateDate();

        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse( id, name, createDate, lastUpdateDate );

        return authorDtoResponse;
    }

    @Override
    public AuthorModel dtoToModel(AuthorDtoRequest request) {
        if ( request == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( request.id() );
        authorModel.setName( request.name() );

        return authorModel;
    }
}
