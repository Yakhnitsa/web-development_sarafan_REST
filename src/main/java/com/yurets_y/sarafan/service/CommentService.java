package com.yurets_y.sarafan.service;

import com.yurets_y.sarafan.domain.Comment;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        return commentRepo.save(comment);
    }
}
