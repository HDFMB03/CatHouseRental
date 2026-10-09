package com.Cathouse.project.DataTransferObject;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class QueryDto {
    private Integer id;
    private Integer current;
    private Integer size;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

