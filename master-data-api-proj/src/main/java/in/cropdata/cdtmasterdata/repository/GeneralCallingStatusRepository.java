package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.GeneralCallingStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralCallingStatusRepository extends JpaRepository<GeneralCallingStatus, Integer>
{
    Optional<GeneralCallingStatus> findByCallingStatus(String callingStatus);
}
