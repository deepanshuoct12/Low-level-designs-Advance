package org.project.model;

import lombok.Data;
import org.project.enums.Tag;

import java.util.List;

@Data
public class Question extends BaseEntity {
    private String authorId;
    private String title;
    private String body;
    private List<Tag> tags;
    private String acceptedAnswerId;
}
