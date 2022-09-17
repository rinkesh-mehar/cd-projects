package in.cropdata.portal.repository;

import in.cropdata.portal.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer>
{
    Optional<EmailTemplate> findAllByName(String name);
}