package com.testvagrant.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class HttpService {

	static Logger logger = (Logger) LogManager.getLogger(HttpService.class);

	// This method to serve the response of the GET HTTP Calls
	public static HttpResponse get(String url, HashMap<String, String> headers) throws Exception {
		HttpClientBuilder client = getHttpClient(url);
		try {
			HttpGet request = new HttpGet(url);
			if (headers != null) {
				Iterator<Entry<String, String>> it = headers.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();

					if ((pair.getKey() == null) && (pair.getValue() == null)) {
						request.addHeader(pair.getKey().toString(), pair.getValue().toString());
					} else if (pair.getKey() == null) {
						request.addHeader(null, pair.getValue().toString());
					} else if (pair.getValue() == null) {
						request.addHeader(pair.getKey().toString(), null);
					} else {
						request.addHeader(pair.getKey().toString(), pair.getValue().toString());
					}
				}
			}
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			HttpResponse response = client.build().execute(request);
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	// This will build the HttpClient based on the URL given i.e. will SSL
	// certificate if URL having HTTPS
	public static HttpClientBuilder getHttpClient(String url) throws Exception {
		try {
			logger.info("Requested URI: " + url);
			if (url.contains("https")) {
				HttpClientBuilder sslClient = HttpClientBuilder.create();
				HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
				SSLContextBuilder sslBuilder = new SSLContextBuilder();
				sslBuilder.loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
							throws java.security.cert.CertificateException {
						return false;
					}
				});
				SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslBuilder.build(),
						hostnameVerifier);
				sslClient.setSSLSocketFactory(sslConnectionFactory);
				Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
						.register("https", sslConnectionFactory).build();
				HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
				HttpClientBuilder client = HttpClientBuilder.create().setConnectionManager(ccm);
				return client;
			} else {
				return HttpClientBuilder.create();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
