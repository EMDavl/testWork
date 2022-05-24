package ru.itis.testwork.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.itis.testwork.models.Post;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Страница с постами")
public class PostPage {

    @SchemaProperty(name = "посты")
    private List<PostDto> posts;

    @Parameter(name = "Количество страниц", description = "Общее количество страниц. Отсчет с 0")
    private int totalPages;

    @Parameter(description = "Текущая страница")
    private int currentPage;

    public static PostPage from(Page<Post> posts) {
        return PostPage.builder()
                .totalPages(posts.getTotalPages())
                .currentPage(posts.getNumber())
                .posts(PostDto.from(posts.getContent()))
                .build();
    }
}
