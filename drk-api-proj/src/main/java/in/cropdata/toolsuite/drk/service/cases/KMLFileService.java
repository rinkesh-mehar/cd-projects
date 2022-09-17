/**
 * 
 */
package in.cropdata.toolsuite.drk.service.cases;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import in.cropdata.toolsuite.drk.model.cases.CaseDetails;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         23-Nov-2019
 */
public interface KMLFileService {

	public File targetFile(String fileExtn, String fileName);

	public Map _exctractCaseDetails(MultipartFile zipFile, List<CaseDetails> _data);

	public Optional<CaseDetails> getSpotGauidenceByCaseId(int caseID);

	public Map updateCaseDetails(List<CaseDetails> _caseList);
	
	public Map updateStatusAndReason(List<CaseDetails> _caseList);

}
