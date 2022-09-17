package com.krishi.service;

import com.krishi.entity.Rights;
import com.krishi.model.KafkaRightsModel;

public interface KafkaRightsService {

	void updateRights(KafkaRightsModel model) throws Exception;

    void saveEmail(Rights newRight);
}
