package org.project.model;

import lombok.Data;
import org.project.enums.PostType;

@Data
public class Comment extends BaseEntity {
    private String authorId;
    private String body;
    private PostType parentType;
    private String parentId;
}
