package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment extends AbstractEntity {
    private String post_id;
    private String user_id;
    private String content;
}
