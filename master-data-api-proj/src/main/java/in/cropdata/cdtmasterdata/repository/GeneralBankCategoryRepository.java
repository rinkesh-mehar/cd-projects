package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.GeneralBankCategory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralBankCategoryRepository extends JpaRepository<GeneralBankCategory, Integer>
{
    Optional<GeneralBankCategory> findByName(String categoryName);
}
