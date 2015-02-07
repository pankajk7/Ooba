package com.ooba.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OobaWebServiceParser {

	public static double parseGetCurrentInterestRate(String responseString) {

		try {
			NodeList nList = getNodeListByTag(responseString,
					"ns2:getCurrentInterestRateResponse");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node;
					return Double.parseDouble(getValue("return", element2));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int parseCreateCallMeBackPrequalifyLead(String responseString) {

		try {
			NodeList nList = getNodeListByTag(responseString,
					"ns0:createCallMeBackPreQualLeadResponse");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node;
					return Integer.parseInt(getValue("ns0:return", element2));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int parseCreateCallMeBackBondLead(String responseString) {

		try {
			NodeList nList = getNodeListByTag(responseString,
					"ns0:createCallMeBackBondLeadResponse");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node;
					return Integer.parseInt(getValue("ns0:return", element2));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int parseCreateCallMeBackInsuranceLead(String responseString) {

		try {
			NodeList nList = getNodeListByTag(responseString,
					"ns0:createCallMeBackInsuranceLeadResponse");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node;
					return Integer.parseInt(getValue("ns0:return", element2));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int parseCreateCallMeBackGeneralLead(String responseString) {

		try {
			NodeList nList = getNodeListByTag(responseString,
					"ns0:createCallMeBackGeneralLeadResponse");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element2 = (Element) node;
					return Integer.parseInt(getValue("ns0:return", element2));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private static NodeList getNodeListByTag(String responseString,
			String tagName) {
		try {
			InputStream is = new ByteArrayInputStream(responseString.getBytes());

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			Element element = doc.getDocumentElement();
			element.normalize();

			return doc.getElementsByTagName(tagName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

}
