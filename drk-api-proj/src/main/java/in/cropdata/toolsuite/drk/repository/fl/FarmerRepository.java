package in.cropdata.toolsuite.drk.repository.fl;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.toolsuite.drk.model.cases.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, BigInteger> {


}
