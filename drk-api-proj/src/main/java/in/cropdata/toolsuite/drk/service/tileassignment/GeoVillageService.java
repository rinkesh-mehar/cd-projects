package in.cropdata.toolsuite.drk.service.tileassignment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dto.tileassignment.VillageDTOInf;
import in.cropdata.toolsuite.drk.repository.tileassignment.GeoVillageRepository;

@Service
public class GeoVillageService {

    @Autowired
    private GeoVillageRepository geoVillageRepository;

    public List<VillageDTOInf> getAllGeoVillage(String subRegionId) {
	List<VillageDTOInf> villageList = geoVillageRepository.findVillageCodeAndName(subRegionId);
//	List<VillageDTO> villageDTOList = new ArrayList<>();
//	for (Object[] ob : villageList){
//            int key = (int)ob[0];
//            String value = (String)ob[1];
//           
//            VillageDTO villageDTO = new VillageDTO();
//            villageDTO.setVillageCode(key);
//            villageDTO.setVillageName(value);
//            villageDTOList.add(villageDTO);
//	}
	return villageList;
    }// getAllGeoVillage

}// GeoVillageService
