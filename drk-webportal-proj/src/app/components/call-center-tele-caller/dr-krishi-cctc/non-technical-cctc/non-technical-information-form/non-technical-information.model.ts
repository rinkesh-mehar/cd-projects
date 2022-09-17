export interface DRKrishiNonTechnicalInformationForm {
  state:                   string;
  district:                string;
  tehsil:                  string;
  village:                 string;
  farmerId:                string; // added new
  name:                    string;
  fathername:              string;
  relationshipToReferenceName: string;
  primarymobilemo:         number;
  alternatemobileno:       number;
  referenceperson:         string;
  referencepersonmobileno: number;
  governmentidproof:       boolean;
  ownland:                 boolean;
  irrigatedland:           boolean;
  farmsize:                number;
  majorcropsgrown:         List;
  croppingarea:            number;
  speakinglanguage:        List[];
  mobiletype:              List;
  prscoutname:             string;
  willingnessoffarmer:     boolean;
  vip:                     boolean;
  status:                  number;
  designation:             List;
  otherDesignation:        string;
  scheduledate:            string[];
  mlvisit:                 Date[];
  meetingpoint:            string;  // added for POI - Pranay
  masterData:              MasterDataList;
  comments:                string;
  sellerType:              string;
  cropType:                string;
  regionId:                number;
}

export interface MasterDataList {
  majorcropsgrownlist:  List[];
  speakinglanguagelist: List[];
  mobiletypelist:       List[];
  proxyRelationType:    List[];
  statuslist:           List[];
  designationlist:      List[];
  meetingpointlist:     List[]; // added for POI - Pranay
  seasonList:           List[];
  farmerCropInfoList:   FarmerCropInfo[]; // added for Farmer major crop -CDT-Ujwal
  leadStatus:           List[]; //
}

export interface List {
  id:   number;
  name: string;
}

export interface FormResponse {
  status: number;
  message: string;
  data: DRKrishiNonTechnicalInformationForm;
}

export interface FormSubmitResponse {
  status: number;
  message: string;
  data:string;
}
export interface SubmitNontechincal {
  fatherRelationshipToReferenceName: string;
  userid:                  number;
  taskid:                  number;
  fathername:              string;
  alternatemobileno:       number;
  referenceperson:         string;
  referencepersonmobileno: number;
  governmentidproof:       boolean;
  ownland:                 boolean;
  irrigatedland:           boolean;
  farmsize:                number;
  majorcropsgrown:         List;
  croppingarea:            number;
  speakinglanguage:        List[];
  mobiletype:              List;
  willingnessoffarmer:     boolean;
  vip:                     boolean;
  status:                  number;
  designation:             List;
  otherDesignation:        string;
  scheduledate:            string[];
  meetingpoint:            string; // added for POI - Pranay
  comments:                string;
  cropInfoId:              string[];
  sellerName:              string;
}


//
/**
 * added for farmer major crop info - CDT-Ujwal
 */
export interface FarmerCropInfo
{
  selected: any;
  userId:                     number;
  cropInfoId:                 string;
  farmerId:                   string;
// seasonId:                   number;
  commodityId:                number;
  varietyId:                  number;
  croppingArea:               number;
  yield:                      number;
  hasIrrigation:              number;
  alternateVariety:           string;
  sellerGivenQtyTon:          number;
  dateOfAvailability:         string;
  commodityName:              string;
  seasonName:                 string;
  varietyName:                string;
  sowing:                     string;
  sowingDate:                 string;
  harvest:                    string;
  cropType:                   string;
  leadResponseName:           string;
  cropTypeId:                 number;
}

//
/**
 * added for farmer major crop info - CDT-Ujwal
 */
export interface CropResponse {
  status: number;
  message: string;
  data: FarmerCropInfo[];
}


