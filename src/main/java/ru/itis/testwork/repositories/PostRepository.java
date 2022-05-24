package ru.itis.testwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.testwork.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
