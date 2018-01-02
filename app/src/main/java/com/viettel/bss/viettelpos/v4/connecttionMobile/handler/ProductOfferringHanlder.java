package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

public class ProductOfferringHanlder extends DefaultHandler {

    private  enum PARSE_LOCATION{
        PRODUCT,ATRR,SPEC
    }
    private PARSE_LOCATION location;
    private Boolean currentElement = false;
    private String currentValue = "";
    private CommonOutput item = null;
    private ProductOfferingDTO temp = null;
    private ProductOfferingDTO tempArr = null;
    private final int NAME_DES_HTHM = 2;
    private int nameType = 0;
    private ArrayList<ProductOfferingDTO> lstData = null;

    private ArrayList<ProductSpecCharDTO> lstProductSpecCharDTOs;


    public ArrayList<ProductOfferingDTO> lstProductOfferingDTOHasAtrr = null;
    private int checkType = 0;
    private final int checkTypeSpec = 2;
    private final int checkTypeSpecArr = 3;
    private ProductSpecCharDTO productSpecCharDTO = null;

    public CommonOutput getItem() {
        return item;
    }

    public ArrayList<ProductOfferingDTO> getLstData() {
        return lstData;
    }

    public ArrayList<ProductSpecCharDTO> getLstProductSpecCharDTOs() {
        return lstProductSpecCharDTOs;
    }

    public ArrayList<ProductOfferingDTO> getLstProductOfferingDTOHasAtrr() {
        return lstProductOfferingDTOHasAtrr;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        currentElement = false;

        /** set value */
        /* Common */
        if (localName.equalsIgnoreCase("errorCode")) {
            item.setErrorCode(currentValue);
        } else if (localName.equalsIgnoreCase("description")) {
            switch (nameType) {
                case 0:
                    item.setDescription(currentValue);
                    nameType = 0;
                    break;
                case NAME_DES_HTHM:
                    temp.setDescription(currentValue);
                    nameType = 0;
                    break;
                default:
                    break;
            }
        } else if (localName.equalsIgnoreCase("token")) {
            item.setToken(currentValue);
        }

        // GETDATA
        else if (localName.equalsIgnoreCase("productOfferingId")) {
            Log.d("productOfferingId", currentValue);
            switch (location){
                case PRODUCT:
                    temp.setProductOfferingId(Long.parseLong(currentValue));
                    break;
                case ATRR:
                    tempArr.setProductOfferingId(Long.parseLong(currentValue));
                    break;
                default:
                    break;
            }

        } else if (localName.equalsIgnoreCase("name")) {
            switch (location){
                case ATRR:
                    tempArr.setName(currentValue);
                    break;
                case PRODUCT:
                    temp.setName(currentValue);
                    break;
                case SPEC:
                    productSpecCharDTO.setName(currentValue);
                    break;
                default:
                    break;
            }
        } else if (localName.equalsIgnoreCase("code")) {
            switch (location){
                case ATRR:
                    tempArr.setCode(currentValue);
                    break;
                case PRODUCT:
                    temp.setCode(currentValue);
                    break;
                case SPEC:
                    productSpecCharDTO.setCode(currentValue);
                    break;
                default:
                    break;
            }
//            switch (checkType) {
//                case 0:
//                    temp.setCode(currentValue);
//                    break;
//                case checkTypeSpecArr:
//                    tempArr.setCode(currentValue);
//                    break;
//                case checkTypeSpec:
//                    productSpecCharDTO.setCode(currentValue);
//                    break;
//                default:
//                    break;
//            }
        } else if (localName.equalsIgnoreCase("lstProductSpecCharDTOs")) {
            lstProductSpecCharDTOs.add(productSpecCharDTO);

//            checkType = 0;
        } else if (localName.equalsIgnoreCase("lstProductOfferingDTO")) {
            lstData.add(temp);
        } else if (localName.equalsIgnoreCase("lstProductOfferingDTOHasAtrr")) {
            tempArr.setLstProductSpecCharDTOs(lstProductSpecCharDTOs);
            lstProductOfferingDTOHasAtrr.add(tempArr);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentElement = true;
        currentValue = "";
        if (localName.equalsIgnoreCase("return")) {
            item = new CommonOutput();
            lstData = new ArrayList<>();
            lstProductSpecCharDTOs = new ArrayList<>();
            lstProductOfferingDTOHasAtrr = new ArrayList<>();
            nameType = 0;
            checkType = 0;
        } else if (localName.equalsIgnoreCase("lstProductOfferingDTO")) {
            location = PARSE_LOCATION.PRODUCT;
            nameType = NAME_DES_HTHM;
            temp = new ProductOfferingDTO();
            checkType = 0;
        } else if (localName.equalsIgnoreCase("lstProductOfferingDTOHasAtrr")) {
            location = PARSE_LOCATION.ATRR;
            nameType = NAME_DES_HTHM;
            tempArr = new ProductOfferingDTO();
            lstProductSpecCharDTOs = new ArrayList<>();
            checkType = checkTypeSpecArr;
        } else if (localName.equalsIgnoreCase("lstProductSpecCharDTOs")) {
            location = PARSE_LOCATION.SPEC;
            checkType = checkTypeSpec;
            productSpecCharDTO = new ProductSpecCharDTO();
        }
    }


}
