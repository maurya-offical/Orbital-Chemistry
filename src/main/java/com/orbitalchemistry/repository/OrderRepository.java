package com.orbitalchemistry.repository;

import com.orbitalchemistry.model.CourseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<CourseOrder, Long> {
    Optional<CourseOrder> findByRazorpayOrderId(String razorpayOrderId);
    List<CourseOrder> findTop100ByOrderByIdDesc();
    boolean existsByCourseId(Long courseId);
    long countByStatus(String status);

    @Query("select coalesce(sum(o.amountPaise), 0) from CourseOrder o where o.status = 'PAID'")
    long paidPaise();

    /** Empty status / q mean "no filter". q matches student email, course title or coupon code. */
    @Query("""
            select o from CourseOrder o join o.user u join o.course c
            where (:status = '' or o.status = :status)
              and (:q = '' or lower(u.email) like lower(concat('%', :q, '%'))
                           or lower(c.title) like lower(concat('%', :q, '%'))
                           or lower(coalesce(o.couponCode, '')) like lower(concat('%', :q, '%')))
              and o.createdAt >= :from and o.createdAt < :to
            order by o.id desc""")
    Page<CourseOrder> search(@Param("status") String status, @Param("q") String q,
                             @Param("from") Instant from, @Param("to") Instant to, Pageable pageable);
}
