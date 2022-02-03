package hello.vuetilserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    private boolean success;
    private int status;
    private String message;
    private LocalDateTime timeStamp;
}
