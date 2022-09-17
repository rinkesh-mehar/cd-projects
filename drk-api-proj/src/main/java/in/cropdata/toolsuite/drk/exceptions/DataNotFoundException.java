package in.cropdata.toolsuite.drk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.exceptions
 * @date 10/07/20
 * @time 5:50 PM
 * To change this template use File | Settings | File and Code Templates
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException
{
    public DataNotFoundException(String message)
    {
        super(message);
    }
}
