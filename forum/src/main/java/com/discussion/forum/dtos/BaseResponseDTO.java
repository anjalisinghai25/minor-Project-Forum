package com.discussion.forum.dtos;

import com.discussion.forum.dtos.response.PageResposne;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDTO {

    private Object data;
    private String message;
    private Integer status;
    private Long totalElements;
    private Integer pages;
    private Map<String, Object> extras;
    private Map<String, Object> errors;

    public BaseResponseDTO(Object data, String message) {
        this.data = data;
        this.message = message;
        this.status = 200;
    }


    public BaseResponseDTO(Object data, String message, Integer status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public BaseResponseDTO(Object data, Integer status) {
        this.data = data;
        this.status = status;
    }

    public BaseResponseDTO(Object data, String message, Map<String, Object> extras) {
        this.data = data;
        this.message = message;
        this.status = 200;
        this.extras = extras;
    }

    public BaseResponseDTO(Object data, String message, Page<?> pageData) {
        this.data = data;
        this.message = message;
        this.status = 200;
        this.totalElements = pageData.getTotalElements();
        this.pages = pageData.getTotalPages();
    }

    public BaseResponseDTO(String message, Page<?> pageData) {
        this.data = pageData.getContent();
        this.message = message;
        this.status = 200;
        this.totalElements = pageData.getTotalElements();
        this.pages = pageData.getTotalPages();
    }

    public BaseResponseDTO(String message, PageResposne pageResposne) {
        this.data = pageResposne.getData();
        this.message = message;
        this.status = 200;
        this.totalElements = pageResposne.getTotalElements();
        this.pages = pageResposne.getTotalPages() != null ? pageResposne.getTotalPages().intValue() : null;
    }

    public BaseResponseDTO(Object data, String message, PageResposne pageResposne) {
        this.data = data;
        this.message = message;
        this.status = 200;
        this.totalElements = pageResposne.getTotalElements();
        this.pages = pageResposne.getTotalPages() != null ? pageResposne.getTotalPages().intValue() : null;
    }

    public ResponseEntity<BaseResponseDTO> createResponseEntity() {
        return ResponseEntity.status(this.status != null ? this.status : 200).body(this);
    }
}
