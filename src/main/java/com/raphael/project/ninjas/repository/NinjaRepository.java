package com.raphael.project.ninjas.repository;

import com.raphael.project.ninjas.model.Ninja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NinjaRepository extends JpaRepository<Ninja, Long> {
}
