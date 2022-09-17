/**
 * 
 */
package in.cropdata.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.gateway.model.Zuul;

/**
 * @author Vivek Gajbhiye
 *
 */
public interface ZuulRepository extends JpaRepository<Zuul, Integer> {

	public Optional<Zuul> findByServiceId(String serviceId);
	

}
