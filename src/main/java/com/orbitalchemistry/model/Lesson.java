package com.orbitalchemistry.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "section_id") public Section section;
    @Column(nullable = false) public String title;
    @Column(nullable = false, length = 500) public String videoUrl;
    public int durationMinutes;
    public int position;
    public boolean freePreview;
}
