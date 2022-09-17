package in.cropdata.toolsuite.filemanager.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import in.cropdata.toolsuite.filemanager.model.Directory;

public interface DirectoryRepository extends MongoRepository<Directory, ObjectId> {

    @Query("{moduleName:'?0'}")
    List<Directory> findAllByModuleName(String moduleName);

    Directory findByDirPath(String dirPath);

    Directory findByPathKey(String pathKey);

    @Query("{ 'moduleName' : ?0 , 'dirPath' : ?1}")
    List<Directory> findByModuleNameAndDirPath(String moduleName, String dirPath);

//    @Query("{ 'moduleName' : ?0 , 'dirPathNew' : ?1}")
//    Directory findByModuleNameAndDirPathNew(String moduleName,String dirPathNew);
}
