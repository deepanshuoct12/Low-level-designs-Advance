package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.enums.ContentType;
import org.project.enums.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content extends BaseEntity {
    private Long id;
    private String title;
    private String description;
    private Long duration;
    private ContentType contentType;
    private Genre genre;
}
