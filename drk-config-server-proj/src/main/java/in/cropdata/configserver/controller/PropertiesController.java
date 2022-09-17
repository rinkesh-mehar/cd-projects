package in.cropdata.configserver.controller;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.configserver.model.Properties;
import in.cropdata.configserver.service.PropertiesService;

@RestController
@RequestMapping("/api")
public class PropertiesController {

    @Autowired
    private PropertiesService proertyService;

    @GetMapping("/props")
    public Iterable<Properties> getAll() {
	return proertyService.getAll();
    }

    @GetMapping("/props/{application}")
    public Iterable<Properties> get(@PathVariable() String application) {
	return proertyService.get(application);
    }

    @GetMapping("/props/{application}/{profile}")
    public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile) {
	return proertyService.get(application, profile);
    }

    @GetMapping("/props/{application}/{profile}/{label}")
    public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile, @PathVariable() String label) {
	return proertyService.get(application, profile, label);
    }

    @GetMapping("/props/{application}/{profile}/{label}/{key}")
    public Iterable<Properties> get(@PathVariable() String application, @PathVariable() String profile, @PathVariable() String label, @PathVariable() String key) {
	return proertyService.get(application, profile, label, key);
    }

    @PutMapping("/props")
    public Optional<Properties> update(@Valid @RequestBody Properties insuranceProperties) throws URISyntaxException {
	return proertyService.update(insuranceProperties);
    }

    @PostMapping("/props")
    public Properties create(@Valid @RequestBody Properties properties) {
	return proertyService.create(properties);
    }
}
