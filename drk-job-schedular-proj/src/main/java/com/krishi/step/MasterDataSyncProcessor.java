package com.krishi.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.service.MasterdataSyncImpl;

public class MasterDataSyncProcessor  implements Tasklet {
	
	@Autowired
	private MasterdataSyncImpl masterdataSyncImpl;

	/*call masterdata api and update the database*/
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		masterdataSyncImpl.run();
		return RepeatStatus.FINISHED;
	}

}
