package in.cropdata.cdtmasterdata.dto.interfaces;


import in.cropdata.cdtmasterdata.model.GeoCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 *
 */
public interface GeoCountryInfDto
{
    public Integer getId();

    public Integer getCountryCode();

    public String getName();

    public Date getUpdatedAt();

    public Date getCreatedAt();

    public String getStatus();
}
