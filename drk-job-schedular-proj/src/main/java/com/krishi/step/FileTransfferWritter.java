package com.krishi.step;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.krishi.entity.StaticData;
import com.krishi.model.PennyDrop;
import com.krishi.repository.StaticDataRepository;

public class FileTransfferWritter implements ItemWriter<List<PennyDrop>> {
	@Autowired
	private StaticDataRepository staticDataRepository;
	private FileWriter fw = null;

	@Override
	public void write(List<? extends List<PennyDrop>> items) throws Exception {

		if(items.get(0).size() > 0) {
			writeFile2(items.get(0));
		}
	}

	/*process and create pennydrop file in the upload folder*/
	private void writeFile2(List<PennyDrop> penyDrop) throws IOException {
		StaticData instrumentamount = staticDataRepository.findByKey("FileGenerationLimitValue");
		StaticData pennydropFileSerialNo = staticDataRepository.findByKey("pennydropFileSerialNo");
		String fileLimitValue = instrumentamount.getValue();
		int fileGenarationLimitValue = Integer.valueOf(fileLimitValue);
		int counter = Integer.parseInt(pennydropFileSerialNo.getValue()) +1;
		for (int i = 0; i < penyDrop.size(); i++) {
			if (fw == null) {
				fw = new FileWriter(penyDrop.get(i).getBasePath() + penyDrop.get(i).getFileName() + "_" + getDate()
						+ "_" + counter + ".txt");
			}

			fw.write(String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s||%s|%s|%s||NEW|\n",
					penyDrop.get(i).getCdtTransactionId(), 
					penyDrop.get(i).getUserEntityCode(),
					penyDrop.get(i).getPaymentProductCode(), 
					penyDrop.get(i).getFarmerName(),
					penyDrop.get(i).getInstrumentAmount(), 
					penyDrop.get(i).getPaymentInstructionDate(),
					penyDrop.get(i).getBeneficiaryBankAccountNumber(), 
					penyDrop.get(i).getBeneficaryIFSC(),
					penyDrop.get(i).getDebitAccountNo(), 
					penyDrop.get(i).getDebitNarration(),
					penyDrop.get(i).getCreditNarration(), 
					penyDrop.get(i).getIfscValidation(),
					penyDrop.get(i).getBeneficaryBankCode(), 
					penyDrop.get(i).getFarmerMobileNumber(),
					penyDrop.get(i).getFarmeremailId(),  
					penyDrop.get(i).getRightId(), 
					penyDrop.get(i).getFarmerAddress(),
					penyDrop.get(i).getFarmerPANNumber() 
					));
			

			if (i == fileGenarationLimitValue && i != 0) {
				fw.close();
				fw = null;
				++counter;
			}
		}

		if (fw != null) {
			fw.close();
			pennydropFileSerialNo.setValue(Integer.toString(counter));
			staticDataRepository.saveAndFlush(pennydropFileSerialNo);
			
		}
	}

	private static String getDate() {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String strDate = formatter.format(date);
		return strDate;

	}

}
