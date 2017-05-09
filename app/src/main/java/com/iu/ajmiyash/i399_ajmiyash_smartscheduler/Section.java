package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adam on 3/3/2017.
 */

public enum Section {
    LECTURE, LAB, DISCUSSION, RECITATION;

    static public ArrayList<Section> toArrayList(String sections) {
        ArrayList<Section> sectionArray = new ArrayList<Section>();

        String[] stringArray = sections.substring(1, sections.length()-1).split(", ");

        ArrayList<String> strArrayList = new ArrayList<String>(Arrays.asList(stringArray));

        // convert strings to sections
        for (String section : strArrayList) {
            sectionArray.add(Section.valueOf(section));
        }

        return sectionArray;
    }
}
