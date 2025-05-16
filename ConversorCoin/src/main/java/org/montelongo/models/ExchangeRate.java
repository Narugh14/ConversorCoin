package org.montelongo.models;

import java.util.Map;

public class ExchangeRate {
    private String result;
    private String documentation;
    private String termsOfUse;
    private long timeLastUpdateUnix;
    private String timeLastUpdateUtc;
    private long timeNextUpdateUnix;
    private String timeNextUpdateUtc;
    private String baseCode;
    private Map<String, Double> conversionRates;

    // Getters y setters
    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public String getTimeLastUpdateUtc() {
        return timeLastUpdateUtc;
    }

    public String getTimeNextUpdateUtc() {
        return timeNextUpdateUtc;
    }
}
