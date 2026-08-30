package org.project.model;

import lombok.Data;
import org.project.enums.PostType;
import org.project.enums.VoteType;

@Data
public class Vote extends BaseEntity {
    private String userId;
    private PostType targetType;
    private String targetId;
    private VoteType voteType;
}
