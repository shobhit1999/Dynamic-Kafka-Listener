package com.dynamic.kafkalistener.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void createDate() {
        if (createdAt == null) {
            setCreatedAt(LocalDateTime.now());
        }
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    void updatedAt() {
        setUpdatedAt(LocalDateTime.now());
    }
}