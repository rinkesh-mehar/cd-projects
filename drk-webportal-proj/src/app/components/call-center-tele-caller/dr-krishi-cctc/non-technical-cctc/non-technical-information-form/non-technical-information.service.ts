import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import {FormResponse, FormSubmitResponse, FarmerCropInfo, CropResponse} from './non-technical-information.model';
import {environment} from '../../../../../../environments/environment';


@Injectable()
export class DrKrishiNonNonTechnicalInformationFormService {

  constructor(private http: HttpClient) {
  }


  public getVarietyList(commodityId: number): Observable<any> {
    return this.http.get<any>(environment.cctcURL + 'varietyList/' + commodityId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Dr Krishi Non Technical form
  public getDrKrishiNonTechnicalSpotFormService(taskId: number): Observable<FormResponse> {
    return this.http.get<FormResponse>(environment.cctcURL + 'leadCallingSpotDetail/' + taskId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public geFarmerCropListByTaskId(taskId: string): Observable<CropResponse> {
    return this.http.get<CropResponse>(environment.cctcURL + 'getFarmerCropList/' + taskId)
      .map((Response) => Response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to save user
  public submitDrKrishiNonTechnicalInformationForm(data): Observable<FormSubmitResponse> {
    return this.http.post<any>(environment.cctcURL + 'leadCallingDetail', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getCommodityListByCropType(cropType, farmerId): Observable<any> {
    return this.http.get<any>(environment.cctcURL + 'getCommodityList' + '?cropType=' + cropType + '&regionId=' + farmerId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  public saveAndUpdateFarmerCropInfo(data): Observable<FormSubmitResponse> {
    console.log(JSON.stringify(data));
    return this.http.post<any>(environment.cctcURL + 'updateFarmerCropInfo', data)
      .map((Response) => Response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }



  public deleteFarmerCropInfo(farmerCropId: string): Observable<FormSubmitResponse> {
    return this.http.delete<any>(environment.cctcURL + 'deleteFarmerCropInfo/' + farmerCropId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }




// Dr Krishi Non Technical form
  public getDrKrishiNonTechnicalInformationFormService(taskId: number): Observable<FormResponse> {
    return this.http.get<FormResponse>(environment.cctcURL + 'leadCallingDetail/' + taskId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getFarmerCropInfo(farmerCropId: string): Observable<CropResponse> {
    return this.http.get<CropResponse>(environment.cctcURL + 'getExistingCropInfo/' + farmerCropId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
}
