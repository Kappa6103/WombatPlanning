package com.wombatplanning.services;

import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeekService {

    private static final int NUMBER_OF_WEEKS_IN_YEAR = 52;

    public void createWeeksFor(User user) {
        int yearNumber = LocalDate.now().getYear();
        for (int i = 1; i <= NUMBER_OF_WEEKS_IN_YEAR; i++) {
            user.addWeek(Week.create(user, i, yearNumber));
        }
    }

}
