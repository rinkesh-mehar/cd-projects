package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankBranchInf;
import in.cropdata.cdtmasterdata.model.GeneralBankBranch;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralBankBranchRepository extends JpaRepository<GeneralBankBranch, Integer>
{


    @Query(value = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank, GBB.IsValid, GBB.ErrorMessage FROM general_bank_branch GBB\n" +
            "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
            "	where GB.Name like :searchText\n" +
            "	OR GBB.Name like :searchText\n" +
            "	OR GBB.IFSCCode like :searchText",
            countQuery = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank, GBB.IsValid, GBB.ErrorMessage FROM general_bank_branch GBB\n" +
                    "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
                    "	where GB.Name like :searchText\n" +
                    "	OR GBB.Name like :searchText\n" +
                    "	OR GBB.IFSCCode like :searchText", nativeQuery = true)
    Page<GeneralBankBranchInf> findAllWithSearch(Pageable sortedByIdDesc, String searchText);
    
    @Query(value = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank FROM general_bank_branch_missing GBB\n" +
            "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
            "	where GB.Name like :searchText\n" +
            "	OR GBB.Name like :searchText\n" +
            "	OR GBB.IFSCCode like :searchText",
            countQuery = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank FROM general_bank_branch_missing GBB\n" +
                    "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
                    "	where GB.Name like :searchText\n" +
                    "	OR GBB.Name like :searchText\n" +
                    "	OR GBB.IFSCCode like :searchText", nativeQuery = true)
    Page<GeneralBankBranchInf> findAllWithMissingSearch(Pageable sortedByIdDesc, String searchText);
       

    @Query(value = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank, GBB.IsValid, GBB.ErrorMessage FROM general_bank_branch GBB\n" +
            "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
            "		 where GBB.IsValid = 0 and (GB.Name like :searchText\n" +
            "	OR GBB.Name like :searchText\n" +
            "	OR GBB.IFSCCode like :searchText)",
            countQuery = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank, GBB.IsValid, GBB.ErrorMessage FROM general_bank_branch GBB\n" +
                    "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
                    "	 where GBB.IsValid = 0 and ( GB.Name like :searchText\n" +
                    "	OR GBB.Name like :searchText\n" +
                    "	OR GBB.IFSCCode like :searchText)", nativeQuery = true)
    Page<GeneralBankBranchInf> findAllWithSearchInvalidated(Pageable sortedByIdDesc, String searchText);

   
    @Query(value = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank FROM general_bank_branch_missing GBB\n" +
            "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
            "		 where GBB.IsValid = 0 and (GB.Name like :searchText\n" +
            "	OR GBB.Name like :searchText\n" +
            "	OR GBB.IFSCCode like :searchText)",
            countQuery = "SELECT GBB.ID,GBB.BankID,GBB.Name,GBB.IFSCCode,GBB.Status, GB.Name as Bank FROM general_bank_branch_missing GBB\n" +
                    "	LEFT JOIN general_bank GB ON(GB.ID = GBB.BankID)\n" +
                    "	 where GBB.IsValid = 0 and ( GB.Name like :searchText\n" +
                    "	OR GBB.Name like :searchText\n" +
                    "	OR GBB.IFSCCode like :searchText)", nativeQuery = true)
    Page<GeneralBankBranchInf> findAllWithSearchMissingInvalidated(Pageable sortedByIdDesc, String searchText);
    
    
    
    
    Optional<GeneralBankBranch> findByIfscCode(final String ifscCode);



}
