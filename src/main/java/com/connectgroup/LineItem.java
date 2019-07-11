package com.connectgroup;

/**
 * Model REQUEST_TIMESTAMP,COUNTRY_CODE,RESPONSE_TIME
 * */
public class LineItem {
    private Long requestTimestamp;
    private String countryCode;
    private Long responseTime;

    public LineItem(Long requestTimestamp, String countryCode, Long responseTime) {
        this.requestTimestamp = requestTimestamp;
        this.countryCode = countryCode;
        this.responseTime = responseTime;
    }

    public Long getRequestTimestamp() {
        return requestTimestamp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Long getResponseTime() {
        return responseTime;
    }
}
