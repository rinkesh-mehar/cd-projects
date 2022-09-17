package com.drk.tools.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.drk.tools.constants.ApplicationConstants;
import com.drk.tools.dto.ResponseMessage;
import com.drk.tools.exceptions.InvalidApiKeyException;
import com.drk.tools.model.ZipFileDTO;
import com.drk.tools.util.DrkUtil;
import com.drk.tools.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.drk.tools.constants.ErrorConstants;
import com.drk.tools.exceptions.DirectoryNotFoundException;
import com.drk.tools.exceptions.InvalidDataException;
import com.drk.tools.model.ClsoQuestions;
import com.drk.tools.model.DropDownList;
import com.drk.tools.model.OutputStatus;
import com.drk.tools.service.FlipbookService;

/**
 * Controller for handling browser requests and cron requests.
 *
 * @author PranaySK
 */

@RestController
public class FlipbookController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlipbookController.class);

	@Autowired
	private FlipbookService flipbookService;

	@Autowired
	FileManagerUtil fileManagerUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	DrkUtil drkUtils;

	@Autowired
	DiscoveryClient client;

	/**
	 * This API is used to get Drop Down data on CLSO Screen.
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@GetMapping("/get-dropdown")
	public Map<String, List<DropDownList>> getDropDownList() {
		return flipbookService.getDropDownList();
	}

	@GetMapping("/get-services")
	public void getServices(){
		List<ServiceInstance> collect = client.getServices().stream().flatMap(it -> client.getInstances(it).stream())
				.collect(Collectors.toList());

		LOGGER.info("services are -> {}" , collect);
	}

	/**
	 * This API is used to get Drop Down data on CLSO Screen with inputs as
	 * commodity, stress type, phenophase, stress, plant part and a flag to decide
	 * which data to be fetched.
	 *
	 * @param cmd     the commodity id
	 * @param strtyp  the stress type id
	 * @param phn     the phenophase id
	 * @param strs    the stress id
	 * @param plntprt the plant part id
	 * @param flg     the flag value to decide which data to be fetched
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@GetMapping("/get-stress/{cmd}/{strtyp}/{phn}/{strs}/{plntprt}/{flg}")
	public Map<String, List<DropDownList>> getStressDropdown(@PathVariable int cmd, @PathVariable int strtyp,
			@PathVariable int phn, @PathVariable int strs, @PathVariable int plntprt, @PathVariable int flg) {
		return flipbookService.getDropdownData(cmd, strtyp, phn, strs, plntprt, flg);
	}

	
	/**
	 * This API is used to get Benchmark and Generic Image data based on the input
	 * data.
	 *
	 * @param data the {@link ClsoQuestions} data
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@PostMapping("/get-image")
	public Map<String, List<?>> fetchBenchMarkImages(@RequestBody ClsoQuestions data) {
		return flipbookService.fetchBenchMarkImages(data);
	}

	/**
	 * This API is used to tag images with the symptoms.
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@PostMapping("/tag-image")
	public OutputStatus tagImages(@RequestBody Map<String, String> data) {
		return flipbookService.tagImage(data);
	}

	/**
	 * This API is used to untag the existing image from the symptom.
	 *
	 * @param qid   the symptom id
	 * @param imgID the image id
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@DeleteMapping("/un-tag-image/{qid}/{imgID}")
	public boolean untagImages(@PathVariable String qid, @PathVariable String imgID) {
		return flipbookService.untagImage(qid, imgID);
	}

	/**
	 * This API is used to remove the existing symptom using symptom id.
	 *
	 * @param qid     the symptom id
	 * @param symptom the symptom name
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@DeleteMapping("/remove-symptom/{qid}/{symptom}")
	public boolean removeSymptom(@PathVariable String qid, @PathVariable String symptom) {
		return flipbookService.removeSymptom(qid, symptom);
	}

	/**
	 * This API is used to add any new image to existing symptom.
	 *
	 * @param data the input data
	 *
	 * @return the response in Map
	 *
	 * @author PranaySK
	 */
	@PostMapping("/tag-image-to-symptom")
	public OutputStatus tagImageToSymptom(@RequestBody Map<String, String> data) {
		return flipbookService.tagImageToSymptom(data);
	}

	@PostMapping("/edit_symptom_description")
	public OutputStatus editSymptom(@RequestBody Map<String, String> data) {
		return flipbookService.editSymptom(data);
	}

	/**
	 * This API is used to tag any image as a Generic image for the symptom.
	 *
	 * @param symptomId the symptom id
	 * @param imageName the image url
	 *
	 * @return the response boolean
	 *
	 * @author PranaySK
	 */
	@GetMapping("/is-generic/{qid}")
	public boolean isGeneric(@PathVariable(name = "qid", required = true) String symptomId,
			@RequestParam(name = "imageName", required = true) String imageName) {
		LOGGER.info("symptomId -> {} and imageName -> {}", symptomId, imageName);
		return flipbookService.isGeneric(symptomId, imageName);
	}

	/**
	 * This API is used to create a ZIP file of agriculture images in
	 * <b><i>master-data</i></b> module and upload it to Azure blob. This will be
	 * called using <i>CRON-JOB Scheduler</i> on a daily basis.
	 *
	 * @param lastZipTime   the last 24 hour time in minutes
	 * @param pathKey       the path key for which the images ZIP file to be created
	 * @param directoryName the directory name for which the images ZIP file to be
	 *                      created
	 * @param fileName      the file name of requested resource to be created
	 *
	 * @return the response map
	 *
	 * @throws DirectoryNotFoundException
	 *
	 * @author PranaySK
	 */
	@GetMapping("/zip-agri-images-cron")
	public Map<String, Object> zipAgriImages(@RequestParam(name = "zipTime") Long lastZipTime,
			@RequestParam(name = "pathKey") String pathKey, @RequestParam(name = "dirName") String directoryName,
			@RequestParam(name = "fileName") String fileName) {

		if (lastZipTime == null || pathKey == null || directoryName == null || fileName == null) {
			throw new DirectoryNotFoundException(ErrorConstants.INVALID_DATA,
					"zipTime or pathKey or dirName or fileName can not be null!");
		}

		return flipbookService.zipAgriImages(lastZipTime, pathKey, directoryName, fileName);
	}

	/**
	 * This API is used to create a ZIP file of agriculture images in
	 * <b><i>master-data</i></b> module and upload it to Azure blob. This will be
	 * called using <i>CRON-JOB Scheduler</i> on a daily basis. There will be 2 ZIP
	 * files i.e., a) file containing last 24 hours updates and b) file containing
	 * all image files.
	 *
	 * @param lastZipTime the timestamp for creating ZIP files
	 *
	 * @return the response map
	 *
	 * @author PranaySK
	 *
	 * @apiNote If zipTime = 0 then all images will be zipped otherwise the images
	 *          updated in last 24 hours will be zipped.
	 */
	@GetMapping("/zip-media-cron")
	public Map<String, Object> zipMediaFiles(@RequestParam(name = "zipTime") Long lastZipTime, @RequestParam String apiKey) {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY, ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (Objects.isNull(lastZipTime)) {
			throw new InvalidDataException("zipTime can not be null or empty!");
		}

		if (drkUtils.validateApiKey(apiKey)) {
			return flipbookService.zipMediaFiles(lastZipTime);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

    /**
     * This API is used to create a ZIP file in the <b><i>Flipbook_Temp</i></b> directory.
     * The images uploaded to Azure blob and meta data is uploaded to database.
     * For uploading the images this calls the DRK's API <i>/add-benchmark-image</i>.
     * All the operation perform inside the <i>/Toolsuite/Flipbook_Temp</i> directory.
	 *
     * @param file ZIP files
	 *
     * @return the response entity
	 *
     * @author Rinkesh Mehar
	 *
     * @apiNote In zip file, all the images must be at root level and excel file must be in directory name
     * <i>excel-file</i>
     */
    @PostMapping("/upload-bm-image")
    public ResponseMessage uploadZipFile(@ModelAttribute("zipFile") ZipFileDTO file) throws IOException, ZipException {
        return flipbookService.uploadZipFile(file.getZipFile());
    }
}
