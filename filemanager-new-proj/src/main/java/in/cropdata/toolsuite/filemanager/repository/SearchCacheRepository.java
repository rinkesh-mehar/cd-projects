package in.cropdata.toolsuite.filemanager.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import in.cropdata.toolsuite.filemanager.model.SearchCache;

public interface SearchCacheRepository extends MongoRepository<SearchCache, ObjectId> {

//    @Query("{ 'moduleName' : ?0, 'searchQuery' : ?1, 'createdAt' : { $gte : ?2 }}")
    List<SearchCache> findByModuleNameAndSearchQueryAndCreatedAtGreaterThanOrderByCreatedAtDesc(String moduleName,String searchQuery, long oldTime);
    
}//SearchCacheRepository
