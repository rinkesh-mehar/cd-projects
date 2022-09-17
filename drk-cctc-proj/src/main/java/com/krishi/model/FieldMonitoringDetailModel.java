package com.krishi.model;

public class FieldMonitoringDetailModel {
	
	private String taskId;

	private String rightId;

	private String caseId;
	
	private CropInformationModel cropinformation;
	
	private IrrigationdetailsModel irrigationdetails;
	
	private FertilizerModel fertilizer;
	
	private SeedtreatmentModel seedtreatment;
	
	private RemedialMeasureModel remedialmeasure;

	private ContactInformation contactInformation;

	private RightInformation rightInformation;

	private CalenderModel calender;

	//private NdviImageModel ndviimage;
	
	private ScheduleModel schedule;
	
	private String type;
	
	private Integer currentYear;
	
	private Integer currentWeek;

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public RightInformation getRightInformation() {
		return rightInformation;
	}

	public void setRightInformation(RightInformation rightInformation) {
		this.rightInformation = rightInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public CropInformationModel getCropinformation() {
		return cropinformation;
	}

	public void setCropinformation(CropInformationModel cropinformation) {
		this.cropinformation = cropinformation;
	}

	public IrrigationdetailsModel getIrrigationdetails() {
		return irrigationdetails;
	}

	public void setIrrigationdetails(IrrigationdetailsModel irrigationdetails) {
		this.irrigationdetails = irrigationdetails;
	}

	public FertilizerModel getFertilizer() {
		return fertilizer;
	}

	public void setFertilizer(FertilizerModel fertilizer) {
		this.fertilizer = fertilizer;
	}

	public SeedtreatmentModel getSeedtreatment() {
		return seedtreatment;
	}

	public void setSeedtreatment(SeedtreatmentModel seedtreatment) {
		this.seedtreatment = seedtreatment;
	}

	public RemedialMeasureModel getRemedialmeasure() {
		return remedialmeasure;
	}

	public void setRemedialmeasure(RemedialMeasureModel remedialmeasure) {
		this.remedialmeasure = remedialmeasure;
	}

	public ScheduleModel getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleModel schedule) {
		this.schedule = schedule;
	}

//	public NdviImageModel getNdviimage() {
//		return ndviimage;
//	}
//
//	public void setNdviimage(NdviImageModel ndviimage) {
//		this.ndviimage = ndviimage;
//	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public Integer getCurrentWeek() {
		return currentWeek;
	}

	public void setCurrentWeek(Integer currentWeek) {
		this.currentWeek = currentWeek;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public CalenderModel getCalender() {
		return calender;
	}

	public void setCalender(CalenderModel calender) {
		this.calender = calender;
	}
}


