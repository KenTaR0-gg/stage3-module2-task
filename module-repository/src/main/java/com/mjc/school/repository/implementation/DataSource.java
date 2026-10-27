package com.mjc.school.repository.implementation;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSource {

    private final List<NewsModel> newsList = new ArrayList<>();
    private final List<AuthorModel> authorList = new ArrayList<>();

@PostConstruct
    public void init() {

        List<String> authorNames = readLinesFromResources("author.txt");
        List<String> titles = readLinesFromResources("news.txt");
        List<String> contents = readLinesFromResources("content.txt");

        for (long i = 0; i < authorNames.size(); i++) {
            authorList.add(new AuthorModel(i + 1, authorNames.get((int) i)));
        }

        Random random = new Random();
        for (long i = 1; i <= 20; i++) {
            String randomTitle = titles.get(random.nextInt(titles.size()));
            String randomContent = contents.get(random.nextInt(contents.size()));

            AuthorModel randomAuthor = authorList.get(random.nextInt(authorList.size()));

            NewsModel news = new NewsModel(
                    i,
                    randomTitle,
                    randomContent,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    randomAuthor.getId()
            );
            newsList.add(news);
        }
    }


    private List<String> readLinesFromResources(String fileName) {
        List<String> lines = new ArrayList<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Файл не найден: " + fileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public List<NewsModel> getNews() {
        return newsList;
    }

    public List<AuthorModel> getAuthors() {
        return authorList;
    }
}