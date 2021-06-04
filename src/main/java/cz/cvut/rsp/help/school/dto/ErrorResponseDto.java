package cz.cvut.rsp.help.school.dto;

import java.util.Date;


public class ErrorResponseDto {

    private final Date date;

    private final String error;

    private final Object data;


    public ErrorResponseDto(String error, Object data) {
        this.date = new Date();
        this.error = error;
        this.data = data;
    }


    public Date getDate() {
        return date;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

}
