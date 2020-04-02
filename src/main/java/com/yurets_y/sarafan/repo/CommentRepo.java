package com.yurets_y.sarafan.repo;

import com.yurets_y.sarafan.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
