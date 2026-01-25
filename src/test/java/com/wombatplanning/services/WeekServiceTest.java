package com.wombatplanning.services;

import com.wombatplanning.models.entities.User;
import com.wombatplanning.models.entities.UserFactory;
import com.wombatplanning.models.entities.Week;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class WeekServiceTest {

    private final WeekService weekService = new WeekService();

    @Test
    void createWeeksFor() {
        User user = UserFactory.createUser();

        weekService.createWeeksFor(user);

        Set<Week> userWeekSet = user.getWeekSet();

        Week week1 = Week.create(user, 1, 2026);
        Week week2 = Week.create(user, 2, 2026);

        assertTrue(userWeekSet.contains(week1));
        assertTrue(userWeekSet.contains(week2));
        assertEquals(52, userWeekSet.size());

        int index = 1;
        for (Week w : userWeekSet) {
            assertTrue(w.getWeekNumber() == index);
            index++;
            assertEquals(2026, w.getYear());
        }


    }
}