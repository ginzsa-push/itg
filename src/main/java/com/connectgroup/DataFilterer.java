package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class DataFilterer {

    public static final String SEPARATOR = ",";

    public static Collection<?> filterByCountry(Reader source, String country) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(source)) {

            list = br.lines()
                    .filter(line -> extractCountryCodeFromLine(line, country))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(source)) {

            list = br.lines()
                    .filter(line -> extractCountryCodeFromLine(line, country))
                    .filter(line -> extractResponseTimeAvobeLimitFromLine(line, limit))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {

        LongSummaryStatistics stat = null;
        List<LineItem> list = new ArrayList<>(); // for this test save it in a list
        try (BufferedReader br = new BufferedReader(source)) {

            stat = br.lines()
                    .map(line -> {
                        LineItem item = extractLineItem(line);
                        list.add(item);
                        return item;
                    })
                    .mapToLong(item -> item.getResponseTime())
                    .summaryStatistics();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // needs average first - TODO: this value can be cached
        Double mean=  stat.getAverage();

        return list.stream().
                filter(item -> item.getResponseTime() > mean)
                .collect(Collectors.toList());
    }

    public static LineItem extractLineItem(String line) {
        String[] content = line.split(SEPARATOR);
        if (content.length > 2) {
            return new LineItem(extractLong(content[0]), content[1], extractLong(content[2]));
        }
        throw new IllegalArgumentException("wrong number of arguments in line: " + line);
    }

    public static boolean extractResponseTimeAvobeLimitFromLine(String line, Long limit) {
        String[] content = line.split(SEPARATOR);
        if (content.length > 2) {
            return extractLong(content[2]) > limit;
        }
        return false;
    }

    public static boolean extractCountryCodeFromLine(String line, String country) {
        String[] content = line.split(SEPARATOR);
        if (content.length > 1) {
            return content[1].trim().equals(country);
        }
        return false;
    }


    public static Long extractLong(String number){
        try {
            return Long.parseLong(number.trim());
        } catch (NumberFormatException | NullPointerException nfe) {
            return -1L; // time elapsed has not negative
        }
    }
}