package com.discussion.forum.service;

import com.discussion.forum.entities.Material;
import com.discussion.forum.repository.MaterialRepository;
import com.discussion.forum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public Material saveMaterial(Material material) {
        try {
            return materialRepository.save(material);
        } catch (Exception e) {
            throw new RuntimeException("Error saving material", e);
        }
    }

    public Material getMaterialById(String id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + id));
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public void deleteMaterial(String id) {
        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material not found with id: " + id);
        }
        try {
            materialRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting material", e);
        }
    }
}
