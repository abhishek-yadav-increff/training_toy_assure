package com.increff.assure.utils;

import com.increff.assure.model.BinForm;

public class BinUtils {
    public static BinForm getBinForm(Long binSize) {
        BinForm binForm = new BinForm();
        binForm.setBinSize(binSize);
        return binForm;
    }
}
