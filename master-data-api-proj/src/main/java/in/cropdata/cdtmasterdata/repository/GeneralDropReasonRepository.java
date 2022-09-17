package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.GeneralDropReason;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralDropReasonRepository extends JpaRepository<GeneralDropReason, Integer>
{
    Optional<GeneralDropReason> findByDropReason(String dropReason);
}
