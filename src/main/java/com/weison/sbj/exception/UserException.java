package com.weison.sbj.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class UserException extends RuntimeException {

    private Integer code;

    public UserException() {
        super();
    }

    public UserException(int code, String message) {
        super(message);
        this.code = code;
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
