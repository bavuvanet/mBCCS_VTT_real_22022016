package com.viettel.bss.viettelpos.v4.synchronizationdata;

import android.util.Log;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlDomParse {
	//Returns the entire XML document 
    public Document getDocument(InputStream inputStream) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        return document;
    }
 
    public String getValue(Element item, String name) {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }
	public ArrayList<String> getValue2(Element item, String name) {
		ArrayList<String> lisStrings = new ArrayList<>();
		NodeList nodes = item.getElementsByTagName(name);
		for (int i = 0; i < nodes.getLength(); i++) {
			lisStrings.add(getTextNodeValue(nodes.item(i)));
		}
		return lisStrings;
	}
    // method parse xml using DOMPASER
 	public Document getDomElement(String xml) {
 		Document doc = null;
 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 		dbf.setCoalescing(true);
 		try {
 			DocumentBuilder db = dbf.newDocumentBuilder();
 			InputSource is = new InputSource();
 			is.setCharacterStream(new StringReader(xml));
 			doc = db.parse(is);
 		} catch (ParserConfigurationException e) {
 			return null;
 		} catch (SAXException e) {
 			return null;
 		} catch (IOException e) {
 			return null;
 		}
 		return doc;
 	}
 	
 	public static Document XMLfromString(String xml){

 	    Document doc = null;

 	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 	    try {

 	        DocumentBuilder db = dbf.newDocumentBuilder();

 	        InputSource is = new InputSource();
 	        is.setCharacterStream(new StringReader(xml));
 	        doc = db.parse(is); 

 	    } catch (ParserConfigurationException e) {
 	        System.out.println("XML parse error: " + e.getMessage());
 	        return null;
 	    } catch (SAXException e) {
 	        System.out.println("Wrong XML file structure: " + e.getMessage());
 	        return null;
 	    } catch (IOException e) {
 	        System.out.println("I/O exeption: " + e.getMessage());
 	        return null;
 	    }

 	    return doc;

 	}

 	/** Returns element value
 	  * @param elem element (it is XML tag)
 	  * @return Element value otherwise empty String
 	  */
 	 public static String getElementValue(Node elem ) {
 	     Node kid;
 	     if( elem != null){
 	         if (elem.hasChildNodes()){
 	             for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
 	                 if( kid.getNodeType() == Node.TEXT_NODE  ){
 	                     return kid.getNodeValue();
 	                 }
 	             }
 	         }
 	     }
 	     return "";
 	 }




 	public static int numResults(Document doc){     
 	    Node results = doc.getDocumentElement();
 	    int res = -1;

 	    try{
 	        res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
 	    }catch(Exception e ){
 	        res = -1;
 	    }

 	    return res;
 	}


 	public static String getCharacterDataFromElement(Element e) {
 	      Node child = e.getFirstChild();
 	      if (child instanceof CharacterData) {
 	         CharacterData cd = (CharacterData) child;
 	         return cd.getData();
 	      }
 	      return "?";
 	    }
 	
 	
 	
    private String getTextNodeValue(Node node) {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                while(child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue().trim();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return "";
    }
}
