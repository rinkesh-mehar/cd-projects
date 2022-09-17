import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import {
  BenchmarkedImageModel,
  Stress,
  GeneralInfo,
  SubmitImages,
  ResponseData,
  RejectImage, CompleteTask,
} from './image-list.model';
import { environment } from '../../../../../environments/environment';
import {error} from "util";

@Injectable()
export class ClImageList {

  constructor(private http: HttpClient) {
  }

  // Method to get Benchmark images
  /** get stress photos */
  public getClImageListService(qaId, commodityId, stateId, regionId): Observable<ResponseData> {
    return this.http.get<ResponseData>(environment.ImageQaURL + 'getImageDetails/' + qaId + '/' + commodityId + '/' + stateId + '/' + regionId + '/' + true)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getSpotStressDetails(spotId, commodityId): Observable<ResponseData> {
    return this.http.get<ResponseData>(environment.ImageQaURL + 'getSpotStressDetails/' + spotId + '/' + commodityId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  public getTaskSpotList(commodityId, stateId, regionId): Observable<ResponseData> {
    return this.http.get<ResponseData>(environment.ImageQaURL + 'getTaskSpot/' + '/' + commodityId + '/' + stateId + '/' + regionId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // get health photos
  public getHealthDetails(spotId): Observable<any> {
    return this.http.get<any>(environment.ImageQaURL + 'getSpotHealthDetails/' + spotId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to Approve Benchmark images
  public getsubmitBenchmarkImages(data: SubmitImages): Observable<ResponseData> {
    return this.http.post<ResponseData>(environment.ImageQaURL + 'uploadBenchmarkedImage/', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to Reject Benchmark images

  public rejectBenchmarkedImage(data: RejectImage): Observable<ResponseData> {
    return this.http.post<ResponseData>(environment.ImageQaURL + 'rejectBenchmarkedImage/', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public completeTask(data: CompleteTask): Observable<ResponseData> {
    return this.http.post<ResponseData>(environment.ImageQaURL + 'completeTask/', data )
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
}
