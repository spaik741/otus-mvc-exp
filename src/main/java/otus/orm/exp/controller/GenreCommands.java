package otus.orm.exp.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.orm.exp.entity.Genre;
import otus.orm.exp.service.GenresService;
import otus.orm.exp.service.io.IOService;

import java.util.List;
import java.util.Optional;


public class GenreCommands {

    private final GenresService genresService;
    private final IOService ioService;

    public GenreCommands(GenresService genresService, IOService ioService) {
        this.genresService = genresService;
        this.ioService = ioService;
    }


    public void save(long id, String name) {
        Genre genre = new Genre(id, name);
        try {
            genresService.saveGenre(genre);
            ioService.printString("Жанр сохранен. " + genre);
        }catch (Exception e){
            ioService.printString("Жанр не сохранен. ");
        }
    }


    public void get(long id) {
        Optional<Genre> genre = genresService.getGenreById(id);
        String answerText = genre.map(value -> "Жанр получен. " + value).orElse("Жанр не получен.");
        ioService.printString(answerText);
    }


    public void getAll() {
        List<Genre> genres = genresService.getAllGenres();
        if (CollectionUtils.isNotEmpty(genres)) {
            ioService.printString("Жанры получены. " + genres);
        } else {
            ioService.printString("Жанры не найдены.");
        }
    }


    public void delete(long id) {
        try {
            genresService.deleteGenre(id);
            ioService.printString(String.format("Жанр № [%s] удален. ", id));
        }catch (Exception e){
            ioService.printString("Жанр не удален. ");
        }
    }
}
