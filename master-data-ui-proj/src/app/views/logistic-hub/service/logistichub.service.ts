import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { GeoState } from '../model/GeoState';
import {LhPage} from '../model/lhPage';
import {ResponseMessage} from '../../agri/models/ResponseMessage';




@Injectable({
  providedIn: 'root'
})
export class LogistichubService {

  baseUrl = environment.logisticHubApiUrl + '/logisticHub';

  maxSize: number = 10;

  constructor(private httpClient: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  getStateCodes(): Observable<GeoState> {
    console.log('Inside service geoStateCodes');
    return this.httpClient
      .get<GeoState>(this.baseUrl + '/getStateCodes')
      .pipe(retry(1),
        catchError(this.errorHandl));
  }

  getLhDetails(){
    return sessionStorage.getItem('lhFormGroup');
  }
 /* getOwnerDetails(){
    return sessionStorage.getItem('userFormGroup');
  }
  getDistanceDetails(){
    return sessionStorage.getItem('hubDistanceFormGroup');
  }
  getStructureDetails(){
    return sessionStorage.getItem('hubStructureFormGroup');


  }*/

  setLhDetails(lhDetails) {
    sessionStorage.setItem('lhFormGroup', JSON.stringify(lhDetails));
  }

  setOwnerDetails(ownerDetails) {
    sessionStorage.setItem('userFormGroup', JSON.stringify(ownerDetails));
  }

  setDistanceDetails(distanceDetails) {
    sessionStorage.setItem('hubDistanceFormGroup', JSON.stringify(distanceDetails));
  }
  setStructureDetails(structureDetails) {
    sessionStorage.setItem('hubStructureFormGroup', JSON.stringify(structureDetails));
  }

  getDistrictCodes(stateCode, district): Observable<any> {
    console.log('State code for district :' + stateCode);
    return this.httpClient
      .get(this.baseUrl + '/getDistrictCodes?stateCode=' + stateCode + '&district=' + district)
      .pipe(retry(1),
        catchError(this.errorHandl));
  }

  getCommoditys(): Observable<any> {
    return this.httpClient.get(this.baseUrl + '/commodity-list')
        .pipe(retry(1),
            catchError(this.errorHandl));
  }

  getCollectedPageLh(page: number, rowSize: number, searchText): Observable<LhPage> {
    this.maxSize = rowSize || this.maxSize;
    const url = this.baseUrl + '/collectedLhWarehouse' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<LhPage>(url)
        .pipe(
            map(response => {
              const data = response;
              return data;
            }));
  }

  getRejectedPageLh(page: number, rowSize: number, searchText): Observable<LhPage> {
    this.maxSize = rowSize || this.maxSize;
    const url = this.baseUrl + '/rejectedLhWarehouse' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<LhPage>(url)
        .pipe(
            map(response => {
              const data = response;
              return data;
            }));
  }
  getCollectedLhById(id): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + '/getLhDetails' + '/' + id)
        .pipe(
            catchError(this.errorHandl)
        );
  }

  approveCollectedLh(id): Observable<ResponseMessage>{
    const logisticHubWarehouse = {
      'id' : id
    };
    return  this.httpClient.put<ResponseMessage>(this.baseUrl + '/approve/collected-lh', logisticHubWarehouse)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }

  rejectWarehouse(data): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + '/lhWarehouse-reject/', JSON.stringify(data), this.httpOptions)
        .pipe(retry(1),
            catchError(this.errorHandl));
  }
  storeLhDetails(lhDetails, lhStructureDetails, ownerDetails, lhDistance): Observable<any> {
    let data = {
      'logisticHub': lhDetails,
      'ownerDetails': ownerDetails,
      'lhMetaData': lhStructureDetails,
      'lhDistance': lhDistance
    };
    console.log('latest data------->' + JSON.stringify(data));
      return this.httpClient.post(this.baseUrl + '/storeHubDetails', JSON.stringify(data), this.httpOptions)
          .pipe(retry(1),
              catchError(this.errorHandl));
  }


  updateLhDetails(lhDetails, lhStructureDetails, ownerDetails, lhDistance, id): Observable<any> {
    let data = {
      'logisticHub': lhDetails,
      'ownerDetails': ownerDetails,
      'lhMetaData': lhStructureDetails,
      'lhDistance': lhDistance
    };
    console.log('latest data------->' + JSON.stringify(lhDetails));
    return this.httpClient.put(this.baseUrl + '/updateHubDetails' + '/' + id, JSON.stringify(data), this.httpOptions)
        .pipe(retry(1),
            catchError(this.errorHandl));
  }

    getShortlistedKycDetails(page: number, rowSize: number, searchText):Observable < any > {
      this.maxSize = rowSize || this.maxSize;
      // return this.httpClient
      //     .get<any>(this.baseUrl + '/getShortlistedHub')
      //     .pipe(retry(1),
      //         catchError(this.errorHandl));

      let url = this.baseUrl + '/getShortlistedHub' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.httpClient.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
    }

  getPVApprovedList(page: number, rowSize: number, searchText): Observable<any> {
    this.maxSize = rowSize || this.maxSize;
    // return this.httpClient
    //   .get(this.baseUrl + '/getLhPhysicalApprovedList')
    //   .pipe(retry(1),
    //     catchError(this.errorHandl));
    let url = this.baseUrl + '/getLhPhysicalApprovedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<any>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }

  getLhBasicDetails(viewId: string) {
    console.log('id----',viewId)
    return this.httpClient
    .get(this.baseUrl + '/getLhBasicDetails?warehouseId=' + viewId)
    .pipe(retry(1),
      catchError(this.errorHandl));
    // .flatMap((response) => response.json())
    // .filter((person) => person.id > 5)
    // .map((person) => "Dr. " + person.name)
  }

  markWarehouseComplete(viewId: string,type: number,  reasonID): Observable<any> {
    console.log('id----',viewId)
    return this.httpClient.post<any>(this.baseUrl + '/markWarehouseComplete?warehouseId=' + viewId+'&type='+type + '&reasonId=' + reasonID, this.httpOptions)
    .pipe(retry(1),
      catchError(this.errorHandl));
  }

  whBulkInsertByExcelUpload(file) {
    let formData: FormData = new FormData;
    if (file) {
      formData.append("excelFile", file);
    }

    return this.httpClient.post<ResponseMessage>(this.baseUrl + '/wh-bulk-insert-by-excel-upload', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  errorHandl(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }


}
