package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 09/11/20
 * @time 6:22 PM
 */
@Data
@Entity(name = "weather_based_travel_time")
public class WeatherBasedTravelTime
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "MinPerKm")
    private String minPerKm;
    
    @Column(name = "Status")
    private String status;
}
