package in.cropdata.configserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.configserver.model.Properties;

public interface PropertiesRepository extends JpaRepository<Properties, Long> {

    public List<Properties> findByApplicationName(String applicationName);

    public List<Properties> findByApplicationNameAndProfile(String applicationName, String profile);

    public List<Properties> findByApplicationNameAndProfileAndLabel(String applicationName, String profile, String label);

    public List<Properties> findByApplicationNameAndProfileAndLabelAndKey(String applicationName, String profile, String label, String key);

    public Optional<Properties> findOneByApplicationNameAndProfileAndLabelAndKey(String applicationName, String profile, String label, String key);

}