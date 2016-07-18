package com.example.reedme.custom;

import com.google.android.gms.location.places.Place;

/**
 * Listener interface for place selection.
 */
public interface PlaceSelectedListener {

    void onPlaceSelected(Place place);
}