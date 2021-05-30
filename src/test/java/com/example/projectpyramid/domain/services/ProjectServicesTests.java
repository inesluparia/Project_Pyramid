package com.example.projectpyramid.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.InputMismatchException;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;
class ProjectServicesTests {

    private ProjectServices projectServices;

    @BeforeEach
    void initialize(){
        projectServices = new ProjectServices();
    }

    //Testing getTotalCost()
    @Test
    void getTotalCostSuccessTest() {
        int cost = projectServices.getTotalCost(2);
        assertEquals(500, cost);
    }
    @Test
    void getTotalCostFailTest() {
        int cost = projectServices.getTotalCost(4);
        assertFalse(cost == 250);
    }
    @Test
    void getTotalCostManyManHoursFailTest() {
        assertThrows(InputMismatchException.class, () -> {
            projectServices.getTotalCost(1000001);
        });
    }
    @Test
    void getTotalCostManyManHoursPassTest() {
        int cost = projectServices.getTotalCost(1000000);
        assertTrue(cost == 250000000);
    }
    @Test
    void getTotalCostNegativeManHoursTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            projectServices.getTotalCost(-2);
        });
    }

    //Testing getCompletionDate()
    @Test
    void getCompletionDateSuccessTest() throws Exception {
        LocalDate today = LocalDate.now();
        projectServices.setAmountOfProgrammers(1);
        LocalDate completionDate = projectServices.getCompletionDate(35);
        assertEquals(completionDate, today.plusWeeks(1));
    }

    @Test
    void getCompletionDateSuccessTest2() throws Exception {
        LocalDate today = LocalDate.now();
        projectServices.setAmountOfProgrammers(1);
        LocalDate completionDate = projectServices.getCompletionDate(42);
        assertEquals(completionDate, today.plusWeeks(1).plusDays(1));
    }

    @Test
    void getCompletionDateFailTest() throws Exception {
        LocalDate today = now();
        projectServices.setAmountOfProgrammers(1);
        LocalDate completionDate = projectServices.getCompletionDate(35);
        assertFalse(today == completionDate);

    }
    @Test
    void getCompletionDateManyManHoursFailTest() {
        assertThrows(InputMismatchException.class, () -> {
            projectServices.getCompletionDate(1000001);
        });
    }
    @Test
    void getCompletionDateManyManHoursPassTest() {
        LocalDate today = now();
        projectServices.setAmountOfProgrammers(1);
        LocalDate completionDate = projectServices.getCompletionDate(700000);
        assertEquals(completionDate, today.plusWeeks(20000));
    }
    @Test
    void getCompletionDateNegativeManHoursTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            projectServices.getCompletionDate(-200);
        });
    }


}