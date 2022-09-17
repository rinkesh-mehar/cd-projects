package in.cropdata.configserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.configserver.model.Application;


public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
