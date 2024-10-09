package com.discussion.forum.dtos;

import com.discussion.forum.constants.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class AuditDTO {
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_TIME_FORMAT, timezone = Constant.TIME_ZONE)
    private LocalDateTime createdOn;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_TIME_FORMAT, timezone = Constant.TIME_ZONE)
    private LocalDateTime lastUpdatedOn;
}
