export interface GeneralInfo {
  taskId: number;
  commodity: string;
  state: string;
  region: string;
  basePath: string;
  spotId: string;
  stressId: number;
  stressName: string;
  taskSpotModels: TaskSpotModels[];
  spotStressModels: SpotStressModels[];
  // healthImageModelList: SpotHealthModels[];
  stressModelList: Stress[];
  healthImageModelList: Health[];
}

export interface SpotStressModels {
  spotId: string;
  stressModelList: Stress[];
}

export interface Stress {
  stressName: string;
  stressId: string;
  stressImageModelList: BenchmarkedImageModel[];
}

export interface Health {
  id: number;
  symptom: string;
  taskId: string;
  // imageName: string;
  imageUrl: string;
  side: string;
  // benchmarkedImageDetails: BenchmarkedImageModel[];
}

export interface SpotHealthModels {

  spotId: string;
  healthImageModelList: Health[];
}

export interface BenchmarkedImageModel {
  id: number;
  symptom: string;
  taskId: string;
  imageUrl: string;
  frontPhoto;
  leftPhoto;
  rightPhoto;
  side: string;
}

export interface SubmitImages {
  userId: number;
  id: number;
  imageName: string;
  image: string;
  comment: string;
  status: string;
}
export interface RejectImage {
  userId: number;
  benchmarkedImageId: number;
  comment: string;
  status: string;
}
export interface ResponseData {
  statusCode:string;
  message:string;
  data: GeneralInfo;
}

export interface TaskSpotModels {
  taskId: string;
  caseId: string;
  cropType: string;
  count: number;
  disable: boolean;
  spotIds: string[];
  spotModels: SpotModels[];
  // spotStressModels: SpotStressModels[];
  // spotHealthModels: SpotHealthModels[];
}

export interface SpotModels {
  spotId: string;
  // spotStressModels: SpotStressModels[];
  stressModelList: Stress[];
  spotHealthModels: SpotHealthModels[];
}

export interface CompleteTask {
  taskId: string;
  userId: number;
  cropType: string;
}
