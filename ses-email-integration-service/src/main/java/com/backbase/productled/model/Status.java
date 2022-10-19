package com.backbase.productled.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Status {
    private String ref;
    private String status;
    private Error error;

    public Status(String status) {
        this.status = status;
    }
}

