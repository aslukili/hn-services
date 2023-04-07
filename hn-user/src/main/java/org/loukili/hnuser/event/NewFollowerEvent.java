package org.loukili.hnuser.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFollowerEvent {
    private String userUsername;
    private String message;
}
