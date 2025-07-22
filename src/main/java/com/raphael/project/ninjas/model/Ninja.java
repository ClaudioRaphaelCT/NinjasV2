package com.raphael.project.ninjas.model;

import com.raphael.project.ninjas.enums.NinjaRanks;
import com.raphael.project.ninjas.enums.NinjaVilas;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ninjas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ninja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String nome;
    private NinjaVilas vila;
    private NinjaRanks rank;
    private Double poder;

    @PrePersist
    protected void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
