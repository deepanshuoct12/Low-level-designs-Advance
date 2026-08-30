package org.project.model;

import org.project.enums.ContentType;
import org.project.enums.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class Content extends BaseEntity {
    private String title;
    private String description;
    private ContentType type;
    private Long duration; // in seconds, nullable for SERIES/SEASON
    private String thumbnailUrl;
    private LocalDate releaseDate;
    private Genre genre;
    private Float rating;
    private String language;
}
