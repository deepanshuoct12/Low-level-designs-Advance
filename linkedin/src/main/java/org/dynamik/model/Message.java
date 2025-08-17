package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message extends AbstractEntity {
    private String content;
    private String senderId;
    private String receiverId;
}
