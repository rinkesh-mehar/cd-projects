package in.cropdata.toolsuite.filemanager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import in.cropdata.toolsuite.filemanager.model.SessionKey;

public interface SessionKeyRepository extends MongoRepository<SessionKey, ObjectId> {

}
