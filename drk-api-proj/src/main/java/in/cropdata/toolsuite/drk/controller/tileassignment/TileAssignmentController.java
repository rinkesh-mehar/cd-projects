package in.cropdata.toolsuite.drk.controller.tileassignment;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.dto.tileassignment.SubRegionMetaDataDTO;
import in.cropdata.toolsuite.drk.exceptions.DataNotFoundException;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.tileassignment.GeoRegion;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.repository.tileassignment.GeoSubRegionMetaDataRepository;
import in.cropdata.toolsuite.drk.repository.tileassignment.RegionMasterRepository;
import in.cropdata.toolsuite.drk.service.tileassignment.TileAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class TileAssignmentController
{

    private static final Logger logger = LoggerFactory.getLogger(TileAssignmentController.class);

    @Autowired
    private TileAssignmentService tileAssignmentService;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RegionMasterRepository regionMasterRepository;

    @Autowired
    private GeoSubRegionMetaDataRepository geoSubRegionMetaDataRepository;

    @Autowired
    private AppProperties appProperties;

    /***
     * It is use for generate tile region in html format
     * @param model
     * @param regionId
     * @return String - view name
     */
//	@GetMapping("/region-map/{regionId}")
    @GetMapping("/tile-assignment/{regionId}")
    public String tileAssignmentByRegionId(Model model, @RequestParam(required = false) String apiKey, @PathVariable int regionId)
    {
        StringBuilder htmlFile = new StringBuilder();

        if (apiKey == null)
        {
            throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY, "Api Key is required to access this Resource");
        }
        if (!resourceDao.isApiKeyExists(apiKey).isEmpty())
        {
            String imageURL = "https://cdtuatts.blob.core.windows.net/cdtuatts/development/gstm/regions/images/not-found.jpg";
            Optional<GeoRegion> regionMaster = regionMasterRepository.findById(regionId);

            if (regionMaster.isPresent())
            {
                imageURL = regionMaster.get().getImageUrl();
                List<SubRegionMetaDataDTO> geoSubRegionMetaDataList = geoSubRegionMetaDataRepository.getByRegionId(regionId);

                htmlFile.append("<head>\n" +
                        "<title>Tile Assignment</title> \n" +
                        "<style> " +
                        ".no-tiles{ background-color: #555; opacity: 0.4;} \n"+
                        ".tiles{ width: 29px; height: 30px;}\n"+
                        ".lt{ border-top:2px solid #000 !important; border-left: 2px solid #471212 !important;}\n"+
                        ".rt{ border-top:2px solid #000 !important; border-right:2px solid #471212 !important;}\n"+
                        ".rb{ border-bottom:2px solid #000 !important; border-right:2px solid #471212 !important;}\n"+
                        ".lb{ border-bottom:2px solid #000 !important; border-left:2px solid #471212 !important;}\n"+
                        ".r{ border-right:2px solid #000 !important;}\n"+
                        ".l{ border-left:2px solid #000 !important;}\n"+
                        ".t{ border-top:2px solid #000 !important;}\n"+
                        ".b{ border-bottom:2px solid #000 !important;}\n"+
                        ".assigned{position:relative;}\n"+
                        ".assigned:after { content: 'O'; position: absolute; top: 70%; left: 80%; z-index: 9999999; font-size: 10px; transform: translate(-50%, -50%); color: transparent; background-color: black; border-radius: 50%;}" +
                        ".rloffice {position:relative; background-color: red !important; border-radius: 20px; color: white; font-size: 22px; text-align: center; }\n" +
                        ".rloffice:after {    content: 'RL';\n" +
                        "    position: absolute;\n" +
                        "    top: 50%;\n" +
                        "    left: 50%;\n" +
                        "    z-index: 9999999;\n" +
                        "    font-size: 12px;\n" +
                        "    transform: translate(-50%, -50%);\n" +
                        "    color: #fff;} \n" +
                        ".no-region{cursor: default !important;}\n");
                       if (regionId == 2){
                           htmlFile.append("table.l9to11 { border-collapse: collapse; background-repeat: no-repeat; background-size: 552px 541px !important;} \n");
                       } else {
                           htmlFile.append("table.l9to11 { border-collapse: collapse; background-repeat: no-repeat; background-size: 465px 481px !important;} \n");
                       }
                htmlFile.append( " table.l9to11 tr td.no-tiles { cursor: default !important; }\n" +
                        " table.l9to11 tr td { \n" +
                        " cursor: pointer;  \n" +
                        " font-size: 14px; \n" +
                        "text-align: center; \n" +
                        " font-weight: bold;\n" +
                        " font-size: 18px; \n" +
                        "color: black; } \n" +
                        "table.l9to11 tr td.tiles:hover { outline: 3px solid #F00 !important; } \n" +

                        "table.l9to11 tr td.selected { \n" +
                        "background-color: #000e6e !important; opacity:1;}  \n " +
                        ".lightgreen { background-color: #95ff0b;opacity: 0.6;}\n" +
                        ".orange { background-color: #ff9c00;opacity: 0.6;}\n" +
                        ".yellow { background-color: #f4ff00;opacity: 0.6;}\n" +
                        ".green { background-color: #4fe10f;opacity: 0.6;}\n" +
                        ".darkgreen { background-color: #36a605;opacity: 0.6;}\n" +
                        ".blue { background-color: #076FB6;opacity: 0.6;}\n" +
                        " </style> \n" +
                        "</head> \n" +
                        "<body> \n" +
                        "<div style= \"height: auto;\"> \n");

                       /** It is validate subregion-meta list of size are zero or not
                        * if size is zero then only show default image  */
                if (geoSubRegionMetaDataList.size() > 0)
                {
                    htmlFile.append("<table class='l9to11' cellspacing='0' cellpadding='0' style='background: url(")
                            .append(imageURL)
                            .append(") no-repeat;'>\n<tbody>\n");
                    int count = 0;

                    for (int i = 0; i < regionMaster.get().getMapRows(); i++)
                    {
                        htmlFile.append("<tr> \n ");

                        for (int j = 0; j < regionMaster.get().getMapColumns(); j++)
                        {
                            if (geoSubRegionMetaDataList.get(count).getIsInRegion() == 1)
                            {
                                String assignedClass = "";
                                String rlClass = geoSubRegionMetaDataList.get(count).getIsRlOffice().equals("Yes")? " rloffice": "";
                                String focusCrop = geoSubRegionMetaDataList.get(count).getFocusCrops() == null? null: geoSubRegionMetaDataList.get(count).getFocusCrops();
                                String IrrigationPer = geoSubRegionMetaDataList.get(count).getIrrigationPercent() == null? "" : geoSubRegionMetaDataList.get(count).getIrrigationPercent();
                                String avgLandHolding = geoSubRegionMetaDataList.get(count).getAvgLandHoldingSize()== null? "" : geoSubRegionMetaDataList.get(count).getAvgLandHoldingSize();
                                int ringNumber = geoSubRegionMetaDataList.get(count).getRingNumber();
                                String color =geoSubRegionMetaDataList.get(count).getColor()==null ?"": geoSubRegionMetaDataList.get(count).getColor();
                                Integer col = geoSubRegionMetaDataList.get(count).getColNo();
                                Integer row = geoSubRegionMetaDataList.get(count).getRowNo();
                                String totalAssignVillage = geoSubRegionMetaDataList.get(count).getAssignedVillageCount();
                                String villageCount = geoSubRegionMetaDataList.get(count).getVillageCount();
                                String leadCount = geoSubRegionMetaDataList.get(count).getLeadCount();
                                String bccs = geoSubRegionMetaDataList.get(count).getBcss() == null ? "" : geoSubRegionMetaDataList.get(count).getBcss();

                                String tagClasses = "tiles " + color + " " + rlClass + " " + bccs;
                                String titleText = "";

                                if (focusCrop != null)
                                {
                                    if ((!focusCrop.equals("")) || (!IrrigationPer.equals("")) || (!avgLandHolding.equals("")))
                                    {
                                        if (!focusCrop.equals(""))
                                        {
                                            titleText = titleText + "Focus Crops : " + focusCrop;
                                        }
                                        if (!IrrigationPer.equals(""))
                                        {
                                            titleText = titleText + "\nIrrigation(%) : " + IrrigationPer;
                                        }
                                        if (!avgLandHolding.equals(""))
                                        {
                                            titleText = titleText + "\nAvg Landholding Size(Ha) : " + avgLandHolding;
                                        }
                                        if (totalAssignVillage != null)
                                        {
                                            tagClasses = tagClasses + " assigned";
                                            titleText = titleText + "\nAssigned Villages : " + totalAssignVillage + "/" + villageCount;
                                        }
                                        if (leadCount != null)
                                        {
                                            titleText = titleText + "\nLeads : " + leadCount;
                                        }
                                    }
                                }

                                htmlFile.append("<td data-row='").append(row).append("'")
                                        .append(" data-col='").append(col).append("'")
                                        .append(" class ='").append(tagClasses).append("'")
                                        .append(" data-sid='").append(geoSubRegionMetaDataList.get(count).getSubRegionId()).append("'")
                                        .append(" title='").append(titleText).append("'")
                                        .append("></td>");

                            } else {
                                htmlFile.append("<td class='no-tiles' data-sid='")
                                        .append(geoSubRegionMetaDataList.get(count).getSubRegionId())
                                        .append("' no-title='Focus Crops: ")
                                        .append(geoSubRegionMetaDataList.get(count).getFocusCrops())
                                        .append("\nIrrigation(%): ")
                                        .append(geoSubRegionMetaDataList.get(count).getIrrigationPercent())
                                        .append("\nAvg Landholding Size(Ha): ")
                                        .append(geoSubRegionMetaDataList.get(count).getAvgLandHoldingSize())
                                        .append("'></td>");
                            }

                            count++;
                        }
                        htmlFile.append("</tr> \n");
                    }
                    htmlFile.append(" </tbody> \n" + "</table> \n");
                } else
                {
                    htmlFile.append("\n<img src='https://cdtuatts.blob.core.windows.net/cdtuatts/development/gstm/regions/images/not-found.jpg'\n" +
                            "style='width: 512px; height: 512px;'>\n");
                }
                htmlFile.append("</div> \n" + "</body>");
                logger.info("htmlFile-----> {}", htmlFile);
            } else
            {
                throw new DataNotFoundException("Region Not Available");
            }

            return htmlFile.toString();
        } else
        {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }
    }// tileAssignmentByRegionId

    @GetMapping("/region-mmpk/{regionId}")
    public void regionMmpkByRegionId(HttpServletResponse httpServletResponse,
                                     @RequestParam(required = false) String apiKey, @PathVariable int regionId)
    {
        if (apiKey == null)
        {
            throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
                    "Api Key is required to access this Resource");
        }
        if (!resourceDao.isApiKeyExists(apiKey).isEmpty())
        {
            Optional<GeoRegion> regionMaster = regionMasterRepository.findById(regionId);
            if (regionMaster.isPresent())
            {
//                String fileURL = regionMaster.get().getMmpkUrl();
                String fileURL = regionMaster.get().getImageUrl();
                logger.debug("Mmpk fileURL >>>>> {}", fileURL);
                httpServletResponse.setHeader("Location", fileURL);
                httpServletResponse.setStatus(302);
            } else
            {
                throw new DataNotFoundException("Region Not Available");
            }
        } else
        {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }
    }// regionMmpkByRegionId

    @GetMapping("/region-ndvi/{regionId}/{year}/{week}")
    public ResponseEntity<Resource> regionNDVIByRegionIdYearAndWeek(HttpServletResponse httpServletResponse,
                                                                    HttpServletRequest request, @RequestParam(required = false) String apiKey, @PathVariable int regionId,
                                                                    @PathVariable int year, @PathVariable int week)
    {
        if (apiKey == null)
        {
            throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
                    "Api Key is required to access this Resource");
        }
        if (!resourceDao.isApiKeyExists(apiKey).isEmpty())
        {
            if (Integer.toString(year).length() != 4)
            {
                throw new InvalidDataException("Invalid year, please provide year in 4 digits.");
            }

            if ((Integer.toString(week).length() <= 0 && Integer.toString(week).length() >= 3) || week >= 53)
            {
                throw new InvalidDataException(
                        "Invalid week, please provide year in 2 digits and must be in between 1-52.");
            }

            // Load file as Resource
            Resource resource = getResource();

            // Try to determine file's content type
            String contentType = null;
            try
            {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex)
            {
                System.out.println("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null)
            {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"ndvi-" + year + "-" + week + ".zip\"")
                    .body(resource);
        } else
        {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }
    }// regionNDVIByRegionIdYearAndWeek

    public Resource getResource()
    {
        return new ClassPathResource("ndvi.zip");
    }

}// TileAssignmentController
