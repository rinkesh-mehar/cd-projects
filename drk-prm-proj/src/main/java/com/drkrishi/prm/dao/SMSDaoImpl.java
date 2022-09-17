package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.Sms;
import com.drkrishi.prm.entity.SmsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Repository
@Component
public class SMSDaoImpl implements SMSDao{

private static final Logger LOGGER = LogManager.getLogger(SMSDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	// get sms template
	@Override
	public SmsTemplate getSMSTemplate(String templateName) {
		
		SmsTemplate user = null;
		try {
			Query  query = em.createNativeQuery("select * from sms_template where name=?", SmsTemplate.class)
					.setParameter(1, templateName);
			
			if ( query.getResultList().size() != 0 ) 
				 user = (SmsTemplate)query.getResultList().get(0);	
			
			return user;

		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return null;
		}
	}
	
	// save sms in database
	@Transactional
	@Override
	public boolean saveSMSMessage(Sms smsMessage) {
		try {
			em.persist(smsMessage);
			em.close();
			return true;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
	}
	
}
