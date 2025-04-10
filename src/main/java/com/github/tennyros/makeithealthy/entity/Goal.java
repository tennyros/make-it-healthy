package com.github.tennyros.makeithealthy.entity;

public enum Goal {

    WEIGHT_LOSS {
        @Override
        public double calculateAdjustedCalories(double bmr) {
            return bmr * 0.85;
        }
    },
    MAINTENANCE {
        @Override
        public double calculateAdjustedCalories(double bmr) {
            return bmr;
        }
    },
    MUSCLE_GAIN {
        @Override
        public double calculateAdjustedCalories(double bmr) {
            return bmr * 1.15;
        }
    };

    public abstract double calculateAdjustedCalories(double bmr);
}
