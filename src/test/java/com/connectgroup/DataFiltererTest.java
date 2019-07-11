package com.connectgroup;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }

    @Test
    public void shouldReturnListSingleItem_WhenLogFileIsHasOneLine() throws FileNotFoundException {
        assertEquals(1,
                DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB")
                .size());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsWithOneLineDoesntMatchCountry() throws FileNotFoundException {
        assertTrue(DataFilterer
                .filterByCountry(openFile("src/test/resources/single-line"), "AR")
                .isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileHasWrongContent() throws FileNotFoundException {
        assertTrue(DataFilterer
                .filterByCountry(openFile("src/test/resources/wrong-line"), "GB")
                .isEmpty());
    }

    @Test
    public void shouldReturnListMultipleItems_WhenMultiLogFileLineMatchCountry() throws FileNotFoundException {
        assertEquals(3,
                DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US")
                        .size());
    }


    @Test
    public void shouldReturnListOfItems_WhenResopnseTimeAvobeLimit() throws FileNotFoundException {
        assertEquals(2,
                DataFilterer
                        .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"),
                                "US", 600)
                        .size());
    }

    @Test
    public void shouldReturnListOfItems_WhenResopnseTimeAvobeAverage() throws FileNotFoundException {
        assertEquals(3,
                DataFilterer
                        .filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"))
                        .size());
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
