package com.viettel.bss.viettelpos.v4.commons;

import java.util.ArrayList;

import android.util.Log;

import com.viettel.bss.viettelpos.v4.sale.object.Serial;

public class SaleCommons {

	private static final String logTag = SaleCommons.class.getName();

	/**
	 * Ham thuc hien gom dai serial tu danh sach serial truyen vao
	 * 
	 * @return
	 */
	public static ArrayList<Serial> getRangeSerial(
			ArrayList<String> lstSelectedSerial, Boolean isStockHandset) {
		try {
			ArrayList<Serial> result = new ArrayList<>();
			if (lstSelectedSerial == null || lstSelectedSerial.isEmpty()) {
				return result;
			}

			if (isStockHandset) {
				for (String serial : lstSelectedSerial) {
					Serial tmp = new Serial();
					tmp.setFromSerial(serial);
					tmp.setToSerial(serial);
					result.add(tmp);
				}
			} else {
				ArrayList<String> lstSerial = new ArrayList<>();
				lstSerial.addAll(lstSelectedSerial);
				String firstSerial = lstSerial.get(0);

				Serial firtSerial = new Serial();
				firtSerial.setFromSerial(firstSerial);
				firtSerial.setToSerial(firstSerial);
				result.add(firtSerial);
				lstSerial.remove(0);
				if (lstSerial.isEmpty()) {
					return result;
				}
				for (String tmp : lstSerial) {
					int lastIndex = result.size() - 1;
					Long lastToSerial = Long.parseLong(result.get(lastIndex)
							.getToSerial());
					Long currentSerial = Long.parseLong(tmp);
					// Serial cung dai, set lai toSerial
					if (currentSerial - lastToSerial == 1) {
						result.get(lastIndex).setToSerial(tmp);
					} else {
						Serial serial = new Serial();
						serial.setFromSerial(tmp);
						serial.setToSerial(tmp);
						result.add(serial);
					}
				}
			}
			return result;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @param lstSerial
	 * @return
	 */
	public static ArrayList<String> sortSerial(ArrayList<String> lstSerial,
			Boolean isStockHandset) {
		try {
			if (lstSerial == null || lstSerial.isEmpty()) {
				return lstSerial;
			}
			if (isStockHandset) {
				return lstSerial;
			}
			ArrayList<String> lstResult = new ArrayList<>();
			ArrayList<String> lstTmp = new ArrayList<>();
			lstTmp.addAll(lstSerial);
			while (!lstTmp.isEmpty()) {
				Long serialLong = Long.parseLong(lstTmp.get(0));
				String serial = lstTmp.get(0);
				for (int i = 1; i < lstTmp.size(); i++) {
					Long item = Long.parseLong(lstTmp.get(i));
					if (item < serialLong) {
						serialLong = item;
						serial = lstTmp.get(i);
					}
				}
				lstResult.add(serial);
				for (int i = 0; i < lstTmp.size(); i++) {
					if (lstTmp.get(i).equals(serial)) {
						lstTmp.remove(i);
						break;
					}
				}
			}
			return lstResult;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
			return lstSerial;
		}
	}

	/**
	 * Kiem tra serial co nam trong dai hay khong
	 * 
	 * @param serial
	 * @return
	 */
	public static Boolean checkInRange(Serial serialArrange, String serial,
			Boolean isStockHandset) {
		try {
			if (serialArrange == null || serial == null || serial.isEmpty()) {
				return false;
			}
			if (isStockHandset) {
                return serialArrange.getFromSerial().equalsIgnoreCase(serial);
            }
			Long fromSerial = Long.parseLong(serialArrange.getFromSerial());
			Long toSerial = Long.parseLong(serialArrange.getToSerial());
			Long currSerial = Long.parseLong(serial);
            return fromSerial <= currSerial && toSerial >= currSerial;
        } catch (Exception e) {
			Log.e(logTag, "Exception", e);
			return false;
		}
	}

	/**
	 * Tach lay serial tu dai serial
	 * 
	 * @return
	 */
	public static ArrayList<String> splitSerial(Serial serial,
			boolean isStockCard) {

		try {
			ArrayList<String> result = new ArrayList<>();
			if (serial == null) {

				return result;
			}
			if (serial.getFromSerial().equals(serial.getToSerial())) {
				result.add(serial.getFromSerial());
				return result;
			}
			result.add(serial.getFromSerial());
			// result.add(serial.getToSerial());
			Long end = Long.parseLong(serial.getToSerial());
			Long start = Long.parseLong(serial.getFromSerial());
			while (start < end) {
				start++;
				if (isStockCard) {
					result.add(normalSerial(start.toString()));
				} else {
					result.add(start.toString());
				}
			}

			return result;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
			return new ArrayList<>();
		}

	}

	/**
	 * Rxxxx: Rat don gian la them so 0 cho cai seial the cao
	 * 
	 * @author thuannx1 @date 16/07/2014
	 * @return pageForward
	 * @throws Exception
	 */
	public static String normalSerial(String serial) {
		if (serial != null && !"".equals(serial.trim())) {
			while (serial.length() < 11) {
				serial = "0" + serial;
			}
		}
		return serial;
	}
}
