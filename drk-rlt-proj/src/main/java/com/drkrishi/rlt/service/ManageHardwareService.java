package com.drkrishi.rlt.service;

import com.drkrishi.rlt.model.ManageHardwareModel;
import com.drkrishi.rlt.model.ResponseMessage;

public interface ManageHardwareService {

	ResponseMessage getAssignedHardwardList(Integer userId, String searchKeyward);

	ResponseMessage unTagHardware(ManageHardwareModel manageHardwareModel);

}
