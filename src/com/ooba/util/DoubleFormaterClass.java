package com.ooba.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DoubleFormaterClass {

	public static String priceWithDecimal (double price) {

	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    String string = formatter.format(price);
	    string = string.replace(",", " ");
	    return string;
	}
}
