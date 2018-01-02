package com.viettel.bss.viettelpos.v4.customview;

import org.xml.sax.XMLReader;

import android.text.Editable;
import android.text.Html.TagHandler;

public class MyTagHandler implements TagHandler {
	boolean first = true;
	String parent = null;
	int index = 1;

	@Override
	public void handleTag(final boolean opening, final String tag,
			final Editable output, final XMLReader xmlReader) {

		if (tag.equals("ul")) {
			parent = "ul";
			index = 1;
		} else if (tag.equals("ol")) {
			parent = "ol";
			index = 1;
		}
		if (tag.equals("li")) {
			char lastChar = 0;
			if (output.length() > 0) {
				lastChar = output.charAt(output.length() - 1);
			}
			if (parent.equals("ul")) {
				if (first) {
					if (lastChar == '\n') {
						output.append("\t•  ");
					} else {
						output.append("\n\t•  ");
					}
					first = false;
				} else {
					first = true;
				}
			} else {
				if (first) {
					if (lastChar == '\n') {
						output.append("\t" + index + ". ");
					} else {
						output.append("\n\t" + index + ". ");
					}
					first = false;
					index++;
				} else {
					first = true;
				}
			}
		}
	}
}