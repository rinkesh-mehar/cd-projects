package in.cropdata.farmers.app.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrkFarmerDTO implements Serializable {

    private String success;

    private Error error;

    private Integer status;

    private Object data;
}
