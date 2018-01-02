package com.viettel.bss.viettelpos.v4.hsdt.object;

import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Toancx on 10/25/2017.
 */

public class ProfileTypeComparator implements Comparator<ArrayList<RecordPrepaid>> {
    @Override
    public int compare(ArrayList<RecordPrepaid> o1, ArrayList<RecordPrepaid> o2) {
        return o1.get(0).getElectronicSign().compareTo(o2.get(0).getElectronicSign());
    }
}