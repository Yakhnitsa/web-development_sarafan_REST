package com.yurets_y.sarafan.service;

import com.yurets_y.sarafan.domain.Comment;
import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.dto.EventType;
import com.yurets_y.sarafan.dto.ObjectType;
import com.yurets_y.sarafan.repo.CommentRepo;
import com.yurets_y.sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    private final BiConsumer<EventType,Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo,  WsSender sender) {
        this.commentRepo = commentRepo;
        this.wsSender = sender.getSender(ObjectType.COMMENT,Views.FullComment.class);
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        Comment commentFromDB = commentRepo.save(comment);
        wsSender.accept(EventType.CREATE,commentFromDB);
        return commentFromDB;
    }
}
