package in.cropdata.toolsuite.filemanager.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import in.cropdata.toolsuite.filemanager.model.FileMetadata;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, ObjectId> {

	@Query("{ 'dirPath' : ?0 , 'fileOrigionalName' : ?1}")
	List<FileMetadata> findByDirPathAndFileOrigionalName(String dirPath, String fileOrigionalName);

	List<FileMetadata> findByModuleNameAndFileExtension(String moduleName,String fileExtension);

//    @Query("searchQuery = ?0")
	List<Searchdata> findAllBy();
}
