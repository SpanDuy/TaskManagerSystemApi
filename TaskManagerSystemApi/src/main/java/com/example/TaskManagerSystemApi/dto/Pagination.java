package com.example.TaskManagerSystemApi.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class Pagination {
    private Integer page;
    private Integer size;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
