package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoPanchayatInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoPanchayat;
import in.cropdata.cdtmasterdata.repository.GeoPanchayatRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoPanchayatService {

	@Autowired
	private GeoPanchayatRepository geoPanchayatRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoPanchayatInfDto> getAllGeoPanchayat() {
		try {
			List<GeoPanchayatInfDto> list = geoPanchayatRepository.getGeoPanchayatList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoPanchayat

	public Page<GeoPanchayatInfDto> getAllGeoPanchayatPaginated(int page, int size, String searchText) {
		try {
			searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
//			System.out.println(searchText);

			Page<GeoPanchayatInfDto> panchayatList = geoPanchayatRepository.getGeoPanchayatList(sortedByIdDesc,searchText);

			return panchayatList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param geoPanchayat verify given data are exist or not,
	 *                     if data exist then it not store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoPanchayat(GeoPanchayat geoPanchayat)
	{

		try
		{
			if (!(geoPanchayatRepository.findByPanchayatCode(geoPanchayat.getPanchayatCode()) == null))
			{
				return responseMessageUtil.sendResponse(false, "", "Geo-Village" +
						APIConstants.RESPONSE_ALREADY_EXIST + geoPanchayat.getPanchayatCode());
			}
			geoPanchayat = geoPanchayatRepository.save(geoPanchayat);

			approvalUtil.addRecord(DBConstants.TBL_GEO_PANCHAYAT, geoPanchayat.getId());

			return responseMessageUtil.sendResponse(true, "Geo-Panchayat"
					+ APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGeoPanchayat

	public ResponseMessage updateGeoPanchayatById(int id, GeoPanchayat geoPanchayat)
	{
		try
		{
			Optional<GeoPanchayat> foundPanchayat = geoPanchayatRepository.findById(id);
			if (foundPanchayat.isPresent())
			{
				if (geoPanchayatRepository.findByPanchayatCode(geoPanchayat.getPanchayatCode()) == null)
				{
					geoPanchayat.setId(id);
					geoPanchayat = geoPanchayatRepository.save(geoPanchayat);

					approvalUtil.updateRecord(DBConstants.TBL_GEO_PANCHAYAT, geoPanchayat.getId());

					return responseMessageUtil.sendResponse(true,
							"Geo-Panchayat" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "", "Geo-Village" +
							APIConstants.RESPONSE_ALREADY_EXIST + geoPanchayat.getPanchayatCode());
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Panchayat" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoPanchayatById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoPanchayat> foundPanchayat = geoPanchayatRepository.findById(id);

			if (foundPanchayat.isPresent()) {

				GeoPanchayat panchayat = foundPanchayat.get();
				panchayat.setStatus(APIConstants.STATUS_APPROVED);
				panchayat = geoPanchayatRepository.save(panchayat);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_PANCHAYAT, panchayat.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Panchayat" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Panchayat" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoPanchayat> foundPanchayat = geoPanchayatRepository.findById(id);
			if (foundPanchayat.isPresent()) {

				GeoPanchayat panchayat = foundPanchayat.get();
				panchayat.setStatus(APIConstants.STATUS_ACTIVE);
				panchayat = geoPanchayatRepository.save(panchayat);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_PANCHAYAT, panchayat.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Panchayat" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Panchayat" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoPanchayatById(int id) {
		try {
			Optional<GeoPanchayat> foundPanchayat = geoPanchayatRepository.findById(id);
			if (foundPanchayat.isPresent()) {

				GeoPanchayat panchayat = foundPanchayat.get();
				panchayat.setStatus(APIConstants.STATUS_DELETED);
				panchayat = geoPanchayatRepository.save(panchayat);

				approvalUtil.delete(DBConstants.TBL_GEO_PANCHAYAT, panchayat.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Panchayat" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Panchayat" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoPanchayatById

	public GeoPanchayat findGeoPanchayatById(int id) {
		try {
			Optional<GeoPanchayat> foundPanchayat = geoPanchayatRepository.findById(id);
			if (foundPanchayat.isPresent()) {
				return foundPanchayat.get();
			} else {
				throw new DoesNotExistException("Geo-Panchayat" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoPanchayatById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoPanchayat> foundGeoPanchayat = geoPanchayatRepository.findById(id);

			if (foundGeoPanchayat.isPresent()) {

				GeoPanchayat geoPanchayat = foundGeoPanchayat.get();
				geoPanchayat.setStatus(APIConstants.STATUS_REJECTED);
				geoPanchayat = geoPanchayatRepository.save(geoPanchayat);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_PANCHAYAT, geoPanchayat.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Panchayat " + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Panchayat " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// GeoPanchayatService
