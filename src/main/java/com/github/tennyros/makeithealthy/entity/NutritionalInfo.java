package com.github.tennyros.makeithealthy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalInfo {

    @Column(name = "calories", nullable = false)
    private double calories;

    @Column(name = "proteins", nullable = false)
    private double proteins;

    @Column(name = "carbs", nullable = false)
    private double carbs;

    @Column(name = "fats", nullable = false)
    private double fats;
}
