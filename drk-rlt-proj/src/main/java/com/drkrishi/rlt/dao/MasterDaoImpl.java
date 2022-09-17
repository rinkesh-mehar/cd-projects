package com.drkrishi.rlt.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.drkrishi.rlt.entity.StressSeverity;
import com.drkrishi.rlt.entity.StressSymptoms;

@Repository
public class MasterDaoImpl implements MasterDao {

	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getMasterStressDetails() {
		String hqlQuery = "select s.id, s.name, st.id, st.name from Stress as s inner join StressType as st on s.stressTypeId = st.id";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StressSeverity> getMasterStressSeverity() {
		String hqlQuery = "from StressSeverity";
		Query query = em.createQuery(hqlQuery,StressSeverity.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getMasterStressId(){
		String hqlQuery = "select distinct stressId from StressSymptoms";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StressSymptoms> getMasterStressSymptoms(int stressId){
		String hqlQuery = "from StressSymptoms where stressId = :stressId order by symptomDesc";
		Query query = em.createQuery(hqlQuery,StressSymptoms.class)
				.setParameter("stressId", stressId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getMasterStressDetailsByType(String type, String commodityId, Set<String> stressType) {
		String hqlQuery = "select s.stressId as id, s.name, st.id, st.name from Stress as s inner join StressType as st on s.stressTypeId = st.id where st.name  IN (:stressType) and s.commodityId in ("+commodityId+") group by s.stressId, s.name, st.id, st.name order by s.name";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("stressType", stressType);
		return query.getResultList();
	}
}
