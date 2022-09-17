package in.cropdata.toolsuite.filemanager.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import in.cropdata.toolsuite.filemanager.model.ZipFileMetaData;

public interface ZipFileMetaDataRepository extends MongoRepository<ZipFileMetaData, ObjectId> {

//    @Query("{ 'moduleName' : ?0, 'searchQuery' : ?1, 'createdAt' : { $gte : ?2 }}")
	List<ZipFileMetaData> findByModuleNameAndSearchQueryAndCreatedAtGreaterThanOrderByCreatedAtDesc(String moduleName,
			String searchQuery, long timestamp);

	Optional<ZipFileMetaData> findByFileNameAndModuleName(String fileName, String moduleName);

	List<ZipFileMetaData> findByDirPathAndFileName(String dirPath, String fileName);

}// ZipFileMetaDataRepository
