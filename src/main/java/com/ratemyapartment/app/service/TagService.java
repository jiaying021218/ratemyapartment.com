package com.ratemyapartment.app.service;


import com.ratemyapartment.app.model.Tag;
import com.ratemyapartment.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public void addTag(Tag tag) {
        if (tag.getName() == null) {
            throw new IllegalArgumentException("Tag name cannot be null");
        }
        if (tag.getPositive_Negative() == null) {
            throw new IllegalArgumentException("Tag positive_negative cannot be null");
        }
        isValidPositive_Negative(tag.getPositive_Negative());
        tagRepository.save(tag);
    }

    public Tag getTagById(Long id) {
        return tagRepository.getTagById(id);
    }

    public void updateTagNameById(Long id, String name) {
        if (Objects.equals(tagRepository.getTagById(id).getName(), name)) {
            throw new IllegalArgumentException("Tag name is the same");
        }
        tagRepository.updateTagNameById(id, name);
    }

    public void updateTagPositiveNegativeById(Long id, String positive_negative) {
        if (Objects.equals(tagRepository.getTagById(id).getPositive_Negative(), positive_negative)) {
            throw new IllegalArgumentException("Tag positive_negative is the same");
        }
        isValidPositive_Negative(positive_negative);
        tagRepository.updateTagPositiveNegativeById(id, positive_negative);
    }

    public void deleteTagById(Long id) {
        tagRepository.delete(tagRepository.getTagById(id));
    }

    ///////////////////////////////////////////////////////////////////////////
    private void isValidPositive_Negative(String positive_negative) {
        if (!positive_negative.equals("Positive") && !positive_negative.equals("Negative")) {
            throw new IllegalArgumentException("Tag positive_negative must be Positive or Negative");
        }
    }
}
