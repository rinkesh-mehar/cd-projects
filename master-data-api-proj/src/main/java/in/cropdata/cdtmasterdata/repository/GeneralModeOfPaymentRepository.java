package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.GeneralModeOfPayment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralModeOfPaymentRepository extends JpaRepository<GeneralModeOfPayment, Integer>
{
    Optional<GeneralModeOfPayment> findByModeOfPayment(String modeOfPayment);
}

