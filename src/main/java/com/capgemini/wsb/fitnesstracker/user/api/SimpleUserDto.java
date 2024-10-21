package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record SimpleUserDto(@Nullable Long userId, String firstName, String lastName) {
}
