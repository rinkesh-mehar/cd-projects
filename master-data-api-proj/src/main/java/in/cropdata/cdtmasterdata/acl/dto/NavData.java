package in.cropdata.cdtmasterdata.acl.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NavData {

    String name;
    String url;
    String icon;
    List<NavData> children =  new ArrayList<NavData>();;
}
