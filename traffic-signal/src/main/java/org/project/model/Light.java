package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.enums.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Light {
    private boolean isOn;
    private Color color;
}
