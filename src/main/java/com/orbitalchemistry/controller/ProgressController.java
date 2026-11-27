package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.FeatureDtos.*;
import com.orbitalchemistry.service.ProgressService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService service;
    public ProgressController(ProgressService service) { this.service = service; }

    @GetMapping("/summary")
    public List<ProgressSummary> summary(Authentication auth) { return service.summary(auth.getName()); }

    @GetMapping("/{courseId}")
    public ProgressView get(@PathVariable Long courseId, Authentication auth) { return service.get(auth.getName(), courseId); }

    @PostMapping("/lessons/{lessonId}")
    public ProgressView set(@PathVariable Long lessonId, @RequestBody ProgressRequest r, Authentication auth) {
        return service.set(auth.getName(), lessonId, r.completed());
    }
}
