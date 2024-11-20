package com.capgemini.wsb.fitnesstracker.training.api;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainingUpdateDto {
    @Nullable
    public Long id;
    public double distance;
}