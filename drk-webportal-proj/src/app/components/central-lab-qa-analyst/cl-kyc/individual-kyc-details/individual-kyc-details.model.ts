export interface IndividualKycDetails{
	taskId: number;
	farmerId: string;
	documentType: string;
	basePath: string;
	idProofFront: string;
	idProofBack: string;
	farmerName: string;
	farmerHusbandName: string;
	gender: string;
	dateOfBirth: string;
	mobileNumber: number;
	alternateMobileNumber: number;
	address: string;
	comment: string;
}
export interface ResponseData {
    statusCode:string;
    message:string;
    data:IndividualKycDetails;
}





