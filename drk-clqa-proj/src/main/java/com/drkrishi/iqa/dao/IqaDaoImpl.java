package com.drkrishi.iqa.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.drkrishi.iqa.entity.*;
import org.springframework.stereotype.Repository;

import com.drkrishi.iqa.model.BenchmarkedImageRejectionModel;
import com.drkrishi.iqa.model.IqaTaskListModel;

@Repository
public class IqaDaoImpl implements IqaDao {

	@PersistenceContext
	private EntityManager em;

	/*
	 * @Override public StaticData getStaticData() { String hqlQuery =
	 * "from StaticData where id = 1"; Query query = em.createQuery(hqlQuery,
	 * StaticData.class); return (StaticData) query.getResultList().get(0); }
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StaticData> getStaticData(List<String> keys) {
		String hql = "select s from StaticData s where s.dataKey in (:key)";
		Query query = em.createQuery(hql, StaticData.class);
		query.setParameter("key", keys);
		return  query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> checkTaskAssigned(int qaId) {
		String hqlQuery = "select count(id) from Task"
				+ " where taskTypeId = 14 and assigneeId = :assigneeId and status = 1 and taskDate = :taskDate";
		Query query = em.createQuery(hqlQuery).setParameter("taskDate", new Date(System.currentTimeMillis()))
				.setParameter("assigneeId", qaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getQaUserCount() {
		String hqlQuery = "select count(u.id) from Users as u inner join UserRoles as ur ON ur.userId = u.id"
				+ " where ur.roleId = 1";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getGroupBenchmarkedImageByCommodityStateRegion() {
		Query query = em.createQuery(
				"select commodityName, stateName, regionName from ViewBenchmarkedImage where benchmarkedImageStatus = 0 group by commodityName, stateName, regionName ");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewBenchmarkedImage> getGroupedBenchmarkedImageDetails(String commodityName, String stateName,
			String regionName) {

		String hqlQuery = "from ViewBenchmarkedImage where benchmarkedImageStatus = 0 and commodityName = :commodityName and stateName = :stateName and regionName = :regionName";
		Query query = em.createQuery(hqlQuery, ViewBenchmarkedImage.class).setParameter("commodityName", commodityName)
				.setParameter("stateName", stateName).setParameter("regionName", regionName);
		return query.getResultList();
	}

	@Transactional
	@Override
	public int assignTask(List<String> caseIds, int qaId) {
		String hqlQuery = "update Task set "
				+ " taskDate = :taskDate, taskTime = :taskTime, assigneeId = :assigneeId, status = 1"
				+ " where entityId in (:caseId) and entityTypeId = 5 and assigneeId = 0 and status = 0";

		Query query = em.createQuery(hqlQuery).setParameter("taskDate", new Date(System.currentTimeMillis()))
				.setParameter("taskTime", new Time(System.currentTimeMillis())).setParameter("assigneeId", qaId)
				.setParameter("caseId", caseIds);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAssignedTask(int qaId) {
		String hqlQuery = " select commodityName, stateName, regionName from ViewBenchmarkedImage where assigneeId = :assigneeId and taskStatus = 1 group by commodityName, stateName, regionName ";
		Query query = em.createQuery(hqlQuery).setParameter("assigneeId", qaId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllIqaTask() {
//		String hqlQuery = " select commodityName, stateName, regionName from ViewBenchmarkedImage where assigneeId = 0 and taskStatus = 0 and taskTypeId = 14 group by commodityName, stateName, regionName ";
		String hqlQuery = "select c.name as commodityName,s.stateName, r.regionName, c.id as commodityId, s.stateId, r.regionId from Farmer f \n" +
				"inner join Village v on v.id = f.villageId\n" +
				"inner join FarmerFarm ff on ff.farmerId = f.id\n" +
				"inner join FarmCase fc on fc.farmId = ff.id\n" +
				"inner join Task t on t.entityId = fc.id\n" +
				"inner join SubTask sb on sb.taskId =t.id \n" +
				"inner join State s on s.stateId = v.stateId\n" +
				"inner join Region r on r.regionId = v.regionId\n" +
				"inner join CaseCropInfo cci on cci.caseId = t.entityId\n" +
				"inner join Variety vt on vt.id = cci.varietyId\n" +
				"inner join Commodity c on c.id = vt.commodityId\n" +
				"where sb.imageIsVerified=0 and t.taskTypeId=6 group by s.stateId, r.regionId,c.id";
//		Query query = em.createQuery(hqlQuery);
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getHealthImageDetails(Integer stateId,
												Integer regionId, Integer commodityId){
		String hqlQuery = "select thp.id as id, thp.taskId,thp.leftPhoto, thp.rightPhoto, " +
				" thp.frontPhoto, c.id as commodityId, thp.spotId from TaskHealthPhoto as thp\n" +
				"inner join Task t on t.id = thp.taskId\n" +
				"inner join CaseCropInfo cci on cci.caseId = t.entityId \n" +
				"inner join Variety v on v.id = cci.varietyId\n" +
				"inner join Commodity c on c.id = v.commodityId\n" +
				"where thp.spotId in((select ts.id from TaskSpot ts inner join Task t on t.id = ts.taskId\n" +
				"inner join SubTask st on st.taskId=t.id \n" +
				"inner join FarmCase fc on fc.id = t.entityId\n" +
				"inner join FarmerFarm ff on ff.id = fc.farmId\n" +
				"inner join Farmer f  on f.id = ff.farmerId\n" +
				"inner join Village v on v.id = f.villageId\n" +
				"inner join State s on s.id = v.stateId\n" +
				"inner join Region r on r.id = v.regionId\n" +
				"inner join CaseCropInfo cci on cci.caseId = t.entityId\n" +
				"inner join Variety vt on vt.id = cci.varietyId\n" +
				"inner join Commodity c on c.id = vt.commodityId\n" +
				"where t.taskTypeId=6 and st.imageIsVerified =0 and v.stateId = :stateId and v.regionId=:regionId \n" +
				"and c.id= :commodityId))";

		Query query = em.createQuery(hqlQuery)
				//.setParameter("assigneeId", iqaTaskListModel.getQaId())
//				.setParameter("commodityId", iqaTaskListModel.getCommodityId())
				.setParameter("stateId", stateId)
				.setParameter("regionId", regionId)
				.setParameter("commodityId", commodityId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	/*@Override
	public List<Object> getBenchmarkedImageStressDetails(IqaTaskListModel iqaTaskListModel) {

		String hqlQuery = " select stressName from ViewBenchmarkedImage where assigneeId = 0 and taskStatus = 0 and taskTypeId = 14 "
				+ " and commodityName = :commodityName and stateName = :stateName and regionName = :regionName group by stressName";
		Query query = em.createQuery(hqlQuery)
				//.setParameter("assigneeId", iqaTaskListModel.getQaId())
				.setParameter("commodityName", iqaTaskListModel.getCommodityName())
				.setParameter("stateName", iqaTaskListModel.getStateName())
				.setParameter("regionName", iqaTaskListModel.getRegionName());
		return query.getResultList();
	}*/
	@Override
	public List<Object[]> getStressDetails(String spotId) {

		/*String hqlQuery = "select distinct s.id, s.name  from stress s\n" +
				"inner join StressSymptoms ss on ss.stressId = s.id\n" +
				"inner join TaskStressDetails tsd on tsd.symptomId = ss.id\n" +
				"inner join Task t on t.id = tsd.taskId\n" +
				"inner join SubTask st on st.taskId = t.id\n" +
				"inner join FarmCase fc on fc.id = t.entityId\n" +
				"inner join FarmerFarm ff on ff.id = fc.farmId\n" +
				"inner join Farmer f  on f.id = ff.farmerId\n" +
				"inner join Village v on v.id = f.villageId\n" +
				"inner join Commodity c on c.id= ss.commodityId\n" +
				"where st.imageIsVerified=0 and t.taskTypeId=6 and v.stateId = :stateId and v.regionId=:regionId\n" +
				" and c.id= :commodityId";
		Query query = em.createQuery(hqlQuery)
				//.setParameter("assigneeId", iqaTaskListModel.getQaId())
//				.setParameter("commodityId", iqaTaskListModel.getCommodityId())
				.setParameter("stateId", iqaTaskListModel.getStateId())
				.setParameter("regionId", iqaTaskListModel.getRegionId())
				.setParameter("commodityId", iqaTaskListModel.getCommodityId());*/

		String hqlQuery = "select distinct tss.stressId,s.name from TaskSpotStress tss \n" +
				"inner join TaskSpot ts on ts.id = tss.taskSpotId \n " +
				"inner join stress s on s.stressId = tss.stressId where ts.id = :spotId";
		Query query = em.createQuery(hqlQuery)
				.setParameter("spotId", spotId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	/*@Override
	public List<ViewBenchmarkedImage> getBenchmarkedImageDetails(int qaId, String commodityName, String stateName,
			String regionName, String stressName) {

		String hqlQuery = " from ViewBenchmarkedImage where assigneeId = 0 and taskStatus = 0 and taskTypeId = 14 "
				+ " and commodityName = :commodityName and stateName = :stateName and regionName = :regionName and stressName = :stressName";
		
		Query query = em.createQuery(hqlQuery, ViewBenchmarkedImage.class)
				//.setParameter("assigneeId", qaId)
				.setParameter("commodityName", commodityName)
				.setParameter("stateName", stateName)
				.setParameter("regionName", regionName)
				.setParameter("stressName", stressName);
		return query.getResultList();
	}*/
	@Override
	public List<Object[]> getStressImageDetails(String spotId, Integer stressId , Integer commodityId) {

		String hqlQuery = "select distinct tsssi.id as taskStressSpotSymtomImageId,tsssi.imageUrl,tsssi.side,s.name as stressName,\n " +
				"ss.symptomDesc as symptomName,\n" +
				" c.name as commodityName, ts.id from TaskSpotStress tss\n" +
				"inner join TaskStressSpotSymptoms tsss on tss.id = tsss.taskSpotStressId\n "+
				"inner join TaskStressSpotSymptomImages tsssi on tsssi.taskSpotStressSymptomId = tsss.id\n "+
				"inner join StressSymptoms ss on ss.id = tsss.symptomId\n" +
				"inner join stress s on s.stressId = ss.stressId \n" +
				"inner join TaskSpot ts on ts.id = tss.taskSpotId \n" +
				"inner join Task t on t.id = ts.taskId\n" +
				"inner join CaseCropInfo cci on cci.caseId = t.entityId \n" +
				"inner join Variety v on v.id = cci.varietyId\n" +
				"inner join Commodity c on c.id = v.commodityId and c.id = s.commodityId\n" +
				" where tss.taskSpotId = :spotId and s.stressId=:stressId and s.commodityId = :commodityId and tsssi.status ='Pending'";

		Query query = em.createQuery(hqlQuery)
//				.setParameter("assigneeId", qaId)
//				.setParameter("stateId", stateId)
//				.setParameter("regionId", regionId)
				.setParameter("commodityId", commodityId)
				.setParameter("stressId", stressId)
				.setParameter("spotId", spotId);
		return query.getResultList();
	}
	@Override
	public List<Object[]> getHealthImageDetails(String spotId){
		String hqlQuery = "select tshi.id, tshi.side, tshi.imageUrl from TaskSpotHealthImage tshi \n"+
				"inner join TaskSpot ts on ts.id = tshi.taskSpotId where ts.id = :spotId and tshi.status= 'Pending'";
		Query query = em.createQuery(hqlQuery)
				.setParameter("spotId", spotId);
		return query.getResultList();

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTask(String taskId) {
		/*Query query = em.createQuery(" from Task where taskTypeId = 14 and entityId = :caseId and entityTypeId = 5",Task.class)*/
		Query query = em.createQuery("from Task " +
				" where id = :taskId and taskTypeId = 6",Task.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	/** update image and status against spot_stress_symptom_image_id */
	@Transactional
	@Override
	public void updateSpotStressSymptomImages(String imageUrl,String spotStressSymptomImageId,String status){
		Query query;
		String hqlQuery = "update TaskStressSpotSymptomImages set \n";
		if (status.equals("Verified")) {
			hqlQuery = hqlQuery + "imageUrl = :imageUrl, status = :status where id= :id";
			query = em.createQuery(hqlQuery)
					.setParameter("imageUrl", imageUrl)
					.setParameter("status", status)
					.setParameter("id", spotStressSymptomImageId);
		} else {
			hqlQuery = hqlQuery + "status = :status where id= :id";
			query = em.createQuery(hqlQuery)
					.setParameter("status", status)
					.setParameter("id", spotStressSymptomImageId);
		}
		query.executeUpdate();


	}
	/** update image and status against spot_health_image_id */
	@Transactional
	@Override
	public void updateSpotHealthImages(String imageUrl, String spotHealthImageId, String status){
		Query query;
		String hqlQuery = "update TaskSpotHealthImage set \n";
		if (status.equals("Verified")) {
			hqlQuery = hqlQuery + "imageUrl =:imageUrl, status =:status where id =:id";
			query = em.createQuery(hqlQuery)
					.setParameter("imageUrl", imageUrl)
					.setParameter("status", status)
					.setParameter("id", spotHealthImageId);
		} else {
			hqlQuery = hqlQuery + " status =:status where id =:id";
			query = em.createQuery(hqlQuery)
					.setParameter("status", status)
					.setParameter("id", spotHealthImageId);
		}

		query.executeUpdate();
	}

	@Transactional
	@Override
	public void uploadEditedBenchmarkedImage(String comment,
			Task task, TaskHistory taskHistory, SubTask subTask,boolean isVerified) {

			String hqlQuery = "update SubTask set "
					+ " imageIsVerified = :imageName"
					+ " where taskId = :id";

			Query query = em.createQuery(hqlQuery)
					.setParameter("imageName", 1)
					.setParameter("id", subTask.getTaskId());
			query.executeUpdate();

		/*TODO: Required Discussion*/
//		subTask.setImageIsVerified(1);
		/*em.persist(subTask);*/

		if (isVerified) {
			task.setTaskTypeId(7);
		}
//		task.setStatus(2);
		em.merge(task);

			taskHistory.setStatus(2);
			taskHistory.setComment(comment+" | Verified ");
			em.persist(taskHistory);
	}

	@Override
	public List<SubTask> getSubTask(String taskId) {
		Query query = em.createQuery("select st from SubTask st " +
				" where st.taskId = :taskId",SubTask.class)
				.setParameter("taskId", taskId);
		return  query.getResultList();
	}

	@Transactional
	@Override
	public void benchmarkedImageRejection(BenchmarkedImageRejectionModel benchmarkedImageRejectionModel, Task task, TaskHistory taskHistory) {
		String hqlQuery = "update BenchmarkedImages set "
				+ " status = 2 where id = :id";

		Query query = em.createQuery(hqlQuery)
				.setParameter("id", benchmarkedImageRejectionModel.getBenchmarkedImageId());
		query.executeUpdate();
	
		
		task.setStatus(2);
		em.merge(task);
		
		taskHistory.setStatus(2);
		taskHistory.setComment(benchmarkedImageRejectionModel.getComment()+" | Reject");
		em.persist(taskHistory);
	}

	@Override
	public List<String> getSportIds(Integer stateId,
								   Integer regionId, Integer commodityId, boolean isStress){

		String hqlQuery = "select distinct ts.id\n" +
				"from TaskSpot ts\n" +
				"         inner join Task t on t.id=ts.taskId\n";
				/*"         inner join TaskSpotStress tss on ts.id = tss.taskSpotId\n" +
				"         inner join TaskStressSpotSymptoms tsss on tss.id = tsss.taskSpotStressId\n" +
				"         inner join TaskStressSpotSymptomImages tsssi on tsss.id = tsssi.taskSpotStressSymptomId\n" +*/
						if (isStress){
							hqlQuery = hqlQuery + "inner join TaskSpotStress tss on ts.id = tss.taskSpotId\n" +
									"         inner join TaskStressSpotSymptoms tsss on tss.id = tsss.taskSpotStressId\n" +
									"         inner join TaskStressSpotSymptomImages tsssi on tsss.id = tsssi.taskSpotStressSymptomId\n";
						} else {
							hqlQuery = hqlQuery + "inner join TaskSpotHealthImage tshi on tshi.taskSpotId = ts.id \n";
						}
			hqlQuery = hqlQuery + "inner join FarmCase fc on fc.id=t.entityId\n" +
				"         inner join FarmerFarm ff on ff.id=fc.farmId\n" +
				"         inner join Farmer f on f.id=ff.farmerId\n" +
				"         inner join Village vg on vg.id=f.villageId\n" +
				"         inner join SubTask st on st.taskId=t.id\n" +
				"         inner join State s on s.stateId=vg.stateId\n" +
				"         inner join Region r on r.regionId=vg.regionId\n" +
				"         inner join CaseCropInfo cci on cci.caseId=t.entityId\n" +
				"         inner join Variety v on v.id=cci.varietyId\n" +
				"         inner join Commodity c on c.id=v.commodityId\n" +
				"where t.taskTypeId=6 and st.imageIsVerified=0 and s.stateId=:stateId and vg.regionId=:regionId and c.id = :commodityId";

		Query query = em.createQuery(hqlQuery, String.class)
//				.setParameter("assigneeId", qaId)
				.setParameter("stateId", stateId)
				.setParameter("regionId", regionId)
				.setParameter("commodityId", commodityId);
		return  query.getResultList();
	}
}
