import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/Rx';
import { HttpClient} from '@angular/common/http'
import { Rlt, Spot, SpotData, RecommendationData } from './rlt.model';
//import { RegionalLabData } from './rlt-case-details.model';
import { environment } from '../../../../environments/environment';
import { DiagnoseDetails, GeneralInfoMaster, GeneralInfos, ResultsMaster, StressSymptomsModel, SymptomMasterList, Symptom } from './rlt-new-case-details.model';
import {ResponseMessage} from '../../regional-lab-manager/manage-hardware/manage-hardware-list/manage-hardware.model';

@Injectable({
  providedIn: 'root'
})
export class RltCaseDetailsService {

  constructor(private http: HttpClient) { }



  // Method to General information data New
  public getRltGeneraldetails(id): Observable<GeneralInfoMaster> {
    return this.http.get<any>(environment.rltURL + 'getGeneralInformation/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  // Method to Diagnose information data New
  public getRltcasedetails(id,userID): Observable<DiagnoseDetails> {
    return this.http.get<any>(environment.rltURL + 'getDiagnoseDetails/' + id + "/1/" + userID)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to Recommendation information data New
  public getRltRecommendationdetails(id,userID): Observable<ResultsMaster> {
    return this.http.get<any>(environment.rltURL + 'getRecommendations/' + id + "/" + userID)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
  // Method to Recommendation information data New


  // Method to Symptom information data New
  public getRltSymtoms(stressId): Observable<Symptom[]> {
    return this.http.get<any>(environment.rltURL + 'getSymptoms/' + stressId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
  // Method to Symptom information data New






  // Method to Crop information data
  public getCropInformation(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getCropInformation/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
  // Method to Irrigation Details data
  public getIrrigationDetails(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getIrrigationInformation/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  //Method to Fertilizer data
  public getFertilizer(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getFertilizer/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  //Method to Seed Treatment
  public getSeedTreatment(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getSeedTreatment/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
  //Method to Remedial Measures
  public getRemedialMeasures(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getRemedialMeasures/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  public saveSpotData(spotData): Observable<RecommendationData> {


    return this.http.post<any>(environment.rltURL + 'saveSpotDetails', spotData)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  public submitRecommendation(recommendationData): Observable<RecommendationData> {
    return this.http.post<any>(environment.rltURL + 'saveDiagoseDetails', recommendationData)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  // RLM services
  public getRLTUsers(id): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getRlt/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  public reassignTask(data): Observable<any> {
    return this.http.post<any>(environment.rltURL + 'rlmReassign', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  public getParameterList(taskId): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getParameterList/' + taskId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getCommodityInfo(data): Observable<any> {
    return this.http.get<any>(environment.rltURL + 'getCommodityInfo/' + data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getBand(data): Observable<any> {
    return this.http.post<any>(environment.rltURL + 'getBand/', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  public approveRecommendation(recommendationData): Observable<RecommendationData> {
    return this.http.post<any>(environment.rltURL + 'rlmApprove', recommendationData)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }


}
