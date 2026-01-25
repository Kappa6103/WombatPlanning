package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.UserChecker;
import com.wombatplanning.models.constraints.ConstrainedWeekNumberChecker;
import com.wombatplanning.models.constraints.YearChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.TreeSet;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "weeks")
public class Week implements Comparable<Week> {

    // FIELDS

    @Id
    @Column(name = "week_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "is_holiday", nullable = false)
    private boolean isHoliday;

    @OneToMany(mappedBy = "week", fetch = FetchType.LAZY)
    private Set<ScheduledTask> scheduledTaskTreeSet = new TreeSet<>();

    // FACTORY

    public static Week create(User user, Integer weekNumber, Integer year) {
        Week week = new Week();
        week.setUser(user);
        week.setWeekNumber(weekNumber);
        week.setYear(year);
        week.setIsHoliday(false);
        user.addWeek(week);
        return week;
    }

    // GETTERS

    public Long getId() {
        return this.id;
    }

    public Integer getWeekNumber() {
        return this.weekNumber;
    }

    public Integer getYear() {
        return this.year;
    }

    // MUTATORS

    private void setUser(User user) {
        UserChecker.requireValidUser(user);
        this.user = user;
    }

    private void setWeekNumber(Integer weekNumber) {
        ConstrainedWeekNumberChecker.requireValidWeekNumber(weekNumber);
        this.weekNumber = weekNumber;
    }

    private void setIsHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    private void setYear(Integer year) {
        YearChecker.requireValidYear(year);
        this.year = year;
    }

    public void addScheduledTask(ScheduledTask scheduledTask) {
        this.scheduledTaskTreeSet.add(scheduledTask);
    }

    @Override
    public int compareTo(Week o) {
        return Integer.compare(this.weekNumber, o.getWeekNumber());
    }
}
