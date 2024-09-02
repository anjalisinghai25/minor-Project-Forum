package com.discussion.forum.service;

import com.discussion.forum.entities.Comment;
import com.discussion.forum.repository.CommentRepository;
import com.discussion.forum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("Error saving comment", e);
        }
    }

    public Comment getCommentById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(String id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting comment", e);
        }
    }
}
