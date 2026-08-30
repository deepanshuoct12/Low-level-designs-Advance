package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.project.enums.PlayerType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Player extends BaseEntity {
    private String name;
    private Long teamId;
    private PlayerType playerType;
    private String dateOfBirth;
    private String nationality;
    private String imageUrl;
    private Integer jerseyNumber;
    private Boolean isActive;
}
