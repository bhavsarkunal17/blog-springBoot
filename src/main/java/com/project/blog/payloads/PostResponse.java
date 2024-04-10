package com.project.blog.payloads;

import com.project.blog.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private List<PostDto> content;
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
}
