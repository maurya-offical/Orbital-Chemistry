package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.CourseDtos.*;
import com.orbitalchemistry.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** Everything under /api/admin requires ROLE_ADMIN (see SecurityConfig). */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CourseService service;
    public AdminController(CourseService service) { this.service = service; }

    // courses
    @GetMapping("/courses")
    public List<CourseSummary> all() { return service.listAll(); }

    @PostMapping("/courses") @ResponseStatus(HttpStatus.CREATED)
    public CourseSummary create(@Valid @RequestBody CourseRequest r) { return service.createCourse(r); }

    @PutMapping("/courses/{id}")
    public CourseSummary update(@PathVariable Long id, @Valid @RequestBody CourseRequest r) { return service.updateCourse(id, r); }

    @DeleteMapping("/courses/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteCourse(id); }

    @GetMapping("/courses/{id}")
    public CourseDetail detail(@PathVariable Long id, Authentication auth) { return service.detail(id, auth); }

    // sections
    @PostMapping("/courses/{id}/sections") @ResponseStatus(HttpStatus.CREATED)
    public CourseDetail addSection(@PathVariable Long id, @Valid @RequestBody SectionRequest r) { return service.addSection(id, r); }

    @PutMapping("/sections/{id}")
    public CourseDetail updateSection(@PathVariable Long id, @Valid @RequestBody SectionRequest r) { return service.updateSection(id, r); }

    @DeleteMapping("/sections/{id}")
    public CourseDetail deleteSection(@PathVariable Long id) { return service.deleteSection(id); }

    // lessons
    @PostMapping("/sections/{id}/lessons") @ResponseStatus(HttpStatus.CREATED)
    public CourseDetail addLesson(@PathVariable Long id, @Valid @RequestBody LessonRequest r) { return service.addLesson(id, r); }

    @PutMapping("/lessons/{id}")
    public CourseDetail updateLesson(@PathVariable Long id, @Valid @RequestBody LessonRequest r) { return service.updateLesson(id, r); }

    @DeleteMapping("/lessons/{id}")
    public CourseDetail deleteLesson(@PathVariable Long id) { return service.deleteLesson(id); }

    // manual enrollment
    @PostMapping("/enrollments") @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> enroll(@Valid @RequestBody EnrollRequest r) {
        service.enroll(r);
        return Map.of("message", "Student enrolled");
    }
}
