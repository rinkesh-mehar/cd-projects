package com.krishi.step;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.krishi.entity.*;
import com.krishi.model.*;
import com.krishi.properties.AppProperties;
import com.krishi.utility.FileUtility;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
//import com.krishi.entity.Season;
import com.krishi.repository.CaseCropRepository;
import com.krishi.repository.CaseKmlRepository;
import com.krishi.repository.CaseNdviRepository;
import com.krishi.repository.CommodityRepository;
import com.krishi.repository.DistrictRepository;
import com.krishi.repository.FarmCaseRepository;
import com.krishi.repository.FarmerBankAccountRepository;
import com.krishi.repository.FarmerFarmRepository;
import com.krishi.repository.FarmerGeneralInfoRepository;
import com.krishi.repository.FarmerKycRepository;
import com.krishi.repository.FarmerRepository;
import com.krishi.repository.PanchayatRepository;
import com.krishi.repository.PhenophaseRepository;
import com.krishi.repository.RegionRepository;
import com.krishi.repository.RightsRepository;
//import com.krishi.repository.SeasonRepository;
import com.krishi.repository.SimpleNdviRepository;
import com.krishi.repository.StateRepository;
import com.krishi.repository.StaticDataRepository;
import com.krishi.repository.TaskAerialPhotosRepository;
import com.krishi.repository.TehsilRepository;
import com.krishi.repository.VarietyRepository;
import com.krishi.repository.ViewRightRepository;
import com.krishi.repository.VillageRepository;
import com.krishi.service.SystemEmailService;

public class RightsWriter implements ItemWriter<List<ViewRightsModel>> {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	private static final Logger LOGGER = LogManager.getLogger(RightsWriter.class);
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Autowired
	ViewRightRepository viewRightRepository;
	
	@Autowired
	PhenophaseRepository phenophaseRepository;
	
	@Autowired
	RightsRepository rightsRepository;
	
	@Autowired
	StaticDataRepository staticDataRepository;
	
	@Autowired
	TaskAerialPhotosRepository taskAerialPhotosRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RightsRepository rightsRepo;
	
	@Autowired
	CaseNdviRepository caseNdviRepo;
	
	@Autowired
	private SystemEmailService systemEmailService;
	
	@Autowired
	private SimpleNdviRepository simpleNdviRepository;
	
	@Autowired
	private FarmCaseRepository farmCaseRepository;
	
	@Autowired
	private CaseCropRepository caseCropRepository;
	
	@Autowired
	private VarietyRepository varietyRepository;
	
	@Autowired
	private CommodityRepository commodityRepository;
	
//	@Autowired
//	private SeasonRepository seasonRepository;
	
	@Autowired
	private FarmerFarmRepository farmerFarmRepository;
	
	@Autowired
	private FarmerRepository farmerRepository;
	
	@Autowired
	private FarmerKycRepository farmerKycRepository;
	
	@Autowired
	private CaseKmlRepository caseKmlRepository;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private PanchayatRepository panchayatRepository;
	
	@Autowired
	private TehsilRepository tehsilRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private FarmerGeneralInfoRepository farmerGeneralInfoRepository;
	
	@Autowired
	private FarmerBankAccountRepository farmerBankAccountRepository;

	@Autowired
	private FileUtility fileUtility;

	@Autowired
	private AppProperties appProperties;

	@Override
	public void write(List<? extends List<ViewRightsModel>> items) throws Exception {
		StaticData userName=staticDataRepository.findByKey("rightusername");
		StaticData password=staticDataRepository.findByKey("rightspassword");
		CropdataSecurity cropdataSecurity = new CropdataSecurity();
		cropdataSecurity.setUserName(userName.getValue());
		cropdataSecurity.setPassword(password.getValue());
		cropdataSecurity.setSecurityMode(1);
		getNdviFromGstm(items.get(0));
		
		List<String> failedRightId = new ArrayList<>();
 		for (ViewRightsModel rights : items.get(0)) {
			Rights rightToSave = new Rights();

			System.out.println("Onboarding Right is -> " + rights.getRightId());

			Rights fetchedRightFromRightId = rightsRepository.findTop1ByIdOrderByVersionNumberDesc(rights.getRightId());

			RightAdditionalDetails viewRight = getAdditionalRightDetails(rights.getCaseId(), fetchedRightFromRightId);
//			ViewRight viewRight = viewRightRepository.findById(rights.getCaseId()).get();
			Variety variety = varietyRepository.getUomByIdAndCommodityId(viewRight.getVarietyId(),
					viewRight.getCommodityId());
			List<CaseNdvi> caseNdvis = caseNdviRepo.findByCaseId(rights.getCaseId());
			List<SimpleNdvi> simpleNdvis = simpleNdviRepository.findByCaseId(rights.getCaseId());
			List<String> ndviImages = new ArrayList<>();
			List<String> currentNdvi = new ArrayList<>();
			List<String> benchmarkNdvi = new ArrayList<>();
			if (caseNdvis.size() > 0) {
				ndviImages = caseNdvis.stream().map(CaseNdvi::getName).collect(Collectors.toList());
				benchmarkNdvi = caseNdvis.stream().map(o -> o.getExpectedYield() + "").collect(Collectors.toList());
			}
			if (simpleNdvis.size() > 0) {
				currentNdvi = simpleNdvis.stream().map(o -> o.getNdvi() + "").collect(Collectors.toList());
			}
			Pageable page = PageRequest.of(0, 1);
			List<TaskAerialPhotos> photo = taskAerialPhotosRepository.findByCaseId(rights.getCaseId(), page);

			if (isValidData(rights, viewRight, variety, photo, ndviImages)) {

				BlockChainRightsModel blockChainRightsModel = new BlockChainRightsModel();
				if (photo.size() > 0) {
					blockChainRightsModel.setAerialPhotos(Arrays.asList(photo.get(0).getPhotos().split(",")).stream()
							.map(String::trim).collect(Collectors.toList()));
				}
//			blockChainRightsModel.setAerialPhotos(Arrays.asList("areial.jpg"));
				blockChainRightsModel.setAllowableVarianceInqtyNeg(rights.getAllowableVarianceInqtyNeg());
				blockChainRightsModel.setAllowableVarianceInqtyPos(rights.getAllowableVarianceInqtyPos());
				blockChainRightsModel.setAllowableVarianceInqtyNegPer(rights.getAllowableVarianceInqtyNegPer());
				blockChainRightsModel.setAllowableVarianceInqtyPosPer(rights.getAllowableVarianceInqtyPosPer());
				blockChainRightsModel.setCaseId(new BigInteger(rights.getCaseId()));
				blockChainRightsModel.setDelDateTime(
						rights.getDelDateTime() == null ? "" : rights.getDelDateTime().toString());
				blockChainRightsModel.setCurrentQty(rights.getCurrentQty());
				blockChainRightsModel.setEstimatedQty(rights.getEstimatedQty());
				blockChainRightsModel.setStandardQty(rights.getStandardQty());
				blockChainRightsModel.setRiskReport(rights.getRiskReport() == null ? List.of()
						: Arrays.asList(rights.getRiskReport().split(",")));
				blockChainRightsModel.setFarmerDefault(rights.getFarmerDefault());
				blockChainRightsModel.setGeoAdjacent(rights.getGeoAdjacent());
				blockChainRightsModel.setLogisticHubAddress(rights.getLogisticHubAddress());
				blockChainRightsModel.setLogisticHubId(rights.getLogisticHubId());
				blockChainRightsModel.setMbep(rights.getMbep());
				blockChainRightsModel.setRecordBy(rights.getRecordBy().longValue());
				blockChainRightsModel.setRecordDate(sdf.format(new Date(rights.getRecordDate().getTime())).toString());
				blockChainRightsModel.setRightId(rights.getRightId());
				blockChainRightsModel.setRightsVersion(rights.getRightsVersion().longValue());
				blockChainRightsModel.setRightCertificate(rights.getRightCertificate() == null ? List.of()
						: Arrays.asList(rights.getRightCertificate().split(",")));
				blockChainRightsModel.setSplitField(rights.getSplitField());
				blockChainRightsModel.setGradeCurrQty(rights.getCurrentQly());
				blockChainRightsModel.setGradeEstQty(rights.getEstimatedQly());
				blockChainRightsModel.setAllowableVarianceInqtyGrade(rights.getAllowableVarianceInqtyGrade());
				blockChainRightsModel.setStage(rights.getStage());
				decimalFormat.setRoundingMode(RoundingMode.UP);
				blockChainRightsModel.setDueMoneyByFarmer(rights.getDueMoneyByFarmer());
				blockChainRightsModel.setRightValue(rights.getMbep() * rights.getCurrentQty());

				// default data entered
				blockChainRightsModel.setMep((long) 0);
				blockChainRightsModel.setCropType(rights.getCropType());

				// to be calculated
				blockChainRightsModel.setFarmerRating((long) 5);
				blockChainRightsModel.setNdviExpYield(0);
				blockChainRightsModel.setNdviFieldStatus("NA");
				blockChainRightsModel.setNdviImages(ndviImages);
				blockChainRightsModel.setSubRegionName(String.valueOf(viewRight.getSubregionId()));
				blockChainRightsModel.setSeasonId(2);
				blockChainRightsModel.setVarietyId(viewRight.getVarietyId().longValue());
				blockChainRightsModel.setSowingWeek(viewRight.getSowingWeek().longValue());
				blockChainRightsModel.setHarvestWeek(viewRight.getHarvestWeek().longValue());
				blockChainRightsModel.setCroppingArea(viewRight.getCropArea());
				blockChainRightsModel.setVariety(viewRight.getVariety_name());
				blockChainRightsModel.setCommodityId(viewRight.getCommodityId().longValue());
				blockChainRightsModel.setHSCode(viewRight.getHscode());
				blockChainRightsModel.setCommodity(viewRight.getCommodityName());
				blockChainRightsModel.setSeason("Rabi");
				blockChainRightsModel.setFarmerId(new BigInteger(viewRight.getFarmerId()));
				blockChainRightsModel.setVillageId(viewRight.getVillageId().longValue());
				blockChainRightsModel.setVillageName(viewRight.getVillageName());
				blockChainRightsModel.setSubRegionId(viewRight.getSubregionId());
				blockChainRightsModel.setRegionId(viewRight.getRegionId().longValue());
				blockChainRightsModel.setRegionName(viewRight.getRegionName());
				blockChainRightsModel.setStateId(viewRight.getStateCode().longValue());
				blockChainRightsModel.setStateName(viewRight.getStateName());
				blockChainRightsModel.setDistrictId(viewRight.getDistrictId().longValue());
				blockChainRightsModel.setDistrictName(viewRight.getDistrictName());
				blockChainRightsModel.setFarmerEmail(
						viewRight.getFarmerEmail() == null ? "" : getSHA256Hash(viewRight.getFarmerEmail()));
				blockChainRightsModel.setFarmerAccount(getSHA256Hash(viewRight.getFarmerAccountNumber()));
				blockChainRightsModel.setIntRestrictions(viewRight.getInternationalRestrictions());
				blockChainRightsModel.setDOMRestrictions(viewRight.getDomesticRestrictions());

				blockChainRightsModel.setUom(variety.getUom());

				blockChainRightsModel.setBenchmark_ndvi(benchmarkNdvi != null ? benchmarkNdvi : List.of());
				blockChainRightsModel.setCurrent_ndvi(currentNdvi != null ? currentNdvi : List.of());

//				List<Rights> rightsData = rightsRepo.findByRightIdVersionNumber(rights.getRightId(),
//						rights.getRightsVersion());
//				if (rightsData != null && rightsData.size() > 0) {

					// right certificate
					Document document = new Document();
					String basePath = appProperties.getRightCertificateBasePath();
					String rightTemplate = getRightTemplate();
					String rightTemplateWithData = replacePlaceholder(rightTemplate, rights, viewRight);
					String certificateName = rights.getRightId() + "_" + rights.getRightsVersion() + ".pdf";

					FileUtility.checkDirExists(basePath);
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(basePath + "/" + certificateName));

					document.open();
					HTMLWorker htmlWorker = new HTMLWorker(document);
					htmlWorker.parse(new StringReader(rightTemplateWithData));

					// Disclaimer
					// document.newPage();
					// htmlWorker.parse(new StringReader(getDisclaimer()));
					document.close();
					writer.close();

					String uploadFileUrl = fileUtility.uploadFileToBlob(certificateName, new File(basePath + "/" + certificateName), "right-certificate");
					fileUtility.checkAndDeleteExistingFile(new File(basePath + "/" + certificateName));
					LOGGER.info("blob response {}", uploadFileUrl);

					BeanUtils.copyProperties(rights, rightToSave);
					rightToSave.setId(rights.getRightId());
					rightToSave.setVersionNumber(rights.getRightsVersion());
					rightToSave.setBlockchainStatus("Sent");
					rightToSave.setCurrentQuantity(rights.getCurrentQty());
					rightToSave.setEstimatedQuantity(rights.getEstimatedQty());
					rightToSave.setStandardQuantity(rights.getStandardQty());
					rightToSave.setAllowableVarianceQtyPos(rights.getAllowableVarianceInqtyPos());
					rightToSave.setAllowableVarianceQtyNeg(rights.getAllowableVarianceInqtyNeg());
					rightToSave.setAllowableVarianceQtyPosPer(rights.getAllowableVarianceInqtyPosPer());
					rightToSave.setAllowableVarianceQtyNegPer(rights.getAllowableVarianceInqtyNegPer());
					rightToSave.setEstimatedQuality(rights.getEstimatedQly());
					rightToSave.setCurrentQuality(rights.getCurrentQly());
					rightToSave.setAllowableVarianceQuality(rights.getAllowableVarianceInqtyGrade());
					rightToSave.setDomesticRestriction(rights.getDomrestrictions());
					rightToSave.setInternationalRestriction(rights.getInternationalRestriction());
					rightToSave.setDeliveryDateTime(rights.getDelDateTime());
					rightToSave.setGeographicallyAdjustent(rights.getGeoAdjacent());
					rightToSave.setRiskReport(rights.getRiskReport());
					rightToSave.setRecordCreatedBy(rights.getRecordBy());
					rightToSave.setRecordDateTime(rights.getRecordDate());
					rightToSave.setStatusReceivingDate(rights.getStatusReceivingDate());
//					rightToSave.setComments(rights.getComments());
					rightToSave.setLotId(rights.getLotId());
					rightToSave.setIsVerified(rights.getIsVerified());
					rightToSave.setTransactionId(rights.getTransactionId());
					rightToSave.setDueAmount(rights.getDueMoneyByFarmer());
					rightToSave.setAmountCollected(rights.getAmountCollected());

					rightToSave.setRightCertificate(uploadFileUrl);
//					rightsRepository.save(rightToSave);
					blockChainRightsModel.setRightCertificate(Arrays.asList(certificateName));
//				}

				RightRequestModel rightRequestModel = new RightRequestModel();
				rightRequestModel.setSecurity(cropdataSecurity);
				rightRequestModel.setData(blockChainRightsModel);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				ObjectMapper Obj = new ObjectMapper();
				HttpEntity<RightRequestModel> request = new HttpEntity<>(rightRequestModel, headers);
				// Obj.writeValueAsString(rightRequestModel);
				/*
				 * String retSrc = (request); // parsing JSON JSONObject result = new
				 * JSONObject(retSrc);
				 */
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(rightRequestModel);
				System.out.println("Request data :------> " + json);
				String rightUrl = staticDataRepository.findByKey("rightUrl").getValue();
				String response = restTemplate.postForObject(rightUrl, request, String.class);


				System.out.println("Right Sent Response From AGD :------> " + response.toString());

				LOGGER.info("Response : " + response.toString());
				JSONParser jsonParser = new JSONParser(response);

				LinkedHashMap res = (LinkedHashMap) jsonParser.parse();
				String status = res.get("status").toString();
				if (status.equals("SUCCESS")) {
//					rightToSave.setStatus(status);
					LinkedHashMap data = (LinkedHashMap) res.get("data");
					rightToSave.setTransactionId(data.get("txnID").toString());
				} else {
					rightToSave.setStatus(status);
				}
				LOGGER.info("Response status : " + status);
				rightsRepository.save(rightToSave);

			} else {
				failedRightId.add(rightToSave.getId());
				rightToSave.setStatus("INVALID_DATA");
				rightsRepo.saveAndFlush(rightToSave);
			}
		}
		
		systemEmailService.sendRightDataStatusMail(failedRightId);
	}

	/*Validate rights data*/
	private boolean isValidData(ViewRightsModel right, RightAdditionalDetails viewRight, Variety variety, List<TaskAerialPhotos> photo,
			List<String> caseNdvis) {

		if (
				   StringUtils.isEmpty(viewRight.getStateName()) || viewRight.getStateCode() == null
				|| StringUtils.isEmpty(viewRight.getDistrictName()) || viewRight.getDistrictId() == null
				|| StringUtils.isEmpty(viewRight.getVillageName()) || viewRight.getVillageId() == null
				|| StringUtils.isEmpty(viewRight.getHscode()) || StringUtils.isEmpty(right.getRightId())
				|| StringUtils.isEmpty(right.getCaseId()) || StringUtils.isEmpty(viewRight.getCommodityName())
				|| viewRight.getCommodityId() == null || StringUtils.isEmpty(viewRight.getVariety_name())
				|| viewRight.getVarietyId() == null || StringUtils.isEmpty(viewRight.getFarmerId())
				|| StringUtils.isEmpty(viewRight.getFarmerAccountNumber())
				|| (variety == null || StringUtils.isEmpty(variety.getUom())) || viewRight.getCropArea() == null
				|| right.getCurrentQty() == null || right.getEstimatedQty() == null
				|| right.getStandardQty() == null || right.getAllowableVarianceInqtyPos() == null
				|| right.getAllowableVarianceInqtyNeg() == null || right.getAllowableVarianceInqtyPosPer() == null
				|| right.getAllowableVarianceInqtyNegPer() == null || StringUtils.isEmpty(right.getEstimatedQly())
				|| StringUtils.isEmpty(right.getCurrentQty().toString())
				|| StringUtils.isEmpty(right.getAllowableVarianceInqtyGrade()) || StringUtils.isEmpty(right.getStage())
				|| viewRight.getSowingWeek() == null || viewRight.getHarvestWeek() == null
				|| viewRight.getRegionId() == null || StringUtils.isEmpty(viewRight.getRegionName())
				|| viewRight.getSubregionId() == null || right.getMbep() == null
				|| photo == null
				|| photo.size() == 0 || caseNdvis == null || right.getRecordBy() == null
				|| right.getRecordDate() == null || StringUtils.isEmpty(viewRight.getInternationalRestrictions())
				|| StringUtils.isEmpty(viewRight.getDomesticRestrictions())) {
			return false;
		}

		return true;
	}

	private static final class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (httpMethod == HttpMethod.GET) {
                return new HttpGetRequestWithEntity(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
        public HttpGetRequestWithEntity(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
	/*Fetch benchmark ndvi image info and insert into caseNdvi table*/
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	void getNdviFromGstm(List<ViewRightsModel> list) throws JsonProcessingException {
		StaticData baseUrlData = staticDataRepository.findByKey("gstmBasePath");
		StaticData apiKeyData = staticDataRepository.findByKey("masterDataApikey");
		List<String> caseIds = list.stream().map(r -> r.getCaseId()).collect(Collectors.toList());
		Calendar cal = Calendar.getInstance();
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("week", cal.get(Calendar.WEEK_OF_YEAR));
		requestMap.put("year", cal.get(Calendar.YEAR));
		requestMap.put("caseId", caseIds);
		ObjectMapper mapper = new ObjectMapper();
		String requestData = mapper.writeValueAsString(requestMap);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> request = new HttpEntity<>(requestData, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrlData.getValue() + "/benchmark-ndvi")
				.queryParam("apiKey", apiKeyData.getValue());
		try {
			RestTemplate template = new RestTemplate();
			template.setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());
			ResponseEntity<GstmNdviJsonData> response = template.exchange(builder.toUriString(),
					HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
			if(response.getBody().getData().size() > 0) {
				List<CaseNdvi> ndviList = response.getBody().getData().stream().map(GstmNdviResponseModel::getCaseNdvi).collect(Collectors.toList());
				for(CaseNdvi ndvi:ndviList) {
					List<CaseNdvi> existData = caseNdviRepo.findByCaseIdAndWeekAndYear(ndvi.getCaseId(), ndvi.getWeek(), ndvi.getYear());
					if(existData.size() == 0) {
						caseNdviRepo.saveAndFlush(ndvi);
					}
				}
			}
		} catch(HttpStatusCodeException  e) {
			System.out.println(e.getResponseBodyAsString());
		}
		
	}
	
    /*Encryption*/
	public String getSHA256Hash(String plainText) {
		MessageDigest mdSHA256 = null;
		if (plainText.length() > 0) {
			try {
				mdSHA256 = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			byte[] hashInBytes = null;
			// bytes to hex
			StringBuilder sb = new StringBuilder();
			try {
				hashInBytes = mdSHA256.digest(plainText.getBytes(StandardCharsets.UTF_8));
				for (byte b : hashInBytes) {
					sb.append(String.format("%02x", b));
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return sb.toString();
		} else {
			return "NA";
		}

	}
	
	private String replacePlaceholder(String rightTemplate, ViewRightsModel rightDetails, RightAdditionalDetails viewRight) {
		return java.text.MessageFormat.format(rightTemplate, rightDetails.getRightId(), viewRight.getStateName(),
				viewRight.getDistrictName(), viewRight.getVillageName(), viewRight.getHscode(),
				viewRight.getCommodityName(), rightDetails.getCaseId(), viewRight.getVariety_name(),
				viewRight.getSowingWeek(), viewRight.getHarvestWeek(), rightDetails.getCurrentQly(),
				rightDetails.getAllowableVarianceInqtyGrade(), "Metric Tons (MT)", rightDetails.getAllowableVarianceInqtyPos(),
				rightDetails.getAllowableVarianceInqtyNeg(), rightDetails.getCurrentQty(), rightDetails.getMbep(),
				"NA", rightDetails.getMbep() *  rightDetails.getCurrentQty() , rightDetails.getRecordDate());
	}
	
	private String getDisclaimer() {
		String disclaimer = "<html><body> "
				+ " <div color=\"#00B04F\" align=\"center\"><b>DISCLAIMER</b></div> "
				+ " <p  align=\"justify\">Crop Data Technology a private limited company and is working only as an facilitator for farmer and buyer however the company does not give the warranty for getting the higher price to the farmer as this will depend entirely on the current market value, quality of the produce and purchasing capacity of the buyer. However, if the quality parameters as mentioned in the right document are not met as per specifications given by the company to the farmer Crop data Technology reserves the right to reject the same produce and the agreement shall be treated as cancelled. Crop Data Technology Pvt ltd is not responsible for any short fall in the yield occurring as a result of major pest attack or unseasonal rains during the harvesting, flooding and other natural calamities. In case of sudden fall in market price, company is not responsible for the same.</p>"
				+ " </body></html>";
		
		return disclaimer;
	}
	
	private String getRightTemplate() {
		String template = "<html><body>"
				+ " <h3 align=center><u><b>Farmer (Right) Document</b></u></h3>"
				+ " <table border=1 >"
				+ " <tr> "
				+ "		<td  bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Right ID </td> <td bgcolor=\"#F2F2F2\" >{0}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> State </td> <td bgcolor=\"#F2F2F2\" >{1}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> District </td> <td bgcolor=\"#F2F2F2\">{2}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Village </td> <td bgcolor=\"#F2F2F2\">{3}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> HSN Code </td> <td bgcolor=\"#F2F2F2\">{4}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Commodity </td> <td bgcolor=\"#F2F2F2\">{5}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Case ID </td> <td bgcolor=\"#F2F2F2\">{6}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Variety </td> <td bgcolor=\"#F2F2F2\">{7}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Sowing Week </td> <td bgcolor=\"#F2F2F2\">{8}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Harvest Week </td> <td bgcolor=\"#F2F2F2\">{9}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Current Quality (Grade) </td> <td bgcolor=\"#F2F2F2\">{10}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Allowable Variance in Quality (Grade) </td> <td bgcolor=\"#F2F2F2\">{11}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Right Unit </td> <td bgcolor=\"#F2F2F2\">{12}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Allowable Variance in Quantity (+ve) </td> <td bgcolor=\"#F2F2F2\">{13}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Allowable Variance in Quantity (-ve) </td> <td bgcolor=\"#F2F2F2\">{14}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Current Quantity </td> <td bgcolor=\"#F2F2F2\">{15}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> MBEP </td> <td bgcolor=\"#F2F2F2\">{16}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Packaging Size </td> <td bgcolor=\"#F2F2F2\">{17}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Right Value </td> <td bgcolor=\"#F2F2F2\">{18}</td>"
				+ " </tr> "
				+ " <tr> "
				+ "		<td bgcolor=\"#C7B461\" style=\"color:#ffffff\"> Right created on </td> <td bgcolor=\"#F2F2F2\">{19}</td>"
				+ " </tr> "
				+ " </table> "
				+ " </body></html>";
		
		return template;
		 
	}
	
	private RightAdditionalDetails getAdditionalRightDetails(String caseId, Rights right) {
		RightAdditionalDetails rightAdditionalDetails = new RightAdditionalDetails();
		
		FarmCase farmCase = farmCaseRepository.getBycaseID(caseId);
		CaseCropInfo caseCropInfo = null;
		FarmerFarm farmerFarm = null;
		CaseKml caseKml = null;
		if(farmCase != null) {
			rightAdditionalDetails.setCaseId(farmCase.getId());
			caseCropInfo = caseCropRepository.findByCaseId(farmCase.getId());
			
			farmerFarm = farmerFarmRepository.findbyfarmId(farmCase.getFarmId());
			caseKml = caseKmlRepository.findByFarmCaseId(farmCase.getId());
		}
		
		Optional<Variety> variety = null;
//		Optional<Season> season = null;
		if(caseCropInfo != null) {
//			rightAdditionalDetails.setSeasonId(caseCropInfo.getSeasonId());
//			rightAdditionalDetails.setVarietyId(caseCropInfo.getVarietyId());
			rightAdditionalDetails.setSowingWeek(caseCropInfo.getFarmerGivenSowingWeek());
			rightAdditionalDetails.setHarvestWeek(caseCropInfo.getHarvestWeek());
			rightAdditionalDetails.setCropArea(Double.valueOf(String.valueOf(caseCropInfo.getCropArea())));
			variety = varietyRepository.findById(caseCropInfo.getVarietyId());
			
//			season = seasonRepository.findById(caseCropInfo.getSeasonId());
		}

		Optional<Variety> fetchedVariety = varietyRepository.findById(right.getVarietyId());

		if (fetchedVariety.isPresent()){
			rightAdditionalDetails.setVariety_name(fetchedVariety.get().getName());
			rightAdditionalDetails.setVarietyId(fetchedVariety.get().getId());
		}
		
		Optional<Commodity> commodity = null;
		if(variety != null && variety.isPresent()) {
//			rightAdditionalDetails.setVariety_name(variety.get().getName());
			rightAdditionalDetails.setCommodityId(variety.get().getCommodityId());
			rightAdditionalDetails.setHscode(variety.get().getHscode());
			rightAdditionalDetails.setDomesticRestrictions(variety.get().getDomesticRestrictions());
			rightAdditionalDetails.setInternationalRestrictions(variety.get().getInternationalRestrictions());
			commodity = commodityRepository.findById(variety.get().getCommodityId());
		}
		
		if(commodity != null && commodity.isPresent()) {
			rightAdditionalDetails.setCommodityName(commodity.get().getName());
		}
		
//		if(season != null && season.isPresent()) {
//			rightAdditionalDetails.setSeason_name(season.get().getName());
//		}
		
		Farmer farmer = null;
		if(farmerFarm != null) {
			farmer = farmerRepository.findByfarmerId(farmerFarm.getFarmerId());
		}
		
		List<FarmerKyc> farmerKyc = null;
		Optional<Village> village = null;
		FarmerGeneralInfo farmerGeneralInfo = null;
		FarmerBankAccount farmerBankAccount = null;
		if(farmer != null) {
			rightAdditionalDetails.setFarmerId(farmer.getId());
			rightAdditionalDetails.setFarmerDueAmount(farmer.getDueAmount().doubleValue());
			rightAdditionalDetails.setVillageId(farmer.getVillageId());
			farmerKyc = farmerKycRepository.findByFarmerId(farmer.getId());
			village = villageRepository.findById(farmer.getVillageId());
			farmerGeneralInfo = farmerGeneralInfoRepository.findByFarmerId(farmer.getId());
			/** replace farmerId to caseId - As per discussion in 21/08/2021*/
			/*farmerBankAccount = farmerBankAccountRepository.findByFarmerId(farmer.getId());*/
			farmerBankAccount = farmerBankAccountRepository.findByCaseId(caseId);
		}
		
		if(farmerKyc != null && farmerKyc.size() > 0) {
			rightAdditionalDetails.setIsKycVerified(farmerKyc.get(0).getIsVerified());
		}
		
		if(caseKml != null) {
			rightAdditionalDetails.setIsKmlVerified(caseKml.getIsVerified() == 0 ? false : true);
		}
		
		Optional<Region> region = null;
		Optional<Panchayat> panchayat = null;
		if(village != null) {
			rightAdditionalDetails.setVillageName(village.get().getName());
			rightAdditionalDetails.setSubregionId(Long.valueOf(village.get().getSubregionId()));
			rightAdditionalDetails.setRegionId(village.get().getRegionId());
			region = regionRepository.findById(village.get().getRegionId());
			panchayat = panchayatRepository.findById(village.get().getPanchayatId());
		}
		
		Optional<StateEntity> stateEntity = null;
		if(region != null && region.isPresent()) {
			rightAdditionalDetails.setRegionName(region.get().getRegionName());
			stateEntity = stateRepository.findById(region.get().getStateId());
		}
		
		Optional<Tehsil> tehsil = null;
		if(panchayat != null && panchayat.isPresent()) {
			tehsil = tehsilRepository.findById(panchayat.get().getTehsilId());
		}
		
		Optional<District> district = null;
		if(tehsil != null && tehsil.isPresent()) {
			rightAdditionalDetails.setDistrictId(tehsil.get().getDistrictId());
			district = districtRepository.findById(tehsil.get().getDistrictId());
		}
		
		if(district != null && district.isPresent()) {
			rightAdditionalDetails.setDistrictName(district.get().getName());
		}
		
		if(stateEntity != null && stateEntity.isPresent()) {
			rightAdditionalDetails.setStateCode(stateEntity.get().getStateCode());
			rightAdditionalDetails.setStateName(stateEntity.get().getStateName());
		}
		
		if(farmerGeneralInfo != null) {
			rightAdditionalDetails.setFarmerEmail(farmerGeneralInfo.getEmail());
		}
		
		if(farmerBankAccount != null) {
			rightAdditionalDetails.setFarmerAccountNumber(farmerBankAccount.getAccNumber());
			rightAdditionalDetails.setIsPennydropped(farmerBankAccount.getIsPennydropped());
		}
		return rightAdditionalDetails;
	}
}
