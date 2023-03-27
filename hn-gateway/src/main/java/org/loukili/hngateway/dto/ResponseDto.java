package org.loukili.hngateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private String status;
    private String message;
    private Object data;
    public ResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
