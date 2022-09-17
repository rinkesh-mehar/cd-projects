import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import { PageOpportunities } from '../models/page-opportunities';

@Injectable({
  providedIn: 'root'
})
export class OppotunitiesService {

  baseUrls = environment.apiUrl + '/site';

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

getOpportunityList(): Observable<any> {
  return this.http.get(this.baseUrls + '/opportunities/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
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

//GET
getPageOpportunities(page: number, rowSize: number, searchText): Observable<PageOpportunities> {
  console.log('Inside getPageOpportunities of service..');
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/opportunities/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageOpportunities>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}


  // GET
  getPlatFormList(): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/platform')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
  }

  // GET
  getDepartmentList(): Observable<any> {
    return this.http.get(this.baseUrls + '/department/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

// GET
getPositionList(): Observable<any> {
  return this.http.get(this.baseUrls + '/position/list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

  // GET
  getEducationList(): Observable<any> {
    return this.http.get(this.baseUrls + '/education/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // GET
  getStateList(): Observable<any> {
    return this.http.get(environment.apiUrl + '/geo/state/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // GET
  getDistrictList(): Observable<any> {
    return this.http.get(environment.apiUrl + '/geo/district/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // POST
  addOpportunity(data): Observable<ResponseMessage> {

    console.log("Inside add opp service");

    return this.http.post<ResponseMessage>(this.baseUrls + '/opportunities/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // GET
   GetAllDistrictByStateCode(stateCode): Observable<any> {
    return this.http.get<any>(environment.apiUrl + '/geo/district/state-code/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
  getOpportunityById(id): Observable<any> {
    return this.http.get<any>(this.baseUrls + '/opportunities/id' + '/' + id)
        .pipe(
            catchError(this.errorHandl)
        );
}

updateOpportunity(id, data): Observable<ResponseMessage> {
  console.log('Inside updateOpportunity service');
  return this.http.put<ResponseMessage>(this.baseUrls + '/opportunities/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// CLOSE
closeOpportunity(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/opportunities/close/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// Active
activeOpportunity(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/opportunities/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//GET
getEducationIdsByOpportunityId(opportunityId): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/opportunities/education-ids/' + opportunityId)
      .pipe(
          catchError(this.errorHandl)
      );
}

}
