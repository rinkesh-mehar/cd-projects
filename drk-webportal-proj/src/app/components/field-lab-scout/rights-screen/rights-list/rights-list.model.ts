export interface RightsListModel {
    id: string;
    versionNumber: number;
    caseId: string;
    currentQuantity: number;
    estimatedQuantity: number;
    standardQuantity: number;
    allowableVarianceQtyPos: number;
    allowableVarianceQtyNeg: number;
    allowableVarianceQtyPosPer: number;
    allowableVarianceQtyNegPer: number;
    estimatedQuality: string;
    currentQuality: string;
    allowableVarianceQuality: string;
    mbep: number;
    domesticRestriction: string;
    internationalRestriction: string;
    deliveryDateTime: any;
    logisticHubId: string;
    logisticHubAddress: string;
    farmerDefault: string;
    splitField: string;
    geographicallyAdjustent: string;
    riskReport: string;
    rightCertificate: string;
    recordCreatedBy: number;
    recordDateTime?: any;
    status: string;
    statusReceivingDate: any;
    comments: string;
    lotId: string;
    isVerified: number;
    transactionId: string;
    phenophaseId: number;
    stage?: any;
    dueAmount?: any;
}

export interface ViewRightListModel {
    caseId: string;
    expectedYield?: any;
    fieldStatus?: any;
    ndviImages?: any;
    seasonId: number;
    varietyId: number;
    sowingWeek: number;
    harvestWeek?: any;
    cropArea: number;
    variety_name: string;
    commodityId: number;
    hscode: string;
    commodityName: string;
    season_name: string;
    farmerId: string;
    farmerDueAmount: number;
    villageId: number;
    villageName: string;
    subregionId: number;
    regionId: number;
    regionName: string;
    stateCode: number;
    stateName: string;
    districtId: number;
    districtName: string;
    farmerEmail: string;
    farmerAccountNumber: string;
    isPennydropped?: any;
    isKycVerified?: any;
    isKmlVerified?: any;
}

export interface RootObject {
    rightsList: RightsListModel[];
    viewRightList: ViewRightListModel[];
}



