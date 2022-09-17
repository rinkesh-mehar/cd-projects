package com.krishi.config;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.krishi.properties.AppProperties;
//import in.cropdata.toolsuite.sdk.FileManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.krishi.entity.StaticData;
import com.krishi.repository.StaticDataRepository;
import com.sendgrid.SendGrid;

@Configuration
public class SpringConfig {

	/*
	 * @PersistenceContext private EntityManager em;
	 */

	@Autowired
	private StaticDataRepository staticDataRepository;

	@Autowired
	private AppProperties appConfig;

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	/*Sms gateway configuration*/
	@Bean
	public SendGrid sendGrid() {
		String apiKey = "";
		StaticData staticData = staticDataRepository.findByKey("SendGridKey");
		if (staticData != null) {
			apiKey = staticData.getValue();
		}
		SendGrid sg = new SendGrid(apiKey);
		return sg;
	}

	/*Rest template configuration*/
	@Bean
//  @LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AppProperties appProperties(){
		return new AppProperties();
	}

	/*@Bean
	public FileManagerUtil getFileManagerUtil() {
		String filemanagerURL = appConfig.getFileManagerURL();// "http://api.cropdata.tk/file-manager/";
		return new FileManagerUtil(filemanagerURL, restTemplate);
	}*/
}
