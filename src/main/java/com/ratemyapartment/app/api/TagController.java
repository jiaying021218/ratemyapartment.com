package com.ratemyapartment.app.api;


import com.ratemyapartment.app.model.Tag;
import com.ratemyapartment.app.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/tag")
public class TagController { //TODO: HTML

    @Autowired
    private TagService tagService;

    @GetMapping("/test")
    public String healthCheck() {
        return "<h1>TagController Health Check</h1>";
    }

    @GetMapping("")
    public List<Tag> getAllTag() {
        return tagService.getAllTag();
    }

    @PostMapping("")
    public void addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable Long id) {
        tagService.deleteTagById(id);
    }

    @PutMapping("/{id}/name")
    public void updateTagNameById(@PathVariable Long id, @RequestBody Tag tag) {
        tagService.updateTagNameById(id, tag.getName());
    }

    @PutMapping("/{id}/positive-negative")
    public void updateTagPositiveNegativeById(@PathVariable Long id, @RequestBody Tag tag) {
        tagService.updateTagPositiveNegativeById(id, tag.getPositive_Negative());
    }
}