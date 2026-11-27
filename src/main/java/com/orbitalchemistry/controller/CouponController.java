package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.CouponDtos.*;
import com.orbitalchemistry.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CouponController {

    private final CouponService service;
    public CouponController(CouponService service) { this.service = service; }

    /** Logged-in students check a code; /api/admin/** is protected in SecurityConfig. */
    @PostMapping("/api/coupons/validate")
    public PriceQuote validate(@Valid @RequestBody ValidateRequest r) { return service.validate(r); }

    @GetMapping("/api/admin/coupons")
    public List<CouponView> list() { return service.listAll(); }

    @PostMapping("/api/admin/coupons") @ResponseStatus(HttpStatus.CREATED)
    public CouponView create(@Valid @RequestBody CouponRequest r) { return service.create(r); }

    @PutMapping("/api/admin/coupons/{id}")
    public CouponView update(@PathVariable Long id, @Valid @RequestBody CouponRequest r) { return service.update(id, r); }

    @DeleteMapping("/api/admin/coupons/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
