package com.example.projectpyramid.domain.services;

import com.example.projectpyramid.data_access.mappers.ProjectMapper;
import com.example.projectpyramid.domain.entities.Client;
import com.example.projectpyramid.domain.entities.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        int cost = projectServices.getTotalCost(2000000000);
        //assertThrows()

    }

    @Test
    void getTotalCostNegativeManHoursTest() {
        int cost = projectServices.getTotalCost(-2);
        //expect exception of some type

    }


    @Test
    void getCompletionDateSuccessTest() {
    }

    @Test
    void getCompletionDateFailTest() {

    }
    @Test
    void getCompletionDateTooManyManHoursTest() {


    }

}