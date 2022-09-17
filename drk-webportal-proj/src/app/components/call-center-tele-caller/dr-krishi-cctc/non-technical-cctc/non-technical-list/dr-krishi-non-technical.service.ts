import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { DrKrishiNonTechnical, callingStatusModel, ResponseMessage, SubmitNontechincalCallingStatus } from './dr-krishi-cctc-non-technical.model';
import { environment } from '../../../../../../environments/environment';


@Injectable()
export class DrKrishiNonTechnicalService {

    constructor(private http: HttpClient) {
    }

    // getLeadCallingListForward
    public getLeadCallingListForForward(areaId, commodityId, stateId, regionId, districtId, villageId, parameters): Observable<any> {
        return this.http.post<any>(environment.cctcURL+'getLeadCallingListForward?stateId='+stateId+'&regionId='+regionId+'&districtId='+districtId+'&villageId='+villageId+'&commodityId='+commodityId+'&areaId='+areaId,parameters, {})
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    // getLeadCallingListSpot
    public getLeadCallingListForSpot(areaId, commodityId, stateId, regionId, districtId, villageId, parameters): Observable<any> {
        return this.http.post<any>(environment.cctcURL+'getLeadCallingListSpot?stateId='+stateId+'&regionId='+regionId+'&districtId='+districtId+'&villageId='+villageId +'&commodityId='+commodityId +'&areaId='+areaId, parameters, {})
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
    // Get States
    public getCCTCNonTechnicalState(): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLeadCallingStateList/')
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    // Get Regions
    public getCCTCNonTechnicalRegion(stateId): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLeadCallingRegionListByStateId/'+stateId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    // Get Districts
    public getCCTCNonTechnicalDistricts(regionId): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLeadCallingDistrictListByRegionId/'+regionId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public getCCTCNonTechnicalCommodity(): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLeadCallingCommodityList')
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public getLandHoldingSize(): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLandHoldingSize')
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public getCCTCNonTechnicalCommoditySpot(): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getSpotCommodityList')
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }


    // Get Villages for spot
    public getCCTCNonTechnicalVillages(districtId): Observable<any> {
        return this.http.get<any>(environment.cctcURL+'getLeadCallingVillageListByDistrictId/'+districtId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

  // Get Villages
  public getCCTCNonTechnicalSpotVillages(districtId): Observable<any> {
    return this.http.get<any>(environment.cctcURL+'spot/getLeadCallingVillageListByDistrictId/'+districtId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

     // Dr Krishi Non Technical Calling status submit
        public submitUserGI(data: callingStatusModel): Observable<ResponseMessage> {
            return this.http.post<ResponseMessage>(environment.cctcURL+'rejectTask', data)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
        }

    //added for calling status list - Pranay
    public getCallingStatusList(): Observable<any> {
        return this.http.get<any>(environment.cctcURL + 'getCallingStatusList/')
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}


