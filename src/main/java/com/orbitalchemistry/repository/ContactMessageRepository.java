package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findAllByOrderByIdDesc();
    long countByHandledFalse();
}
