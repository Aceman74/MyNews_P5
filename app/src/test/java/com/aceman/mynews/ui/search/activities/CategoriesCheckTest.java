/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.search.activities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Lionel JOFFRAY - on 04/04/2019.
 */
public class CategoriesCheckTest {
    private final List<Boolean> mCorrectList = Arrays.asList(new Boolean[6]);
    private final List<Boolean> mBadList = Arrays.asList(new Boolean[6]);
    private List<Boolean> mCheckList;

    @Test
    public void setCheckListSizeTest() {


        Collections.fill(mCorrectList, FALSE);
        Collections.fill(mBadList, TRUE);

        mCheckList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            mCheckList.add(false);
        }

        assertEquals(mCheckList, mCorrectList);  //  Test that checklist return full "False" List and size 6
        assertNotSame(mCheckList, mBadList);   //  Test that checklist doesn't return any "True"

    }
}