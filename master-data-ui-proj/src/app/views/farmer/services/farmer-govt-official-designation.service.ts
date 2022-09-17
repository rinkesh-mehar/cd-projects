import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FarmerGovtDepartmentService } from './farmer-govt-department.service';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerGovtOfficialDesignation } from '../models/FarmerGovtOfficialDesignation';
import { PageFarmerGovtOfficialDesignation } from '../models/PageFarmerGovtOfficialDesignation';

@Injectable({
  providedIn: 'root'
})
export class FarmerGovtOfficialDesignationService {
  
  baseUrl = environment.apiUrl + '/farmer/govt-official-designation';
  maxSize: number = 10;

  constructor(private http : HttpClient,farmerGovtDepartmentService:FarmerGovtDepartmentService){}

  httpOptions = {
    headers : new HttpHeaders({
      'Content-Type': 'application/json'
    })

  }

      // POST
      CreateGovtOfficialDesignation(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }  
    
      // GET By ID
      GetGovtOfficialDesignation(id): Observable<FarmerGovtOfficialDesignation> {
        return this.http.get<FarmerGovtOfficialDesignation>(this.baseUrl + '/' + id)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
    
      // GET
      GetAllGovtOfficialDesignation(isValid: number): Observable<FarmerGovtOfficialDesignation> {
        return this.http.get<FarmerGovtOfficialDesignation>(this.baseUrl+'/list'+ '?isValid=' + isValid)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
  
      //get department
      GetAllGovtOfficialDesignationByDepartment(departmentId):Observable<FarmerGovtOfficialDesignation>{
        return this.http.get<FarmerGovtOfficialDesignation>(this.baseUrl + '/department-id/' + departmentId)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
  
            //For-Pagination
    getPageFarmerGovtOfficialDesignation(page: number, rowSize: number, isValid: number,searchText): Observable<PageFarmerGovtOfficialDesignation> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText;
      return this.http.get<PageFarmerGovtOfficialDesignation>(url)
        .pipe(
          map(response => {
            const data = response;
            //console.log(data.content);
            return data;
      }));
    }
    
      // PUT
      UpdateGovtOfficialDesignation(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
    
      // DELETE
      DeleteGovtOfficialDesignation(id){
        return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
  
       // Reject
    RejectGovtOfficialDesignation(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
    //  Finalize
    FinalizeGovtOfficialDesignation(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
  
    // Approve
    ApproveGovtOfficialDesignation(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
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
