package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.dynamik.observer.MovieSubject;

@Data
public class Movie extends MovieSubject {
    private String id;
    private String title;
    private String description;
    private String genre;
    private String language;
    private Long duration;
}
