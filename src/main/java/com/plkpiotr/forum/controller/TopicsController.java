package com.plkpiotr.forum.controller;

import com.plkpiotr.forum.entity.Topic;
import com.plkpiotr.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicsController {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicsController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping("topics")
    public String displayAllTopics(Model model) {
        List<Topic> topics = topicRepository.findAll(new Sort(Sort.Direction.ASC, "lastModifiedDate"));
        String header = setHeader("all");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        return "topics";
    }

    @GetMapping("topics/{category}")
    public String displayTopicsByCategory(@PathVariable String category, Model model) {
        List<Topic> topics = topicRepository.findTopicsByCategoryOrderByLastModifiedDateDesc(category);
        String header = setHeader(category);
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        return "topics";
    }

    @GetMapping("topics/user/{id}")
    public String displayTopicsByUser(@PathVariable String id, Model model) {
        List<Topic> topics = topicRepository.findTopicsByUser_IdOrderByLastModifiedDateDesc(Long.valueOf(id));
        String header = setHeader("user");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        return "topics";
    }

    private String setHeader(String category) {
        switch (category) {
            case "se":
                return "Java Standard Edition";
            case "ee":
                return "Java Enterprise Edition";
            case "jpa":
                return "Java Persistence API & Hibernate";
            case "spring":
                return "Spring Framework";
            case "web":
                return "HTML/CSS/JavaScript";
            case "all":
                return "All topics";
            default:
                return "User's topics";
        }
    }
}