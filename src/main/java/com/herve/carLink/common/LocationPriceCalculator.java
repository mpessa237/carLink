package com.herve.carLink.common;

import java.util.Date;

public class LocationPriceCalculator {

    public static double calculateTotalPrice(Date startDate, Date endDate, double dailyPrice) {
        long diffInMillis = Math.abs(endDate.getTime() - startDate.getTime());
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        return diffInDays * dailyPrice;
    }
}
