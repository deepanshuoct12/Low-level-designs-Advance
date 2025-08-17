package org.dynamik.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractEntity {
    private String userId;
    private String title;
    private String content;
}
