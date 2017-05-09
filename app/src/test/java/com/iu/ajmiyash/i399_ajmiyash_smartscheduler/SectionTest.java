package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

/**
 * Created by Adam on 3/9/2017.
 */

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SectionTest {
    @Test
    public void TestStringToSectionArrayList() {
        ArrayList<Section> sections = Section.toArrayList("[LECTURE, LAB, DISCUSSION]");
        ArrayList<Section> testSections = new ArrayList<Section>();
        testSections.add(Section.LECTURE);
        testSections.add(Section.LAB);
        testSections.add(Section.DISCUSSION);

        assertEquals(sections, testSections);
    }
}
