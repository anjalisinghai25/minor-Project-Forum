package com.discussion.forum.service;

import com.discussion.forum.entities.Discussion;
import com.discussion.forum.repository.DiscussionRepository;
import com.discussion.forum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    public Discussion saveDiscussion(Discussion discussion) {
        try {
            return discussionRepository.save(discussion);
        } catch (Exception e) {
            throw new RuntimeException("Error saving discussion", e);
        }
    }

    public Discussion getDiscussionById(String id) {
        return discussionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discussion not found with id: " + id));
    }

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public void deleteDiscussion(String id) {
        if (!discussionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Discussion not found with id: " + id);
        }
        try {
            discussionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting discussion", e);
        }
    }
}
