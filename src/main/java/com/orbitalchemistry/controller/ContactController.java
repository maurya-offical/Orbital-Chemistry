package com.orbitalchemistry.controller;

import com.orbitalchemistry.dto.FeatureDtos.*;
import com.orbitalchemistry.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class ContactController {

    private final ContactService service;
    public ContactController(ContactService service) { this.service = service; }

    @PostMapping("/api/contact") @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> submit(@Valid @RequestBody ContactRequest r) {
        service.submit(r);
        return Map.of("message", "Message received");
    }

    @GetMapping("/api/admin/messages")
    public List<ContactView> list() { return service.list(); }

    @PutMapping("/api/admin/messages/{id}/handled")
    public ContactView toggle(@PathVariable Long id) { return service.toggleHandled(id); }

    @DeleteMapping("/api/admin/messages/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
