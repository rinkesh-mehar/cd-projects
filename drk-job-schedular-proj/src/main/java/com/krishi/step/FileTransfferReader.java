package com.krishi.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.model.PennyDrop;
import com.krishi.service.PennyDropServiceImpl;

public class FileTransfferReader implements ItemReader<List<PennyDrop>> {

	private static int count = 0;

	@Autowired
	private PennyDropServiceImpl pennyService;

	/*read penny drop data from database and send to writer*/
	@Override
	public List<PennyDrop> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		;
		if (count == 0) {
			count = 1;
			return pennyService.pennyDrop();
		} else {
			count = 0;
			return null;
		}
	}

}
