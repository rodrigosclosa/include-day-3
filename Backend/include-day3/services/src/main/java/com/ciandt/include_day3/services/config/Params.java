package com.ciandt.include_day3.services.config;

/**
 * Created by rodrigosclosa on 04/02/16.
 */
public class Params {
    private static Params ourInstance = new Params();

    public static Params getInstance() {
        return ourInstance;
    }

    private Double raio = 10000d;

    private Params() {
    }

    public Double getRaio() {
        return raio;
    }


}
