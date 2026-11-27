package com.orbitalchemistry.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "course_id") public Course course;
    @Column(nullable = false) public String title;
    public int position;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC, id ASC")
    public List<Lesson> lessons = new ArrayList<>();
}
