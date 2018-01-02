package com.viettel.bss.viettelpos.v4.object;

public class Location {
	private String x = "0";
	private String y = "0";
	private String type;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public void setX(Double x) {
		if (x != null) {
			this.x = x + "";
		}
	}

	public void setY(Double y) {
		if (y != null) {
			this.y = y + "";
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
