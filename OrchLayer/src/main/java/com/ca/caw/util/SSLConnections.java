package com.ca.caw.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.log4j.Logger;

public class SSLConnections {

    private static Logger logger = Logger.getLogger(SSLConnections.class);

	public static Client allowSSLConnections() {
		logger.debug("SSL Connection");
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, getTrustManager(), null);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {

		}
		final Client client = ClientBuilder.newBuilder().hostnameVerifier(new HostnameVerifier() {
			public boolean verify(String string, SSLSession ssls) {
				return true;
			}
		}).sslContext(sslContext).build();
		return client;
	}

	private static TrustManager[] getTrustManager() {

		return new TrustManager[] { new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}
		} };
	}
}
