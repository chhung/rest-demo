package com.workflow.restdemo.net;

import java.security.cert.X509Certificate;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class HttpRestful {

	@Bean
	@Primary
    @Autowired
	public RestTemplate restV1(@Qualifier("simpleClientHttpRequestFactory") 
					ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}
	
	@Bean
	@Autowired
	public RestTemplate restfulWithSSL(@Qualifier("SSLHttpsRequestFactory")
	                ClientHttpRequestFactory factory) {
	    return new RestTemplate(factory);
	}
	
    @Bean
    protected ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        // 如果對外有設定firewall且要對外連線則用Proxy類別設定防火牆
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("stfirewall", 8080));

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(60*1000);
        factory.setConnectTimeout(60*1000);
        //factory.setProxy(proxy);
        return factory;
    }
    
    @Bean
    protected ClientHttpRequestFactory SSLHttpsRequestFactory() throws Exception {
        try {
            org.apache.http.ssl.TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            javax.net.ssl.SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory((javax.net.ssl.SSLContext) sslContext, 
              NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = 
                    RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();

            BasicHttpClientConnectionManager connectionManager = 
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();
                  
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(60 * 1000);
            factory.setReadTimeout(60 * 1000);
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
