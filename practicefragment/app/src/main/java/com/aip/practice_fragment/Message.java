package com.aip.practice_fragment;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {

    private String message;
    private LocalDate dateSend;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }
}
