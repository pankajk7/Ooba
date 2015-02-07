package com.ooba.util;

import android.content.Context;
import android.graphics.Typeface;

public class FontSettings {

	Context context;
	String fontPath;

	public FontSettings(Context context) {
		this.context = context;
	}

	public Typeface getSourceSansProLight() {
		fontPath = "fonts/SourceSansPro-Light.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		return tf;
	}

	public Typeface getSourceSansProBold() {
		fontPath = "fonts/SourceSansPro-Bold.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		return tf;
	}

	public Typeface getSourceSansProRegular() {
		Typeface tf = null;
		try {
			fontPath = "fonts/SourceSansPro-Regular.ttf";
			tf = Typeface.createFromAsset(context.getAssets(),
					fontPath);
			
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString()
					+ ">>>Message>>>" + e.getMessage());
		}
		return tf;
	}

	public Typeface getSourceSansProBlack() {
		fontPath = "fonts/SourceSansPro-Black.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		return tf;
	}

	public Typeface getSourceSansProExtraLight() {
		fontPath = "fonts/SourceSansPro-ExtraLight.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		return tf;
	}

	public Typeface getSourceSansProSemiBold() {
		fontPath = "fonts/SourceSansPro-Semibold.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		return tf;
	}

}
