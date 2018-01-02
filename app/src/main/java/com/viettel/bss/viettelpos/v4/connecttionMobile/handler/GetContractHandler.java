package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ConTractBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.CustomerGroup;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.CustomerType;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RepresentativeCustObj;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ContractBank;

public class GetContractHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private ConTractBean temp = null;
	private RepresentativeCustObj representativeCustObj;
	private CustomerType customerType;
	private CustomerGroup customerGroup;
	private ContractBank contractBank;
	private boolean isParseRepresent = false;
	private final int NAME_CONTRACT = 1;
	private final int NAME_CONTRACT_BANK = 2;
	private final int NAME_CUSTOMER_TYPE = 3;
	private final int NAME_CUSTOMER_GROUP = 4;
	private final int NAME_KHDD = 5;
	private int nameType = 0;
	private int addressType = 0;
	private final int ADDRESS_CONTRACT = 1;
    private final int ADDRESS_REPRECUS = 4;

	private int codeType = 0;
	private final int CODE_CUS_TYPE = 1;
	private final int CODE_CUS_GROUP = 2;

	private boolean isCodeCus = false;

	private ArrayList<ConTractBean> lstData = null;

	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<ConTractBean> getLstData() {
		return lstData;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		currentElement = false;
		/** set value */
		/* Common */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		}
		// GETDATA
		else if (localName.equalsIgnoreCase("contractNo")) {
			temp.setContractNo(currentValue);
		} else if (localName.equalsIgnoreCase("contractId")) {
			temp.setContractId(currentValue);
		} else if (localName.equalsIgnoreCase("address")) {
			switch (addressType) {
			case ADDRESS_CONTRACT:
				temp.setAddress(currentValue);
				addressType = 0;
				break;

			case ADDRESS_REPRECUS:
				if (representativeCustObj != null) {
					representativeCustObj.setAddress(currentValue);
				}
				addressType = 0;
				break;
			default:
				break;
			}
			// if(isParseRepresent && contractBank == null){
			// temp.setAddress(currentValue);
			// }else{
			// representativeCustObj.setAddress(currentValue);
			// }
		} else if (localName.equalsIgnoreCase("billCycleFrom")) {
			temp.setBillCycleFrom(currentValue);
		} else if (localName.equalsIgnoreCase("billAddress")) {
			temp.setBillAddress(currentValue);
		} else if (localName.equalsIgnoreCase("billCycleFromCharging")) {
			temp.setBillCycleFromCharging(currentValue);
		} else if (localName.equalsIgnoreCase("contractType")) {
			temp.setContractType(currentValue);
		} else if (localName.equalsIgnoreCase("contractTypeCode")) {
			temp.setContractTypeCode(currentValue);
		}  else if (localName.equalsIgnoreCase("contractPrint")) {
			temp.setContractPrint(currentValue);
			
		}else if (localName.equalsIgnoreCase("custId")) {
			if (isParseRepresent) {
				temp.setCustId(currentValue);
			} else {
				if (representativeCustObj != null) {
					representativeCustObj.setCustId(currentValue);
				}
			}

		} else if (localName.equalsIgnoreCase("dateCreate")) {
			temp.setDateCreate(StringUtils.convertDate(currentValue));
		} else if (localName.equalsIgnoreCase("district")) {
			temp.setDistrict(currentValue);
		} else if (localName.equalsIgnoreCase("effectDate")) {
			temp.setEffectDate(StringUtils.convertDate(currentValue));
		} else if (localName.equalsIgnoreCase("mainIsdn")) {
			temp.setMainIsdn(currentValue);
		} else if (localName.equalsIgnoreCase("mainSubId")) {
			temp.setMainSubId(currentValue);
		} else if (localName.equalsIgnoreCase("noticeCharge")) {
			temp.setNoticeCharge(currentValue);
		} else if (localName.equalsIgnoreCase("numOfSubscribers")) {
			temp.setNumOfSubscribers(currentValue);
		} else if (localName.equalsIgnoreCase("payAreaCode")) {
			temp.setPayAreaCode(currentValue);
		} else if (localName.equalsIgnoreCase("payMethodCode")) {
			temp.setPayMethodCode(currentValue);
		} else if (localName.equalsIgnoreCase("payer")) {
			temp.setPayer(currentValue);
		} else if (localName.equalsIgnoreCase("precinct")) {
			temp.setPrecinct(currentValue);
		} else if (localName.equalsIgnoreCase("printMethodCode")) {
			temp.setPrintMethodCode(currentValue);
		} else if (localName.equalsIgnoreCase("province")) {
			temp.setProvince(currentValue);
		} else if (localName.equalsIgnoreCase("signDate")) {
			temp.setSignDate(StringUtils.convertDate(currentValue));
		} else if (localName.equalsIgnoreCase("status")) {
			temp.setStatus(currentValue);
		} else if (localName.equalsIgnoreCase("streetBlock")) {
			temp.setStreetBlock(currentValue);
		} else if (localName.equalsIgnoreCase("streetName")) {
			temp.setStreetName(currentValue);
		} else if (localName.equalsIgnoreCase("telMobile")) {
			temp.setTelMobile(currentValue);
		}
		if (localName.equalsIgnoreCase("telFax")) {
			temp.setTelFax(currentValue);
		} else if (localName.equalsIgnoreCase("userCreate")) {
			temp.setUserCreate(currentValue);
		} else if (localName.equalsIgnoreCase("home")) {
			temp.setHome(currentValue);
		} else if (localName.equalsIgnoreCase("contractOfferId")) {
			temp.setContractOfferId(currentValue);
		} else if (localName.equalsIgnoreCase("contactName")) {
			temp.setContactName(currentValue);
		} else if (localName.equalsIgnoreCase("contactTitle")) {
			temp.setContactTitle(currentValue);
		} else if (localName.equalsIgnoreCase("idNo")) {
			temp.setIdNo(currentValue);
		} else if (localName.equalsIgnoreCase("email")) {
			temp.setEmail(currentValue);
		} else if (localName.equalsIgnoreCase("name")) {
			switch (nameType) {
			case NAME_CONTRACT:
				nameType = 0;
				break;
			case NAME_CONTRACT_BANK:
				if (contractBank != null) {
					contractBank.setName(currentValue);
				}
				nameType = 0;
				break;
			case NAME_CUSTOMER_TYPE:
				if (customerType != null) {
					customerType.setName(currentValue);
				}
				nameType = 0;
				break;
			case NAME_KHDD:
				if (representativeCustObj != null) {
					representativeCustObj.setName(currentValue);
				}
				nameType = 0;
				break;
			case NAME_CUSTOMER_GROUP:
				if (customerGroup != null) {
					customerGroup.setName(currentValue);
				}
				nameType = 0;
				break;
			default:
				break;
			}
		}
		if (contractBank != null) {
			if (localName.equalsIgnoreCase("account")) {
				contractBank.setAccount(currentValue);
			} else if (localName.equalsIgnoreCase("accountName")) {
				contractBank.setAccountName(currentValue);
			} else if (localName.equalsIgnoreCase("bankContractDate")) {
				contractBank.setStrBankContractDate(StringUtils
						.convertDate(currentValue));
			} else if (localName.equalsIgnoreCase("bankCode")) {
				contractBank.setBankCode(currentValue);
				// }else if (localName.equalsIgnoreCase("name")) {
				// contractBank.setName(currentValue);
			} else if (localName.equalsIgnoreCase("bankContractNo")) {
				contractBank.setBankContractNo(currentValue);
			} else if (localName.equalsIgnoreCase("contractBank")) {
				temp.setContractBank(contractBank);
			}

		}
		// get RepresentativeCustObj
		if (representativeCustObj != null) {
			// if (localName.equalsIgnoreCase("custId")) {
			// representativeCustObj.setCustId(currentValue);
			// } else
			if (localName.equalsIgnoreCase("busType")) {
				representativeCustObj.setBusType(currentValue);
			} else if (localName.equalsIgnoreCase("idType")) {
				representativeCustObj.setIdType(currentValue);
			} else if (localName.equalsIgnoreCase("idNo")) {
				representativeCustObj.setIdNo(currentValue);
			} else if (localName.equalsIgnoreCase("idIssuePlace")) {
				representativeCustObj.setIdIssuePlace(currentValue);
			} else if (localName.equalsIgnoreCase("idIssueDate")) {
				representativeCustObj.setIdIssueDate(StringUtils
						.convertDate(currentValue));
			}
//			} else if (localName.equalsIgnoreCase("name")) {
//				representativeCustObj.setName(currentValue);
//			} 
			else if (localName.equalsIgnoreCase("birthDate")) {
				representativeCustObj.setBirthDate(StringUtils
						.convertDate(currentValue));
			} else if (localName.equalsIgnoreCase("sex")) {
				representativeCustObj.setSex(currentValue);
			} else if (localName.equalsIgnoreCase("nationality")) {
				representativeCustObj.setNationality(currentValue);
//			} else
//				if (localName.equalsIgnoreCase("address")) {
//				representativeCustObj.setAddress(currentValue);
			} 
				else if (localName.equalsIgnoreCase("areaCode")) {
				representativeCustObj.setAreaCode(currentValue);
			} else if (localName.equalsIgnoreCase("province")) {
				representativeCustObj.setProvince(currentValue);
			} else if (localName.equalsIgnoreCase("district")) {
				representativeCustObj.setDistrict(currentValue);
			} else if (localName.equalsIgnoreCase("precinct")) {
				representativeCustObj.setPrecinct(currentValue);
			} else if (localName.equalsIgnoreCase("streetName")) {
				representativeCustObj.setStreetName(currentValue);
			} else if (localName.equalsIgnoreCase("streetBlock")) {
				representativeCustObj.setStreetBlock(currentValue);
			} else if (localName.equalsIgnoreCase("home")) {
				representativeCustObj.setHome(currentValue);
			} else if (localName.equalsIgnoreCase("status")) {
				representativeCustObj.setStatus(currentValue);
			} else if (localName.equalsIgnoreCase("addedUser")) {
				representativeCustObj.setAddedUser(currentValue);
			} else if (localName.equalsIgnoreCase("addedDate")) {
				representativeCustObj.setAddedDate(currentValue);
			} else if (localName.equalsIgnoreCase("code")) {
				switch (codeType) {
				case CODE_CUS_TYPE:
					if (customerType != null) {
						customerType.setCode(currentValue);
					}
					codeType = 0;
					
					break;
				case CODE_CUS_GROUP:
					if (customerGroup != null) {
						customerGroup.setCode(currentValue);
					}
					codeType = 0;
					
					break;
				default:
					break;
				}
			} else if (localName.equalsIgnoreCase("id")) {
				if (isCodeCus) {
					if (customerType != null) {
						customerType.setId(currentValue);
					}
				} else {
					if (customerGroup != null) {
						customerGroup.setId(currentValue);
					}
				}
				
//			    private String popIssuePlace;
//			    private String popIssueDate;
//			    private String popIssueDateStr;
//			    
//			    private String idNoAM;
//			    private String idNoAMIssuePlace;
//			    private String idNoAMIssueDate;
//			    private String idNoAMIssueDateStr;
//			    
//			    private String hc;
//			    private String hcIssueDateStr;
//			    private String hcExpireDateStr;
//			    private String hcIssuePlace;
			}else if (localName.equalsIgnoreCase("popNo")) {
				representativeCustObj.setPopNo(currentValue);
			}else if (localName.equalsIgnoreCase("popIssuePlace")) {
				representativeCustObj.setPopIssuePlace(currentValue);
			}else if (localName.equalsIgnoreCase("popIssueDateStr")) {
				representativeCustObj.setPopIssueDateStr(StringUtils.convertDate(currentValue));
			}else if (localName.equalsIgnoreCase("idNoAM")) {
				representativeCustObj.setIdNoAM(currentValue);
			}else if (localName.equalsIgnoreCase("idNoAMIssuePlace")) {
				representativeCustObj.setIdNoAMIssuePlace(currentValue);
			}else if (localName.equalsIgnoreCase("idNoAMIssueDateStr")) {
				representativeCustObj.setIdNoAMIssueDateStr(StringUtils.convertDate(currentValue));
			}else if (localName.equalsIgnoreCase("hc")) {
				representativeCustObj.setHc(currentValue);
			}else if (localName.equalsIgnoreCase("hcIssueDateStr")) {
				representativeCustObj.setHcIssueDateStr(StringUtils.convertDate(currentValue));
			}else if (localName.equalsIgnoreCase("hcExpireDateStr")) {
				representativeCustObj.setHcExpireDateStr(StringUtils.convertDate(currentValue));
			}else if (localName.equalsIgnoreCase("hcIssuePlace")) {
				representativeCustObj.setHcIssuePlace(currentValue);
			}

			

			
			if (customerType != null) {
				// get custom type
				if (localName.equalsIgnoreCase("addressRequired")) {
					customerType.setAddressRequired(currentValue);
				} else if (localName.equalsIgnoreCase("birthdayRequired")) {
					customerType.setBirthdayRequired(currentValue);
				}
				// else if (localName.equalsIgnoreCase("code")) {
				// customerType.setCode(currentValue);
				// }
				else if (localName.equalsIgnoreCase("custGroupId")) {
					customerType.setCustGroupId(currentValue);
				} else if (localName.equalsIgnoreCase("districtRequired")) {
					customerType.setDistrictRequired(currentValue);
				} else if (localName.equalsIgnoreCase("homeRequired")) {
					customerType.setHomeRequired(currentValue);
				}
				// else if (localName.equalsIgnoreCase("id")) {
				// customerType.setId(currentValue);
				// }
				else if (localName.equalsIgnoreCase("idNoCARequired")) {
					customerType.setIdNoCARequired(currentValue);
				} else if (localName.equalsIgnoreCase("idNoRequired")) {
					customerType.setIdNoRequired(currentValue);
				}
				// else if (localName.equalsIgnoreCase("name")) {
				// customerType.setName(currentValue);
				// if(currentValue != null && !currentValue.isEmpty()){
				// Log.d("name custype" , currentValue);
				// }
				// }
				else if (localName.equalsIgnoreCase("nameRequired")) {
					customerType.setNameRequired(currentValue);
				} else if (localName.equalsIgnoreCase("nationalityRequired")) {
					customerType.setNationalityRequired(currentValue);
				} else if (localName.equalsIgnoreCase("otherRequired")) {
					customerType.setOtherRequired(currentValue);
				} else if (localName.equalsIgnoreCase("passportRequired")) {
					customerType.setPassportRequired(currentValue);
				} else if (localName.equalsIgnoreCase("permitRequired")) {
					customerType.setPermitRequired(currentValue);
				} else if (localName.equalsIgnoreCase("popRequired")) {
					customerType.setPopRequired(currentValue);
				} else if (localName.equalsIgnoreCase("precinctRequired")) {
					customerType.setPrecinctRequired(currentValue);
				} else if (localName.equalsIgnoreCase("provinceRequired")) {
					customerType.setProvinceRequired(currentValue);
				} else if (localName.equalsIgnoreCase("sexRequired")) {
					customerType.setSexRequired(currentValue);
				} else if (localName.equalsIgnoreCase("status")) {
					customerType.setStatus(currentValue);
				} else if (localName.equalsIgnoreCase("streetBlockRequired")) {
					customerType.setStreetBlockRequired(currentValue);
				} else if (localName.equalsIgnoreCase("streetRequired")) {
					customerType.setStreetRequired(currentValue);
				} else if (localName.equalsIgnoreCase("tax")) {
					customerType.setTax(currentValue);
				} else if (localName.equalsIgnoreCase("tinRequired")) {
					customerType.setTinRequired(currentValue);
				} else if (localName.equalsIgnoreCase("updateDate")) {
					customerType.setUpdateDate(currentValue);
				} else if (localName.equalsIgnoreCase("updateUser")) {
					customerType.setUpdateUser(currentValue);
				} else if (localName.equalsIgnoreCase("customerType")) {
					representativeCustObj.setCustomerType(customerType);
				}
			}

			// else
			if (customerGroup != null) {
				// get custom group
				// if (localName.equalsIgnoreCase("code")) {
				// customerGroup.setCode(currentValue);
				// } else

				if (localName.equalsIgnoreCase("custTypeId")) {
					customerGroup.setCustTypeId(currentValue);
				} else if (localName.equalsIgnoreCase("depsciption")) {
					customerGroup.setDepsciption(currentValue);
				}
				// else if (localName.equalsIgnoreCase("id")) {
				// customerGroup.setId(currentValue);
				// if(currentValue != null && !currentValue.isEmpty()){
				// Log.d("id cusgroup" , currentValue);
				// }
				// }
				// else if (localName.equalsIgnoreCase("name")) {
				// customerGroup.setName(currentValue);
				// }
				else if (localName.equalsIgnoreCase("status")) {
					customerGroup.setStatus(currentValue);
				} else if (localName.equalsIgnoreCase("updateDate")) {
					customerGroup.setUpdateDate(currentValue);
				} else if (localName.equalsIgnoreCase("updateUser")) {
					customerGroup.setUpdateUser(currentValue);
				}

				else if (localName.equalsIgnoreCase("customerGroup")) {
					representativeCustObj.setCustomerGroup(customerGroup);
				}
			}
		}
		if (localName.equalsIgnoreCase("representativeCust")) {
			temp.setRepresentativeCustObj(representativeCustObj);
		}

		if (localName.equalsIgnoreCase("lstContract")) {
			lstData.add(temp);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("return")) {
			item = new CommonOutput();
			lstData = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstContract")) {
			addressType = ADDRESS_CONTRACT;
			nameType = NAME_CONTRACT;
			isParseRepresent = true;
			temp = new ConTractBean();
		} else if (localName.equalsIgnoreCase("customer")) {
            addressType = 2;
		} else if (localName.equalsIgnoreCase("representativeCust")) {
			addressType = ADDRESS_REPRECUS;
			nameType = NAME_KHDD;
			isParseRepresent = false;
			representativeCustObj = new RepresentativeCustObj();
		} else if (localName.equalsIgnoreCase("customerType")
				&& representativeCustObj != null) {
			nameType = NAME_CUSTOMER_TYPE;
			codeType = CODE_CUS_TYPE;
			isCodeCus = true;
			customerType = new CustomerType();
		} else if (localName.equalsIgnoreCase("customerGroup")
				&& representativeCustObj != null) {
			isCodeCus = false;
			codeType = CODE_CUS_GROUP;
			nameType = NAME_CUSTOMER_GROUP;
			customerGroup = new CustomerGroup();
		} else if (localName.equalsIgnoreCase("contractBank")) {
            addressType = 3;
			nameType = NAME_CONTRACT_BANK;
			contractBank = new ContractBank();
		}

	}
}
