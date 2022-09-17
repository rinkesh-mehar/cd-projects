package in.cropdata.toolsuite.filemanager.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import in.cropdata.toolsuite.filemanager.model.TempDirectory;

public interface TempDirRepository extends MongoRepository<TempDirectory, ObjectId> {
    List<TempDirectory> findAllByAddedOnLessThanEqual(Date addedOn);
}
