package com.example.bbschool.bbremotemobile;

/**
 * Created by Brendan on 3/13/2016.
 */
public interface Callback<In,Out> {
    public Out call(In in);
}
