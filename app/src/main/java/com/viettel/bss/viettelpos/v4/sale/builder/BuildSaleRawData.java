package com.viettel.bss.viettelpos.v4.sale.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.object.ProgramSaleBean;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOrderChannel;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

public class BuildSaleRawData {
	/**
	 * Build rawdata de tinh tien
	 * 
	 * @param functionType
	 * @param programSaleBeanMain
	 * @param collCode
	 * @param lstStockModel
	 * @param selectedTelecom
	 * @param collId
	 * @return
	 * @throws Exception
	 */
	public static String buildCalculateMoneyRawData(BCCSGateway bccsGateWate,
			int functionType, ProgramSaleBean programSaleBeanMain,
			String collCode, List<StockModel> lstStockModel,
			Long selectedTelecom, Long collId) {
		StringBuilder rawData = new StringBuilder();
		if (functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
			rawData.append("<ws:addGoodForMobile>");
			rawData.append("<input>");
			rawData.append("<saleTransInfoMin>");

			rawData.append("<note>");
			rawData.append("mbccs tinh tien");
			rawData.append("</note>");
			rawData.append("</saleTransInfoMin>");

			if (programSaleBeanMain.getProgram_id() != null
					&& !programSaleBeanMain.getProgram_id().isEmpty()) {
				rawData.append("<saleProgramId>").append(programSaleBeanMain.getProgram_id());
				rawData.append("</saleProgramId>");

			}

			HashMap<String, Object> param = new HashMap<>();
			if (collCode != null && !collCode.isEmpty()) {
				param.put("collStaffId", collId);
			}
			// Neu la BHLD thi truyen false
			if (functionType == FragmentSaleSaling.FUNCTION_SALE_BHLD) {
				param.put("isCheckOwner", "false");
			} else {
				param.put("isCheckOwner", "true");
			}

			// Ban dut 2, ban dat coc 1
			if (functionType == FragmentSaleSaling.FUNCTION_SALE_RETAIL) {
				param.put("sellerType", "RETAIL");
			} else if (functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
				param.put("sellerType", "PROMOTION");
			} else {
				param.put("sellerType", "COLL");
			}
			param.put("token", Session.getToken());
			ArrayList<String> xmlStockModel = new ArrayList<>();
			for (StockModel item : lstStockModel) {
				if (item.getQuantitySaling() > 0) {
					HashMap<String, Object> stockModelParam = new HashMap<>();
					if (item.getCheckSerial() != null
							&& item.getCheckSerial().compareTo(1L) == 0) {
						// Neu la mat hang co serial
						stockModelParam.put("bySerial", "true");

					} else {
						stockModelParam.put("bySerial", "false");

					}

					stockModelParam.put("quantity", item.getQuantitySaling()
							+ "");
					// stockModelParam.put("stockModelCode",
					// item.getStockModelCode());
					stockModelParam.put("productOfferingId",
							item.getStockModelId() + "");
					// stockModelParam.put("telecomServiceId", selectedTelecom
					// + "");
					param.put("stockLst",
							BCCSGateway.buildXMLFromHashmap(stockModelParam));
					xmlStockModel.add(BCCSGateway
							.buildXMLFromHashmap(stockModelParam));
				}
			}
			param.put("stockLst", xmlStockModel);
			rawData.append(BCCSGateway.buildXMLFromHashmap(param));
			rawData.append("</input>");
			rawData.append("</ws:addGoodForMobile>");
		} else {
			rawData.append("<ws:calMoneyForSale>");
			rawData.append("<saleInput>");

			if (programSaleBeanMain.getProgram_code() != null
					&& !programSaleBeanMain.getProgram_code().isEmpty()) {
				rawData.append("<saleProgramCode>").append(programSaleBeanMain.getProgram_code());
				rawData.append("</saleProgramCode>");
			} else {
				rawData.append("<saleProgramCode>" + "");
				rawData.append("</saleProgramCode>");
			}

			HashMap<String, Object> param = new HashMap<>();
			if (collCode != null && !collCode.isEmpty()) {
				param.put("channelCode", collCode);
			}
			// Neu la BHLD thi truyen false
			if (functionType == FragmentSaleSaling.FUNCTION_SALE_BHLD) {
				param.put("isCheckOwner", "false");
			} else {
				param.put("isCheckOwner", "true");
			}
			param.put("token", Session.getToken());
			ArrayList<String> xmlStockModel = new ArrayList<>();
			for (StockModel item : lstStockModel) {
				if (item.getQuantitySaling() > 0 && item.getPrice() > 0) {
					HashMap<String, Object> stockModelParam = new HashMap<>();
					if (item.getCheckSerial() != null
							&& item.getCheckSerial().compareTo(1L) == 0) {
						// Neu la mat hang co serial
						stockModelParam.put("haveSerial", "true");

					} else {
						stockModelParam.put("haveSerial", "false");

					}

					stockModelParam.put("amountTotal", item.getQuantitySaling()
							+ "");
					stockModelParam.put("stockModelCode",
							item.getStockModelCode());
					stockModelParam.put("stockModelId", item.getStockModelId()
							+ "");
					stockModelParam.put("telecomServiceId", selectedTelecom
							+ "");
					param.put("lstStockModel",
							BCCSGateway.buildXMLFromHashmap(stockModelParam));
					xmlStockModel.add(BCCSGateway
							.buildXMLFromHashmap(stockModelParam));
				}
			}
			param.put("lstStockModel", xmlStockModel);
			rawData.append(BCCSGateway.buildXMLFromHashmap(param));
			rawData.append("</saleInput>");
			rawData.append("</ws:calMoneyForSale>");
		}

        return bccsGateWate.buildInputGatewayWithRawData(rawData
                .toString());
	}

	/**
	 * Build rawData de gui len server thuc hien ban hang
	 * 
	 * @param functionType
	 * @param programSaleBeanMain
	 * @param collCode
	 * @param edtCusCompany
	 * @param edtCusTin
	 * @param edtCusName
	 * @param lstStockModel
	 * @param selectedTelecom
	 * @param collId
	 * @param isHandelChannelOrder
	 * @param saleOrderChannel
	 * @param spiVas
	 * @param edtVasPhone
	 * @param recordWorkId
	 * @param payMethod
	 * @param edtBankplusMobileDialog
	 * @param bankCode
	 * @param isSMS
	 * @return
	 * @throws Exception
	 */
	public static String buildSaleForMobileRawData(int functionType,
			ProgramSaleBean programSaleBeanMain, String collCode,
			EditText edtCusCompany, EditText edtCusTin, EditText edtCusName,
			List<StockModel> lstStockModel, Long selectedTelecom, Long collId,
			Boolean isHandelChannelOrder, SaleOrderChannel saleOrderChannel,
			Spinner spiVas, EditText edtVasPhone, Long recordWorkId,
			int payMethod, EditText edtBankplusMobileDialog, String bankCode,
			EditText edtEmail, CheckBox cbxReceiveInvoice, Boolean isSMS) {
		StringBuilder rawData = new StringBuilder();

		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
		if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
			rawData.append("<ws:exportStockCollaborator>");
		} else {
			rawData.append("<ws:saleForMobile>");
		}

		rawData.append("<input>");

		if (programSaleBeanMain != null
				&& programSaleBeanMain.getProgram_code() != null
				&& !programSaleBeanMain.getProgram_code().isEmpty()) {
			rawData.append("<saleProgramId>").append(programSaleBeanMain.getProgram_id());
			rawData.append("</saleProgramId>");
		}
		if (functionType == FragmentSaleSaling.FUNCTION_SALING) {
			Spin vas = (Spin) spiVas.getSelectedItem();
			if (!vas.getId().equals("-1")) {
				rawData.append("<vasCode>").append(vas.getId());
				rawData.append("</vasCode>");
				rawData.append("<vasPhone>").append(CommonActivity.getStardardIsdn(edtVasPhone.getText()
                        .toString()));
				rawData.append("</vasPhone>");
			}

		}

		HashMap<String, Object> param = new HashMap<>();
		if (collCode != null && !collCode.isEmpty()) {
			param.put("collStaffId", collId);
			param.put("channelCode", collCode);
		}
		// Neu la BHLD thi truyen false
		if (functionType == FragmentSaleSaling.FUNCTION_SALE_BHLD) {
			param.put("isCheckOwner", "false");
			param.put("recordWorkId", recordWorkId);
		} else {
			param.put("isCheckOwner", "true");
		}

		if (functionType == FragmentSaleSaling.FUNCTION_SALE_RETAIL) {
			param.put("sellerType", "RETAIL");

		} else if (functionType == FragmentSaleSaling.FUNCTION_SALE_BHLD
				|| functionType == FragmentSaleSaling.FUNCTION_SALING) {
			param.put("sellerType", "COLL");
		} else if (functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
			param.put("sellerType", "PROMOTION");
		}
		if (!isSMS) {
			param.put("token", Session.getToken());
		} else {
			param.put("token", "");
		}

		StringBuilder strSaleTransInfoMin = new StringBuilder();

		if (functionType == FragmentSaleSaling.FUNCTION_SALE_RETAIL
				|| functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
			strSaleTransInfoMin.append("<company>");
			strSaleTransInfoMin.append(edtCusCompany.getText().toString());
			strSaleTransInfoMin.append("</company>");

			strSaleTransInfoMin.append("<custName>");
			strSaleTransInfoMin.append(edtCusName.getText().toString());
			strSaleTransInfoMin.append("</custName>");

				if(edtEmail!=null){
					strSaleTransInfoMin.append("<email>");
					strSaleTransInfoMin.append(edtEmail.getText().toString());
					strSaleTransInfoMin.append("</email>");
			}
			strSaleTransInfoMin.append("<tin>");
			strSaleTransInfoMin.append(edtCusTin.getText().toString());
			strSaleTransInfoMin.append("</tin>");

			strSaleTransInfoMin.append("<telNumber>");
			strSaleTransInfoMin.append(edtBankplusMobileDialog.getText()
					.toString());
			strSaleTransInfoMin.append("</telNumber>");

		}
		if (payMethod == FragmentSaleSaling.PAY_METHOD_BANKPLUS) {
			strSaleTransInfoMin.append("<payBankAccount>");

			strSaleTransInfoMin.append("<bankCode>");
			strSaleTransInfoMin.append(bankCode);
			strSaleTransInfoMin.append("</bankCode>");
			if (edtBankplusMobileDialog != null) {
				strSaleTransInfoMin.append("<isdn>");
				strSaleTransInfoMin.append(edtBankplusMobileDialog.getText()
						.toString());
				strSaleTransInfoMin.append("</isdn>");
			}

			strSaleTransInfoMin.append("</payBankAccount>");
		}
		Boolean receiveInvoice = true;
		if (cbxReceiveInvoice != null) {
			receiveInvoice = cbxReceiveInvoice.isChecked();
		}

		strSaleTransInfoMin.append("<receiveInvoice>");
		strSaleTransInfoMin.append(receiveInvoice);
		strSaleTransInfoMin.append("</receiveInvoice>");
		param.put("saleTransInfoMin", strSaleTransInfoMin);
		param.put("payMethod", payMethod);
		ArrayList<String> xmlStockModel = new ArrayList<>();
		String stockTag = "";
		String serialXMLTag = "";
		if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
			stockTag = "lstProductOfferingSM";
			serialXMLTag = "listStockTransSerialDTOs";
		} else {
			stockTag = "stockLst";
			serialXMLTag = "serialPairLst";
		}
		for (StockModel item : lstStockModel) {
			if (item.getQuantitySaling() > 0) {
				HashMap<String, Object> stockModelParam = new HashMap<>();
				if (item.getCheckSerial() != null
						&& item.getCheckSerial().compareTo(1L) == 0) {
					// Neu la mat hang co serial
					stockModelParam.put("bySerial", "true");
					Boolean isStockHandset = false;
					if (item.isStockHandset()) {
						isStockHandset = true;
					}
					ArrayList<Serial> lstSerial = SaleCommons.getRangeSerial(
							SaleCommons.sortSerial(item.getSelectedSerial(),
									isStockHandset), isStockHandset);
					ArrayList<String> xmlSerial = new ArrayList<>();
					for (Serial serial : lstSerial) {
                        String serialStr = "<fromSerial>" +
                                serial.getFromSerial() +
                                "</fromSerial>" +
                                "<toSerial>" +
                                serial.getToSerial() +
                                "</toSerial>";
                        xmlSerial.add(serialStr);
					}
					for (Serial serial : lstSerial) {
						stockModelParam.put("toSerial", serial.getToSerial());
					}

					stockModelParam.put(serialXMLTag, xmlSerial);

				} else {
					stockModelParam.put("bySerial", "false");

				}

				stockModelParam.put("quantity", item.getQuantitySaling() + "");
				stockModelParam.put("stateId", "1");
				stockModelParam.put("productOfferingCode",
						item.getStockModelCode());
				stockModelParam.put("code", item.getStockModelCode());
				stockModelParam.put("productOfferingId", item.getStockModelId()
						+ "");

				param.put(stockTag,
						BCCSGateway.buildXMLFromHashmap(stockModelParam));
				xmlStockModel.add(BCCSGateway
						.buildXMLFromHashmap(stockModelParam));
			}
		}
		param.put(stockTag, xmlStockModel);
		rawData.append(BCCSGateway.buildXMLFromHashmap(param));
		rawData.append("</input>");
		if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
			rawData.append("</ws:exportStockCollaborator>");
		} else {
			rawData.append("</ws:saleForMobile>");
		}
		// } else {
		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
		// rawData.append("<ws:exportStockCollaborator>");
		// } else {
		// rawData.append("<ws:saleForChannel>");
		// }
		//
		// rawData.append("<saleInput>");
		//
		// if (programSaleBeanMain.getProgram_code() != null
		// && !programSaleBeanMain.getProgram_code().isEmpty()) {
		// rawData.append("<saleProgramCode>"
		// + programSaleBeanMain.getProgram_code());
		// rawData.append("</saleProgramCode>");
		// } else {
		// rawData.append("<saleProgramCode>" + "");
		// rawData.append("</saleProgramCode>");
		// }
		//
		// if (isHandelChannelOrder) {
		// rawData.append("<salrOrderId>");
		// rawData.append(saleOrderChannel.getSaleOrderId());
		// rawData.append("</salrOrderId>");
		// }
		//
		// if (functionType == FragmentSaleSaling.FUNCTION_SALING) {
		// Spin vas = (Spin) spiVas.getSelectedItem();
		// if (!vas.getId().equals("-1")) {
		// rawData.append("<vasCode>" + vas.getId());
		// rawData.append("</vasCode>");
		// rawData.append("<vasPhone>"
		// + CommonActivity.getStardardIsdn(edtVasPhone
		// .getText().toString()));
		// rawData.append("</vasPhone>");
		// }
		//
		// }
		//
		// HashMap<String, Object> param = new HashMap<String, Object>();
		// if (collCode != null && !collCode.isEmpty()) {
		// param.put("channelCode", collCode);
		// }
		// // Neu la BHLD thi truyen false
		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_BHLD) {
		// param.put("isCheckOwner", "false");
		// param.put("recordWorkId", recordWorkId);
		// } else {
		// param.put("isCheckOwner", "true");
		// }
		//
		// // Ban dut 2, ban dat coc 1
		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
		// param.put("sellerType", "1");
		// } else {
		// param.put("sellerType", "2");
		// }
		// if (!isSMS) {
		// param.put("token", Session.getToken());
		// } else {
		// param.put("token", "");
		// }
		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_RETAIL) {

//				if (recordWorkId != null && recordWorkId > 0) {
//					param.put("recordWorkId", recordWorkId);
//				}
		// // Neu la ban le, truyen them cac them so khach hang
		// param.put("cusCompany", edtCusCompany.getText().toString());
		// param.put("cusTin", edtCusTin.getText().toString());
		// param.put("custName", edtCusName.getText().toString());
		// if (payMethod == FragmentSaleSaling.PAY_METHOD_BANKPLUS) {
		// param.put("mobileBankplus", CommonActivity
		// .getStardardIsdn(edtBankplusMobileDialog.getText()
		// .toString()));
		// }
		// }
		// if (payMethod == FragmentSaleSaling.PAY_METHOD_BANKPLUS) {
		// param.put("bankCode", bankCode);
		// }
		// param.put("payMethod", payMethod);
		// ArrayList<String> xmlStockModel = new ArrayList<String>();
		// for (StockModel item : lstStockModel) {
		// if (item.getQuantitySaling() > 0) {
		// HashMap<String, Object> stockModelParam = new HashMap<String,
		// Object>();
		// if (item.getCheckSerial() != null
		// && item.getCheckSerial().compareTo(1L) == 0) {
		// // Neu la mat hang co serial
		// stockModelParam.put("haveSerial", "true");
		// Boolean isStockHandset = false;
		// if (item.getTableName() != null
		// && item.getTableName().equalsIgnoreCase(
		// "stock_handset")) {
		// isStockHandset = true;
		// }
		// ArrayList<Serial> lstSerial = SaleCommons
		// .getRangeSerial(SaleCommons.sortSerial(
		// item.getSelectedSerial(),
		// isStockHandset), isStockHandset);
		// ArrayList<String> xmlSerial = new ArrayList<String>();
		// for (Serial serial : lstSerial) {
		// StringBuilder serialStr = new StringBuilder();
		// serialStr.append("<fromSerial>");
		// serialStr.append(serial.getFromSerial());
		// serialStr.append("</fromSerial>");
		// serialStr.append("<toSerial>");
		// serialStr.append(serial.getToSerial());
		// serialStr.append("</toSerial>");
		// xmlSerial.add(serialStr.toString());
		// }
		// for (Serial serial : lstSerial) {
		// stockModelParam.put("toSerial",
		// serial.getToSerial());
		// }
		// stockModelParam.put("lstSerial", xmlSerial);
		//
		// } else {
		// stockModelParam.put("haveSerial", "false");
		// }
		// stockModelParam.put("amountTotal", item.getQuantitySaling()
		// + "");
		// stockModelParam.put("stockModelCode",
		// item.getStockModelCode());
		// stockModelParam.put("stockModelId", item.getStockModelId()
		// + "");
		// stockModelParam.put("telecomServiceId", selectedTelecom
		// + "");
		// param.put("lstStockModel",
		// BCCSGateway.buildXMLFromHashmap(stockModelParam));
		// xmlStockModel.add(BCCSGateway
		// .buildXMLFromHashmap(stockModelParam));
		// }
		// }
		// param.put("lstStockModel", xmlStockModel);
		// rawData.append(BCCSGateway.buildXMLFromHashmap(param));
		// rawData.append("</saleInput>");
		// if (functionType == FragmentSaleSaling.FUNCTION_SALE_DEPOSIT) {
		// rawData.append("</ws:exportStockCollaborator>");
		// } else {
		// rawData.append("</ws:saleForChannel>");
		// }
		// }
		return rawData.toString();
	}
}
