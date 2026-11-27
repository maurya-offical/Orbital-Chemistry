package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.CourseDtos.*;
import com.orbitalchemistry.service.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;
    public CourseController(CourseService service) { this.service = service; }

    @GetMapping
    public List<CourseSummary> list() { return service.listPublished(); }

    @GetMapping("/{id}")
    public CourseDetail detail(@PathVariable Long id, Authentication auth) { return service.detail(id, auth); }
}
