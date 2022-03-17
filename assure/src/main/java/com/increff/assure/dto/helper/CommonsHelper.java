package com.increff.assure.dto.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.increff.assure.util.StringUtil;

/**
 * utils
 */
public class CommonsHelper {

    public static String normalize(String s) {
        if (s != null)
            return StringUtil.toLowerCase(s).trim();
        return null;
    }

    public static Double normalize(Double d) {
        if (d != null) {
            BigDecimal bigDecimal = new BigDecimal(d).setScale(2, RoundingMode.HALF_DOWN);
            Double scaledDouble = bigDecimal.doubleValue();
            return scaledDouble;
        }
        return null;
    }

    public static String doubleToString(Double d) {
        if (d != null)
            return String.format("%.2f", d);
        return null;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
