package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.AdminDtos.OrderPage;
import com.orbitalchemistry.service.OrderAdminService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class AdminOrderController {

    private final OrderAdminService service;
    public AdminOrderController(OrderAdminService service) { this.service = service; }

    /** Example: /api/admin/orders?q=diwali&status=PAID&from=2026-11-01&to=2026-11-30&page=0&size=20 */
    @GetMapping("/api/admin/orders")
    public OrderPage search(@RequestParam(defaultValue = "") String q,
                            @RequestParam(defaultValue = "") String status,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size) {
        return service.search(q, status, from, to, page, size);
    }
}
