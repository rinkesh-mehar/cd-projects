package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriHsCodeMissing;

@Repository
public interface AgriHsCodeMissingRepository extends JpaRepository<AgriHsCodeMissing, Integer> {

}
