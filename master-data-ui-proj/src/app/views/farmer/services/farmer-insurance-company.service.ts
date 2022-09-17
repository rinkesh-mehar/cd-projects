import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerInsuranceCompany } from '../models/FarmerInsuranceCompany';
import { environment } from '../../../../environments/environment';
import { PageFarmerInsuranceCompany } from '../models/PageFarmerInsuranceCompany';
import { FarmerInsuranceService } from './farmer-insurance.service';

@Injectable({
  providedIn: 'root'
})
export class FarmerInsuranceCompanyService {

    // Base url
    baseUrl = environment.apiUrl+'/farmer/insurance-company';
    maxSize: number = 10;
 

    constructor(private http: HttpClient,private farmerInsuranceService : FarmerInsuranceService) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateInsuranceCompany(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetInsuranceCompany(id): Observable<FarmerInsuranceCompany> {
      return this.http.get<FarmerInsuranceCompany>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllInsuranceCompany(): Observable<FarmerInsuranceCompany> {
      return this.http.get<FarmerInsuranceCompany>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
    

    GetAllInsuranceCompanyByInsurance(insuranceTypeId):Observable<FarmerInsuranceCompany>{
      return this.http.get<FarmerInsuranceCompany>(this.baseUrl+ '/insuranceType-id/' + insuranceTypeId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
      //For-Pagination
  getPageFarmerInsuranceCompany(page: number,rowSize: number,searchText, isValid: number): Observable<PageFarmerInsuranceCompany> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText;
    return this.http.get<PageFarmerInsuranceCompany>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
    }));
  }
  
    // PUT
    UpdateInsuranceCompany(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteInsuranceCompany(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }


  // Reject
  RejectInsuranceCompany(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeInsuranceCompany(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveInsuranceCompany(id) {
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
