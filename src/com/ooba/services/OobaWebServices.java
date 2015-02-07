package com.ooba.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.ooba.entity.WebsiteLeadDTO;

public class OobaWebServices {

	public static String apiEndpointForInterestRate;
	public static String apiEndpointForContact;

	public static String username;
	public static String password;

	static {
		username = "ooba.mobile";
		password = "Oob@moB1le";
		apiEndpointForInterestRate = "https://ws-test.ooba.co.za/gateway/services/BondCalculationService?WSDL";
		apiEndpointForContact = "https://ws-test.ooba.co.za/gateway/services/WebsiteLeadService?WSDL";
	}

	public int saveContactDetails(WebsiteLeadDTO websiteLeadDTO) {

		if (websiteLeadDTO.natureOfEnquiry.equals("Prequalify Now")) {
			return createCallMeBackPrequalifyLead(websiteLeadDTO);
		} else if (websiteLeadDTO.natureOfEnquiry.equals("Bond Enquiry")) {
			return createCallMeBackBondLead(websiteLeadDTO);
		} else if (websiteLeadDTO.natureOfEnquiry.equals("Insurance Enquiry")) {
			return createCallMeBackInsuranceLead(websiteLeadDTO);
		} else if (websiteLeadDTO.natureOfEnquiry.equals("General")) {
			return createCallMeBackGeneralLead(websiteLeadDTO);
		}

		return 0;
	}

	public int createCallMeBackGeneralLead(WebsiteLeadDTO websiteLeadDTO) {
		return OobaWebServiceParser
				.parseCreateCallMeBackGeneralLead(createCallMeByNatureOfEnquiry(
						websiteLeadDTO, "createCallMeBackGeneralLead"));
	}

	public int createCallMeBackPrequalifyLead(WebsiteLeadDTO websiteLeadDTO) {
		return OobaWebServiceParser
				.parseCreateCallMeBackPrequalifyLead(createCallMeByNatureOfEnquiry(
						websiteLeadDTO, "createCallMeBackPreQualLead"));
	}

	public int createCallMeBackBondLead(WebsiteLeadDTO websiteLeadDTO) {
		return OobaWebServiceParser
				.parseCreateCallMeBackBondLead(createCallMeByNatureOfEnquiry(
						websiteLeadDTO, "createCallMeBackBondLead"));
	}

	public int createCallMeBackInsuranceLead(WebsiteLeadDTO websiteLeadDTO) {
		return OobaWebServiceParser
				.parseCreateCallMeBackInsuranceLead(createCallMeByNatureOfEnquiry(
						websiteLeadDTO, "createCallMeBackInsuranceLead"));
	}

	private String createCallMeByNatureOfEnquiry(WebsiteLeadDTO websiteLeadDTO,
			String methodName) {

		if (websiteLeadDTO.firstName == null) {
			websiteLeadDTO.firstName = "";

		}
		if (websiteLeadDTO.lastName == null) {
			websiteLeadDTO.lastName = "";

		}
		if (websiteLeadDTO.cellPhone == null) {
			websiteLeadDTO.cellPhone = "";

		}
		if (websiteLeadDTO.email == null) {
			websiteLeadDTO.email = "";

		}
		if (websiteLeadDTO.comments == null) {
			websiteLeadDTO.comments = "";
		}

		String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:lead=\"http://lead.business.web.services.ooba.co.za/\">"
				+ "<soapenv:Header>"
				+ "<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+ "<wsse:UsernameToken wsu:Id=\"UsernameToken-1\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+ "<wsse:Username>"
				+ OobaWebServices.username
				+ "</wsse:Username>"
				+ "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"
				+ OobaWebServices.password
				+ "</wsse:Password>"
				+ "</wsse:UsernameToken>"
				+ "</wsse:Security>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<lead:"
				+ methodName
				+ ">"
				+ "<lead:websiteLeadDTO>"
				+ "<lead:firstName>"
				+ websiteLeadDTO.firstName
				+ "</lead:firstName>"
				+ "<lead:lastName>"
				+ websiteLeadDTO.lastName
				+ "</lead:lastName>"
				+ "<lead:cellPhone>"
				+ websiteLeadDTO.cellPhone
				+ "</lead:cellPhone>"
				+ "<lead:email>"
				+ websiteLeadDTO.email
				+ "</lead:email>"
				+ "<lead:comments>"
				+ websiteLeadDTO.comments
				+ "</lead:comments>"
				+ "<lead:partnerWebSiteCode>oobamobile</lead:partnerWebSiteCode>"
				+ "<lead:workPhone/>"
				+ "<lead:physicalPostalCode/>"
				+ "<lead:title/>"
				+ "<lead:indicatorReceiveMarketingInfo/>"
				+ "<lead:physicalAddress2/>"
				+ "<lead:maritalStatus/>"
				+ "<lead:homePhone/>"
				+ "<lead:physicalAddress1/>"
				+ "<lead:indicatorIdType/>"
				+ "<lead:occupationalStatus/>"
				+ "<lead:leadSource/>"
				+ "<lead:physicalCity/>"
				+ "<lead:idPassportNumber/>"
				+ "<lead:physicalSuburb/>"
				+ "</lead:websiteLeadDTO>"
				+ "</lead:"
				+ methodName
				+ ">"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";

		String response = httpPostCall(OobaWebServices.apiEndpointForContact,
				request);

		return response;

	}

	public double getCurrentInterestRate() {

		String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bond=\"http://bond.calculator.business.services.ops.ooba.co.za/\">"
				+ "<soapenv:Header>"
				+ "<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+ "<wsse:UsernameToken wsu:Id=\"UsernameToken-1\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:Username>"
				+ OobaWebServices.username
				+ "</wsse:Username>"
				+ "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss- username-token-profile-1.0#PasswordText\">"
				+ OobaWebServices.password
				+ "</wsse:Password>"
				+ "</wsse:UsernameToken> </wsse:Security></soapenv:Header><soapenv:Body><bond:getCurrentInterestRate /> </soapenv:Body></soapenv:Envelope>";

		return OobaWebServiceParser.parseGetCurrentInterestRate(httpPostCall(
				OobaWebServices.apiEndpointForInterestRate, request));
	}

	String httpPostCall(String baseUrl, String request) {
		StringBuilder sb = new StringBuilder();

		try {
			ITSSLSocketFactory iTSSLSocketFactory = new ITSSLSocketFactory(null);
			HttpClient httpclient = iTSSLSocketFactory.getNewHttpClient();
			HttpPost httppost = new HttpPost(baseUrl);
			StringEntity se = new StringEntity(request, HTTP.UTF_8);
			se.setContentType("text/xml");
			httppost.setHeader("Content-Type",
					"application/soap+xml;charset=UTF-8");
			httppost.setEntity(se);
			HttpResponse httpResponse = (HttpResponse) httpclient
					.execute(httppost);

			System.out.println(">>> HTTP Status code >>> "
					+ httpResponse.getStatusLine().getStatusCode());

			// if (httpResponse.getEntity().getContentLength() > 0) {
			HttpEntity httpEntity = httpResponse.getEntity();

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpEntity.getContent()), 65728);
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}


		} catch (ClientProtocolException e) {
			System.out.println(">>> Exception " + e + " >>> Message >>> "
					+ e.getMessage());
		} catch (IOException e) {
			System.out.println(">>> Exception " + e + " >>> Message >>> "
					+ e.getMessage());
		} catch (Exception e) {
			System.out.println(">>> Exception " + e + " >>> Message >>> "
					+ e.getMessage());
		}

		return sb.toString();
	}
}
