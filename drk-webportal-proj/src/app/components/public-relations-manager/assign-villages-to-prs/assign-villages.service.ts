import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { AssignVillagesModel, State, District, Village, Prscoutdetails, Region, TileVillage } from './assign-villages.model';
import { AddUserModel } from '../../user-management/add-user/user.model';
import { environment } from '../../../../environments/environment';

@Injectable()
export class AssignVillagesService {

  constructor(private http: HttpClient) {
  }

  // Method to Submit AssignVillages
  public submitAssignVillagesPRS(data: Prscoutdetails): Observable<any> {
    return this.http.post<any>(environment.prmURL +'assignVillages', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to getStates user
  public getStates(): Observable<State[]> {
    return this.http.get<any>(environment.prmURL +'getStates')
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to getRegion
  public getRegion(regionId: Number): Observable<Region[]> {
    return this.http.get<any>(environment.prmURL +'getRegions/'+regionId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // Method to getVillages
  public getVillage(DistricId: Number): Observable<Village[]> {
    return this.http.get<any>(environment.prmURL +'getVillagesByDistrict/'+DistricId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }


  public getPRscout(userId: Number, regionId :number): Observable<AddUserModel[]> {
    return this.http.get<any>(environment.prmURL +'getScoutNames/'+userId+"/"+regionId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  public getPRscoutByPRM(userId: Number): Observable<AddUserModel[]> {
    return this.http.get<any>(environment.prmURL +'getScoutNames/'+userId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getRegionTemplete(regionId: number, cropDataApiKey): Observable<any> {
    return this.http.get<any>(environment.QPB+'tile-assignment/'+regionId+'?apiKey='+cropDataApiKey+'')
        .map((response) =>  response)
        .catch((error: any) => {
            return Observable.throw(error);
        });
    }


    public getVillagesList(tileId :number, cropDataApiKey): Observable<TileVillage[]> {
      return this.http.get<any>(environment.QPB+'get-villages-by-sub-region-id/'+tileId+'?apiKey='+cropDataApiKey+'')
        .map((response) =>  response)
        .catch((error: any) => {
            return Observable.throw(error);
        });
    }

    public searchPrsAssignTask(data : {}): Observable<any[]> {
      return this.http.post<any>(environment.prmURL+'getAssignmentListFromSearch', data)
         .map((response) => response )
         .catch((error: any) => {
             return Observable.throw(error);
         });
     }

  public getAssignedVillage(weekNumber : Number ,  year : Number): Observable<Village[]> {
    return this.http.get<any>(environment.prmURL +'getAssignedVillage/'+weekNumber+"/"+year)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  // Assigned PRS details
  public getAssignedPRSDetails(assigmentId: number): Observable<any> {
    return this.http.get<any>(environment.prmURL+'getAssignmentTaskForEdit/'+assigmentId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

}
