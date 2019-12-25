package com.runyuanj.upload.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author: runyu
 * @date: 2019/12/24 17:53
 */
@Data
public class ServiceResponse {

    public ServiceResponse (HttpStatus status) {
        this.status = status.value();
    }

    public ServiceResponse (HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public ServiceResponse (HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    private Integer status;

    private String message;

    private Object data;

}
