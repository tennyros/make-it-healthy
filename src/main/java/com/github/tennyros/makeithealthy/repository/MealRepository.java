package com.github.tennyros.makeithealthy.repository;

import com.github.tennyros.makeithealthy.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    boolean existsByName(String name);

    @Query("SELECT SUM(m.nutritionalInfo.calories) FROM Meal m WHERE m.id IN :ids")
    Double sumCaloriesByIds(@Param("ids") List<Integer> mealIds);

}
