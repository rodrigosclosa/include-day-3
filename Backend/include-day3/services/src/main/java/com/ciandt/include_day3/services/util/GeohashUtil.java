package com.ciandt.include_day3.services.util;

import ch.hsr.geohash.GeoHash;

/**
 * Created by rodrigosclosa on 02/03/16.
 */
public class GeohashUtil {
    private static GeohashUtil ourInstance = new GeohashUtil();

    public static GeohashUtil getInstance() {
        return ourInstance;
    }

    private GeohashUtil() {
    }

    public String geohash(String latitude, String longitude, int precisao) {
        GeoHash geohash =
                GeoHash.withCharacterPrecision(Double.valueOf(latitude), Double.valueOf(longitude), precisao);
        String geohashString = geohash.toBase32();
        return geohashString;
    }
}
