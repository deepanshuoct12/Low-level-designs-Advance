package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Like extends AbstractEntity {
    private String postId;
    private String userId;
}
