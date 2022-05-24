package ru.itis.testwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.itis.testwork.models.Post;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Пост")
public class PostDto {

    private Long id;
    private String title;
    private String text;
    private String dateTime;

    public static List<PostDto> from(List<Post> content) {
        return content.stream().map(PostDto::from).collect(Collectors.toList());
    }

    public static PostDto from(Post post){
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .title(post.getTitle())
                .dateTime(post.getLastChanges().toString())
                .build();
    }
}
