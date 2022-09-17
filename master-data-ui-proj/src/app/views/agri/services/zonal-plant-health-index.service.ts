import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { ZonalPlantHealthIndex } from '../models/ZonalPlantHealthIndex';
import { PageZonalPlantHealthIndex } from '../models/page-zonal-plant-health-index';


@Injectable({
  providedIn: 'root'
})
export class ZonalPlantHealthIndexService {

    // Base url
    baseUrl = environment.apiUrl+'/zonal/plant-health-index';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreatePlantHealthIndex(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetPlantHealthIndex(id): Observable<ZonalPlantHealthIndex> {
      return this.http.get<ZonalPlantHealthIndex>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllPlantHealthIndex(): Observable<ZonalPlantHealthIndex> {
      return this.http.get<ZonalPlantHealthIndex>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // PUT
    UpdatePlantHealthIndex(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl +"/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeletePlantHealthIndex(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete' , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
 
        // Reject
   RejectPlantHealthIndex(id) {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   //  Finalize
   FinalizePlantHealthIndex(id) {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
 
   // Approve
   ApprovePlantHealthIndex(id) {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }

   // get Variety List
   getVarietyListByCommodity(commodityId): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/variety-list?commodityId=' +  commodityId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

  // get Phenophase List
  getPhenophaseListByCommodityAndVariety(commodityId,varietyId): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/phenophase-list?commodityId=' +  commodityId + '&varietyId=' +   varietyId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

   //For-Pagination
   getZonalPlantHealthIndexPagenatedList(page: number, rowSize: number,searchText,missing): Observable<PageZonalPlantHealthIndex> {
    this.maxSize = rowSize || this.maxSize;
     console.log("missing : " + missing);
    var url = this.baseUrl + "/pagenated-list?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageZonalPlantHealthIndex>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
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
