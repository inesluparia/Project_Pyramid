package com.example.projectpyramid.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;
class ProjectServicesTests {

    private ProjectServices projectServices;

    @BeforeEach
    void initialize(){
        projectServices = new ProjectServices();
    }

    @Test
    void getTotalCostSuccessTest() {
        int cost = projectServices.getTotalCost(2);
        assertEquals(500, cost);
    }

    @Test
    void getTotalCostFailTestWrongInputType() {
        int cost = projectServices.getTotalCost(2);
        assertTrue(cost == 500);
    }

    @Test
    void getTotalCostManyManHoursFailTest() {
        int cost = projectServices.getTotalCost(20);
        assertFalse(20 == cost);
    }

    @Test
    void getTotalCostNegativeManHoursTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            projectServices.getTotalCost(-2);
        });
    }

    @Test
    void getCompletionDateSuccessTest() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate completionDate = projectServices.getCompletionDate(35);
        assertEquals(completionDate, today.plusWeeks(1));
    }

    @Test
    void getCompletionDateSuccessTest2() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate completionDate = projectServices.getCompletionDate(42);
        assertEquals(completionDate, today.plusWeeks(1).plusDays(1));
    }

    @Test
    void getCompletionDateFailTest() throws Exception {
        LocalDate today = now();
        LocalDate completionDate = projectServices.getCompletionDate(35);
        assertFalse(today == completionDate);

    }
    @Test
    void getCompletionDateTooManyManHoursTest() {


    }

}