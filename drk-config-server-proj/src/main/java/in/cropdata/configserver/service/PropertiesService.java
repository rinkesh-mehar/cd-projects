package in.cropdata.configserver.service;

import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.configserver.exception.RecordNotFoundException;
import in.cropdata.configserver.model.Properties;
import in.cropdata.configserver.repository.PropertiesRepository;

@Service
public class PropertiesService {
    
    Logger logger = LoggerFactory.getLogger(PropertiesService.class);

    @Autowired
    private PropertiesRepository repository;

    public Iterable<Properties> getAll() {
	return repository.findAll();
    }

    public Iterable<Properties> get(String application) {
	return repository.findByApplicationName(application);
    }

    public Iterable<Properties> get(String application, String profile) {
	return repository.findByApplicationNameAndProfile(application, profile);
    }

    public Iterable<Properties> get(String application, String profile, String label) {
	return repository.findByApplicationNameAndProfileAndLabel(application, profile, label);
    }

    public Iterable<Properties> get(String application, String profile, String label, String key) {
	return repository.findByApplicationNameAndProfileAndLabelAndKey(application, profile, label, key);
    }

    public Optional<Properties> update(Properties insuranceProperties) throws URISyntaxException {
	Optional<Properties> existing = repository.findOneByApplicationNameAndProfileAndLabelAndKey(insuranceProperties.getApplicationName(), insuranceProperties.getProfile(), insuranceProperties.getLabel(), insuranceProperties.getKey());
	return existing.map(rProp -> {
	    rProp.setValue(insuranceProperties.getValue());
	    return repository.save(rProp);
	});
    }

    public Properties create(Properties insuranceProperties) {
	return repository.save(insuranceProperties);
    }

    public Optional<Properties> getPropertyById(Long id) throws RecordNotFoundException {
	Optional<Properties> foundProperty = repository.findById(id);
	if (foundProperty.isPresent()) {
	    return foundProperty;
	}else {
	    throw new RecordNotFoundException("Property Does not Exist");
	}
    }

    public void deletePropertyById(Long id) throws RecordNotFoundException {
	Optional<Properties> foundProperty = repository.findById(id);
	if (foundProperty.isPresent()) {
	    repository.deleteById(id);
	    logger.trace("Property deleted with id - "+id);
	}else {
	    throw new RecordNotFoundException("Property Does not Exist");
	}
    }

    public Properties createOrUpdateProperty(Properties entity) {
	if(entity.getId()  == null)
        {
            entity = repository.save(entity);
             
            return entity;
        }
        else
        {
            Optional<Properties> property = repository.findById(entity.getId());
             
            if(property.isPresent())
            {
        	Properties newProperty = property.get();
        	newProperty.setApplicationName(entity.getApplicationName());
        	newProperty.setProfile(entity.getProfile());
        	newProperty.setLabel(entity.getLabel());
                newProperty.setKey(entity.getKey());
                newProperty.setValue(entity.getValue());
 
                newProperty = repository.save(newProperty);
                 
                return newProperty;
            } else {
                entity = repository.save(entity);
                 
                return entity;
            }
        }
    }
}