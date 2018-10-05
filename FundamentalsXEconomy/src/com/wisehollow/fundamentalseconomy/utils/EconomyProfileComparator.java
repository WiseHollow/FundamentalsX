package com.wisehollow.fundamentalseconomy.utils;

import com.wisehollow.fundamentalseconomy.data.EconomyProfile;

import java.util.Comparator;

public class EconomyProfileComparator implements Comparator<EconomyProfile> {
    @Override
    public int compare(EconomyProfile o1, EconomyProfile o2) {
        return Double.compare(o1.getBalance(), o2.getBalance());
    }
}
