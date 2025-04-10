package com.github.tennyros.makeithealthy.repository;

import com.github.tennyros.makeithealthy.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    boolean existsByName(String name);

}
