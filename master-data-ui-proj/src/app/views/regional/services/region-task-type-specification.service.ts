import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, from } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { PageRegionTaskTypeSpecification } from '../models/PageRegionTaskTypeSpecification';
import { environment } from '../../../../environments/environment';
import { RegionTaskTypeSpecifications } from "../models/RegionTaskTypeSpecifications";


@Injectable({
  providedIn: 'root'
})
export class RegionTaskTypeSpecificationService {

  // Base url
  baseUrl = environment.apiUrl + '/regional/task-type-specification';
  maxSize: number = 10;
  constructor(private http: HttpClient) { }
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  //For-Pagination
    getPageRegionTaskTypeSpecification(page: number,searchText, rowSize: number,isValid: number,missing): Observable<PageRegionTaskTypeSpecification> {
      this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText+ '&missing=' + missing;
        return this.http.get<PageRegionTaskTypeSpecification>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

  
  // POST
  CreateTaskTypeSpecification(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }  

  // GET By ID
  GetTaskTypeSpecification(id): Observable<RegionTaskTypeSpecifications> {
    return this.http.get<RegionTaskTypeSpecifications>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

   // PUT
   UpdateTaskTypeSpecification(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // DELETE
  DeleteTaskTypeSpecification(id){
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }


// Reject
RejectTaskTypeSpecification(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//  Finalize
FinalizeTaskTypeSpecification(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}


// Approve
ApproveTaskTypeSpecification(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}
// ================================================================= all list
//state
getStateList(): Observable<any> {
  var url = this.baseUrl+ "/getStateList";
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}

//season
getSeasonList(): Observable<any> {
  var url = this.baseUrl+ "/getSeasonList";
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}
//commodity
getCommodityList(): Observable<any> {
  var url = this.baseUrl+ "/getCommodityList";
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}

//variety
getVerietyList(commodityId: number): Observable<any> {
  var url = this.baseUrl+ "/getVarietyList" + "?commodityID=" + commodityId;
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}


//phenophase
getPhenophaseList(commodityId: number,varietyId: number): Observable<any> {
  var url = this.baseUrl+ "/getPhenophaseList" + "?commodityID=" + commodityId + "&varietyID=" + varietyId;
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}

//Task Type
getTaskTypeList(): Observable<any> {
  var url = environment.apiUrl+ "/task-type/list";
  return this.http.get<any>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}

// POST
moveToMaster(id): Observable<ResponseMessage> {
  return this.http.post<ResponseMessage>(this.baseUrl + '/move-to-master/' + id, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Error handling
  errorHandl(error) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
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
