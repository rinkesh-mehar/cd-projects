import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { PageBuyerPreApplication } from '../models/page-buyer-pre-application';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class BuyerPreApplicationService {

  baseUrls = environment.apiUrl + '/site';
  authKey;

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

// httpOptionsForExcel = {
//   headers: new HttpHeaders({
      // "Content-disposition": "attachment; filename=Pre-Application.xlsx",
      // "Content-Type": "application/json",
      // "Transfer-Encoding": "chunked",
      // "Keep-Alive": "timeout=60",
      // "Connection": "keep-alive"
  
      // observe: 'response',
  // responseType: 'blob',
  //  'Content-Type': 'application/vnd.ms-excel',
  // 'Accept': 'text/csv',
  // 'Content-disposition': 'attachment'
  // responseType: 'blob'
//   })
// };

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

getPageBuyerPreApplicationList(page: number, rowSize: number, searchText): Observable<PageBuyerPreApplication> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/buyer-pre-application/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageBuyerPreApplication>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

// GET
getApplicantTypeList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/applicant-type-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getApplicationTypeList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/application-type-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getBusinessTypeList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/bussiness-type-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getFirmTypeList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/firm-type-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getCommodityList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/commodity-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getCurrencyList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/currency-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getDesignationList(): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application-masters/designation-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//GET
getBuyerPreApplicationById(id): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

//GET
getBuyerPreApplicationByIdForViewMode(id): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/find-by-id-for-view-mode' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

//PUT
updateBuyerPreApplication(id: any, data: any,udyogAadharImgFile: File,cinImgFile: File,panImgFile: File,gstCertificateImgFile: File,tanImgFile: File): Observable<ResponseMessage> {

  console.log("udyogAadharImageFile : " + udyogAadharImgFile + " panImgFile : " + panImgFile + " gstCertificateImgFile : " + gstCertificateImgFile);

  const formData: FormData = new FormData;
    if (udyogAadharImgFile != undefined) {
      formData.append('udyogAadharImgFile', udyogAadharImgFile);
    }
    if (cinImgFile != undefined) {
      formData.append('cinImgFile', cinImgFile);
    }
    if (panImgFile != undefined) {
      formData.append('panImgFile', panImgFile);
    }
    if (gstCertificateImgFile != undefined) {
      formData.append('gstCertificateImgFile', gstCertificateImgFile);
    }
    if (tanImgFile != undefined) {
      formData.append('tanImgFile', tanImgFile);
    }
  
    formData.append('data', JSON.stringify(data));

  return this.http.put<ResponseMessage>(this.baseUrls + '/buyer-pre-application/update/' + id, formData)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
listOfPageNoOfPreApplication(): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/total-page-no')
      .pipe(
          catchError(this.errorHandl)
      );
}

exportToExcelPreApplication(page): Observable<any> {
  console.log("inside exportToExcelPreApplication");
  // window.location.href = this.baseUrls + '/buyer-pre-application/download'  + '?page=' + page;
  // this.authKey = sessionStorage.getItem('token');

  // const httpOptionsForExcel = {
  //   responseType: 'blob' as 'json'
  // };
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/download'  + '?page=' + page,{responseType: 'blob' as 'json'})
  .pipe(
    catchError(this.errorHandl)
    );
  }

//GET
getCommodityIdsByApplicantApplicationId(applicantApplicationId): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/commodity-ids/' + applicantApplicationId)
      .pipe(
          catchError(this.errorHandl)
      );
}

//GET
getApplicantApplicationIdByApplicantId(applicantId): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/buyer-pre-application/applicant-Application-id/' + applicantId)
      .pipe(
          catchError(this.errorHandl)
      );
}

getSignatoryListByApplicantId(applicantId): Observable<any> {
  return this.http.get(this.baseUrls + '/buyer-pre-application/getSignatoryListByApplicantId/' + applicantId)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// PUT
deleteApplicant(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/buyer-pre-application/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
convertApplicanyToFrenchies(applicantId): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/buyer-pre-application/convert-to-franchise/' + applicantId  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
