package com.github.tennyros.makeithealthy.repository;

import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {

    List<FoodIntake> findByUserAndDate(@Param("user") User user, @Param("date") LocalDate date);
    List<FoodIntake> findByUserAndDateBetween(@Param("user") User user,
                                              @Param("date") LocalDate startDate,
                                              @Param("date") LocalDate endDate);

}
