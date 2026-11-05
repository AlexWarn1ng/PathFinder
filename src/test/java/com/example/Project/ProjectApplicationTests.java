package com.example.Project;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ProjectApplicationTests {

    @Autowired
    private OptimalPathFinder pathFinder;

	@Test
	void contextLoads() {
	}

    @Test
    void testPath() {
        List<Point> list = pathFinder.FindOptimalPath(1);

        assertFalse(list.isEmpty());

        list.forEach(point -> System.out.println("X: " + point.getX() + ", Y: " + point.getY()));
    }

}
