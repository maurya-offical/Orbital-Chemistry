package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.AdminDtos.*;
import com.orbitalchemistry.repository.*;
import com.orbitalchemistry.service.UserAdminService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Dashboard numbers and student management. Protected by /api/admin/** in SecurityConfig. */
@RestController
@RequestMapping("/api/admin")
public class AdminStatsController {

    private final UserRepository users;
    private final CourseRepository courses;
    private final OrderRepository orders;
    private final ContactMessageRepository messages;
    private final UserAdminService userAdmin;

    public AdminStatsController(UserRepository users, CourseRepository courses, OrderRepository orders,
                                ContactMessageRepository messages, UserAdminService userAdmin) {
        this.users = users; this.courses = courses; this.orders = orders; this.messages = messages;
        this.userAdmin = userAdmin;
    }

    @GetMapping("/stats")
    public Stats stats() {
        return new Stats(users.countByRole("STUDENT"), courses.count(), courses.countByPublishedTrue(),
                orders.countByStatus("PAID"), orders.paidPaise() / 100, messages.countByHandledFalse());
    }

    @GetMapping("/users")
    public List<UserRow> users() { return userAdmin.list(); }

    @PutMapping("/users/{id}/role")
    public UserRow role(@PathVariable Long id, @Valid @RequestBody RoleRequest r, Authentication auth) {
        return userAdmin.setRole(id, r.role(), auth.getName());
    }

    @PutMapping("/users/{id}/blocked")
    public UserRow blocked(@PathVariable Long id, @RequestBody BlockRequest r, Authentication auth) {
        return userAdmin.setBlocked(id, r.blocked(), auth.getName());
    }
}
