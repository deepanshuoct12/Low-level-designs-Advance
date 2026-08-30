package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Menu extends BaseEntity {
    private String id;
    private String name;
}
