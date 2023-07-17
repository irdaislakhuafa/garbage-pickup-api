package com.irdaislakhuafa.garbagepickupapi.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculatorHelper {
    // earch radius in KM
    private static final double EARTH_RADIUS = 6371;

    public double calculateDistance(Distance a, Distance b) {
        final var resultLat = Math.toRadians(b.getLat() - a.getLat());
        final var resultLng = Math.toRadians(b.getLng() - a.getLng());

        // use haversine formula
        final double haversineCalculation = Math.sin(resultLat / 2) * Math.sin(resultLat / 2) + Math.cos(Math.toRadians(a.getLat())) * Math.cos(Math.toRadians(b.getLat())) * Math.sin(resultLng / 2) * Math.sin(resultLng / 2);
        final double c = 2 * Math.atan2(Math.sqrt(haversineCalculation), Math.sqrt(1 - haversineCalculation));
        final var resultDistance = EARTH_RADIUS * c;

        return resultDistance;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Distance {
        private double lat;
        private double lng;
    }
}