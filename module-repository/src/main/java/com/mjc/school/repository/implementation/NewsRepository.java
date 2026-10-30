package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private final DataSource dataSource;

    public NewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNews();
    }

    @Override
    public NewsModel readById(Long id) {
        return dataSource.getNews().stream()
                .filter(news -> Objects.equals(news.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public NewsModel create(NewsModel entity) {
        long maxId = dataSource.getNews().stream()
                .mapToLong(NewsModel::getId)
                .max().orElse(0);

        entity.setId(maxId + 1);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateDate(now);
        entity.setLastUpdateDate(now);

        dataSource.getNews().add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel existingNews = readById(entity.getId());
        if (existingNews != null) {
            existingNews.setTitle(entity.getTitle());
            existingNews.setContent(entity.getContent());
            existingNews.setAuthorId(entity.getAuthorId());
            existingNews.setLastUpdateDate(LocalDateTime.now());
            return existingNews;
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return dataSource.getNews().removeIf(news -> Objects.equals(news.getId(), id));
    }
}