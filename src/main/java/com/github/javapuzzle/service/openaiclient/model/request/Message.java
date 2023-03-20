package com.github.javapuzzle.service.openaiclient.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Message implements Serializable {
    private String role;
    private String content;
}
