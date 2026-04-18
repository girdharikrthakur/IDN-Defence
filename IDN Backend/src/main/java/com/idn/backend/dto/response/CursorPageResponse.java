package com.idn.backend.dto.response;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CursorPageResponse<T> {
    private List<T> data;
    private Long nextCursor;
    private boolean hasMore;
}