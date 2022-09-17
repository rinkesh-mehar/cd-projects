export interface sampleDiagnosis {
	farmerid: string;
	caseid: string;
	village: string;
	farmermobilenumber: string;
	crop: string;
	status: string;
	receiveddate: string;
	levelonedate: string;
	taskstatus: string;
	taskId:number;
}

export interface ResponseMessage {
	statusCode:number,
	message:string,
	data:sampleDiagnosis[]
}