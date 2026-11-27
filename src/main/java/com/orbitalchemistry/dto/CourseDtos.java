package com.orbitalchemistry.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public final class CourseDtos {
    private CourseDtos() {}

    // ----- requests (admin) -----
    public record CourseRequest(
            @NotBlank(message = "Title is required") @Size(max = 200) String title,
            @NotBlank(message = "Category is required") @Size(max = 40) String category,
            String description,
            @Min(value = 0, message = "Price cannot be negative") int price,
            @Min(value = 0, message = "Original price cannot be negative") Integer originalPrice,
            @Size(max = 20) String color,
            @Size(max = 10, message = "Badge must be 10 characters or fewer") String badge,
            @Pattern(regexp = "^(/uploads/[A-Za-z0-9._-]+|https://.+)$", message = "Invalid image address") @Size(max = 300) String thumbnailUrl,
            boolean published) {}

    public record SectionRequest(@NotBlank(message = "Section title is required") String title, Integer position) {}

    public record LessonRequest(
            @NotBlank(message = "Lesson title is required") String title,
            @NotBlank(message = "Video URL is required")
            @Pattern(regexp = "^https?://.+", message = "Video URL must start with http:// or https://")
            @Size(max = 500) String videoUrl,
            @Min(0) Integer durationMinutes,
            Integer position,
            boolean freePreview) {}

    public record EnrollRequest(
            @NotBlank @Email(message = "Enter a valid email") String email,
            @NotNull(message = "Course is required") Long courseId) {}

    // ----- responses -----
    public record CourseSummary(Long id, String title, String category, String description, int price,
                                Integer originalPrice, String color, String badge, String thumbnailUrl, boolean published, int lessonCount) {}

    public record LessonView(Long id, String title, int durationMinutes, int position, boolean freePreview,
                             String videoUrl, boolean locked) {}

    public record SectionView(Long id, String title, int position, List<LessonView> lessons) {}

    public record CourseDetail(Long id, String title, String category, String description, int price,
                               Integer originalPrice, String color, String badge, String thumbnailUrl, boolean published,
                               List<SectionView> sections) {}
}
