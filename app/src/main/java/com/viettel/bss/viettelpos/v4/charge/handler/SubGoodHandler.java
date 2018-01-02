package com.viettel.bss.viettelpos.v4.charge.handler;

import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by thinhhq1 on 7/14/2017.
 */


public class SubGoodHandler extends DefaultHandler {
    private Boolean currentElement = false;
    private String currentValue = "";
    private CommonOutput item = null;
    private SubGoodsDTO temp = null;

    private final int NAME_DES = 1;
    private final int NAME_DES_HTHM = 2;
    private int nameType = 0;
    private ArrayList<SubGoodsDTO> lstData = null;
    public CommonOutput getItem() {
        return item;
    }
    public ArrayList<SubGoodsDTO> getLstData() {
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
            switch (nameType) {
                case 0:
                    item.setDescription(currentValue);
                    nameType = 0;
                    break;
                case NAME_DES_HTHM:
                    item.setDescription(currentValue);
                    nameType = 0;
                    break;
                default:
                    break;
            }


        } else if (localName.equalsIgnoreCase("token")) {
            item.setToken(currentValue);
        }

        // GETDATA
        else if (localName.equalsIgnoreCase("serial")) {
            temp.setSerial(currentValue);
        } else if (localName.equalsIgnoreCase("serialToRetrieve")) {
            temp.setSerialToRetrieve(currentValue);
        } else if (localName.equalsIgnoreCase("stockModelCode")) {
            temp.setStockModelCode(currentValue);
        } else if (localName.equalsIgnoreCase("stockModelName")) {
            temp.setStockModelName(currentValue);
        }else if (localName.equalsIgnoreCase("stockModelId")) {
            temp.setStockModelId(currentValue);
        }else if (localName.equalsIgnoreCase("subGoodsId")) {
            temp.setSubGoodsId(currentValue);
        }
        else if (localName.equalsIgnoreCase("lstSubGoodsDTOs")) {
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
            nameType = 0;
        } else if (localName.equalsIgnoreCase("lstSubGoodsDTOs")) {
            nameType = NAME_DES_HTHM;
            temp = new SubGoodsDTO();
        }

    }

}
