package com.drkrishi.usermanagement.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.drkrishi.usermanagement.entity.UserCredentials;
import com.drkrishi.usermanagement.entity.Users;



@Repository
@Component
public class CreateUserDaoImpl implements CreateUserDao {

	private static final Logger LOGGER = LogManager.getLogger(CreateUserDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public int createUser(Users entity) {

		try {
			em.persist(entity);
			em.close();	
			
			return entity.getId();	
		}catch(Exception e) {
			LOGGER.error("::", e.fillInStackTrace());
			return 0;
		}
	}
	
	
	@Transactional
	@Override
	public boolean createUserCredientials(UserCredentials credientials) {
		
		try {
			em.persist(credientials);
			return true;
		}catch (Exception e) {
			LOGGER.error("::", e.fillInStackTrace());
			return false;
		}
		
	}
 
	@Transactional
	@Override
	public boolean updateUserCredientials(UserCredentials credientials) {
		try {
			em.persist(credientials);
			return true;
		}catch (Exception e) {
			LOGGER.error("::", e.fillInStackTrace());
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean updateUserDetails(Users credientials) {
		try {
			em.persist(credientials);
			return true;
		}catch (Exception e) {
			LOGGER.error("::", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public Users getUserByMobileNumber(String mobileNumber) {
		
		try {
			String hql = "from Users user where user.mobileNumber = :mobileNumber";
			List result = em.createQuery(hql).setParameter("mobileNumber", mobileNumber).getResultList();
			
			if ( result.size() != 0 ) {
				System.out.println(" getUserByMobileNumber " + mobileNumber );
				return (Users) result.get(0);
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public UserCredentials getUserCredientials(int userId) {
		
		try {
			
			String hql = "from UserCredentials user where user.userId = :userId";
			List<UserCredentials> result = em.createQuery(hql).setParameter("userId", userId).getResultList();
			
			if ( result.size() != 0 ) {				
				return (UserCredentials) result.get(0);
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public boolean validateUserPassword(int userId, String password) {
		
		try {
			
			/*Query  query = em.createQuery(" from UserCredentials user where user.userId="+userId+" and user.userPassword='"+password+"' ");
			boolean validpassword = ( query.getResultList().size() == 1 ) ? true : false ;
			return validpassword; */
			
			String hql = "from UserCredentials user where user.userId = :userId and user.userPassword = :userPassword";
			Query  query = em.createQuery(hql).setParameter("userId", userId).setParameter("userPassword", password);
			boolean validpassword = ( query.getResultList().size() == 1 ) ? true : false ;
			return validpassword;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public Users getUserByEmailAddress(String emailAddress) {
		
		try {
			
			String hql = "from Users user where user.emailAddress = :emailAddress";
			List result = em.createQuery(hql).setParameter("emailAddress", emailAddress).getResultList();
			
			if ( result.size() != 0 ) {
				System.out.println(" getUserByEmailAddress " + emailAddress );
				return (Users) result.get(0);
			}
			

		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public boolean validateEmailAddress(String emailAddress) {
		try {
			
			Query  query = em.createNativeQuery("select * from users where email_address=?", Users.class)
					.setParameter(1, emailAddress);
			
			if ( query.getResultList().size() != 0 ) 
				 return true;
			else 
				return false;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public boolean validateMobileNumber(String mobileNumber) {
		try {
			
			Query  query = em.createNativeQuery("select * from users where mobile_number=?", Users.class)
					.setParameter(1, mobileNumber);
			
			if ( query.getResultList().size() != 0 ) 
				 return true;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
		return false;
	}
	
	@Override
	public boolean validateUpdateMobileNumber(String mobileNumber, int userId) {
		try {
			
			Query  query = em.createNativeQuery("select * from users where mobile_number=? and id !=?" , Users.class)
					.setParameter(1, mobileNumber).setParameter(2, userId);
			
			if ( query.getResultList().size() != 0 ) 
				 return true;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
		return false;
	}
	
	@Override
	public boolean validateUpdateEmailAddress(String emailAddress, int userId) {
		try {
			
			Query  query = em.createNativeQuery("select * from users where email_address=? and id !=?" , Users.class)
					.setParameter(1, emailAddress).setParameter(2, userId);
			
			if ( query.getResultList().size() != 0 ) 
				 return true;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
		return false;
	}
	
	
	@Override
	public List<Users> getUserListByUserId(int userId) {
		
		try {
			
//			Query  query = em.createQuery("from Users where reportingTo = '"+userId+"'");
			
			String hql = "from Users where reportingTo = :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			
			if( query.getResultList().size() != 0 ) {
				List<Users> list = query.getResultList();			
				return list;
			}else {
				return null;
			}			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());			
		}
		return null;
	}
	
	
	@Override
	public List<Integer> getUserIdListByUserId() {
		
		List<Integer> userIdList = new ArrayList<Integer>();
		
		try {
			
			Query  query = em.createQuery(" from Users ");
			List<Users> list = query.getResultList();
			list.forEach( drkuser -> {
				userIdList.add(drkuser.getId());
			});
			return userIdList;
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUserList() {
		
		List<Users> list = new ArrayList<Users>();
		try {
			
			Query  query = em.createQuery(" from Users ");
			list = query.getResultList();
			return list;
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	

	
	public List<Users> getlistOfReportingTo(int stateId, int regionId, int reportingRoleId) {
		List<Users> list = new ArrayList<Users>();
		try {
			
//			Query  query = em.createQuery(" from Users where state = '"+stateId+"' and region = '"+regionId+"' and reportingRoleId = '"+reportingRoleId+"' ");
			Query  query = em.createQuery("select d from Users as d,UserRoles as ur  where d.stateId = '"+stateId+"' and d.id = ur.userId and ur.roleId ='"+reportingRoleId+"' ");
			if( query.getResultList().size() != 0 ) {
				return  query.getResultList();
			}			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return list;	
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getSpecificUserList(int userId) {
		
		List<Users> list = new ArrayList<Users>();
		try {
			
//			Query  query = em.createQuery(" from Users user where user.reportingTo = '"+userId+"' ");
			
			String hql = "from Users where reportingTo = :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			list = query.getResultList();
			return list;
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public int getInvalidLoginAttemptCount(int userId) {
		
		try {
//			Query  query = em.createQuery("from UserCredentials user where user.userId= "+userId+" ");
			
			String hql = "from UserCredentials user where user.userId= :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			if( query.getResultList().size() != 0 ) {
				UserCredentials credientials = (UserCredentials) query.getResultList().get(0);
				return credientials.getInvalidAttempts();
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return 0;
	}
	
	@Override
	public Users viewUserDetails(int userId) {
		
		try {
			
			String hql = "from Users user where user.id = :userId";
			List<Users> result = em.createQuery(hql).setParameter("userId", userId).getResultList();
			
			if ( result.size() != 0 ) {
				return (Users) result.get(0);
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public List<Users> getReportingTo(int userId) {
		
		try {
			
//			Query  query = em.createQuery("from Users user where user.id = '"+userId+"' ");
			
			String hql = "from Users user where user.id = :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			return query.getResultList();
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public Users getReportingToByState(int userId, int state) {
		
		try {
			
//			Query  query = em.createQuery("from Users user where user.stateId = '"+state+"' and user.id = "+userId+" ");
			
			String hql = "from Users user where user.stateId = :stateId and user.id = :userId";
			Query query = em.createQuery(hql);
			query.setParameter("stateId", state);
			query.setParameter("userId", userId);			
			
			if ( query.getResultList().size() != 0 ) {
				Users krishiUsers = (Users) query.getResultList().get(0);
				return krishiUsers;	
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public Users getReportingToByStateRegion(int userId, int state, int region) {
		
		try {
			
//			Query  query = em.createQuery(" from Users user where user.stateId = '"+state+"' and user.regionId = '"+region+"' and  user.id = "+userId+" ");
			
			String hql = "from Users user where user.id = :userId and user.stateId = :stateId and user.regionId = :regionId";
			Query query = em.createQuery(hql);
			query.setParameter("userId", userId);
			query.setParameter("stateId", state);
			query.setParameter("regionId", region);			
			
			
			if ( query.getResultList().size() != 0 ) {
				Users krishiUsers = (Users) query.getResultList().get(0);
				return krishiUsers;	
			}
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public List<Users> getReportingTo(int userId, int state, int region) {
		
		try {
//			Query  query = em.createQuery(" from Users user where user.id = '"+userId+"' and user.stateId = '"+state+"' and user.regionId = '"+region+"' ");
			
			String hql = "from Users user where user.id = :userId and user.stateId = :stateId and user.regionId = :regionId";
			Query query = em.createQuery(hql);
			query.setParameter("userId", userId);
			query.setParameter("stateId", state);
			query.setParameter("regionId", region);		
			
			return query.getResultList();
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	/**
	 * get user details based on user_id
	 * */
	@Override
	public Users getUserByUserId(int userId){
		try {
			String hql ="from Users user where user.id = :userId";
			Query query = em.createQuery(hql);
			query.setParameter("userId", userId);

			return (Users) query.getResultList().get(0);
		}catch (Exception e){
			LOGGER.info("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public Users findById(int userId) {
		
		try {
			Query  query = em.createNativeQuery("select * from users where id=?", Users.class)
					.setParameter(1,userId);
			
			//System.out.println( query.getResultList().size());
					if ( query.getResultList().size() != 0 ) {
						Users krishiUsers = (Users) query.getResultList().get(0);
						
						return krishiUsers;	
					}
					
				}catch(Exception e) {
					LOGGER.error("", e.fillInStackTrace());
				}
				return null;
	
	
	}


	@Override
	public UserCredentials getUserCredentialsByid(int userId) {
try {
			
			Query  query = em.createNativeQuery("select * from user_credentials where user_id=?", UserCredentials.class)
					.setParameter(1, userId);
			
			if ( query.getResultList().size() != 0 ) 
				 return (UserCredentials)query.getResultList().get(0);	
			
		}catch(Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}}
