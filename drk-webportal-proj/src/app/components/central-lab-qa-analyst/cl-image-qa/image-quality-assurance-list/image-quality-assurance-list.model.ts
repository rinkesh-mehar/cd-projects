export interface ClImage{
  qaId: number;
  commodityName: string;
  stateName: string;
  regionName: string;
  commodityId: number;
  stateId: number;
  regionId: number;
}
export interface ResponseData {
  statusCode:string;
  message:string;
  data: ClImage[];
}
