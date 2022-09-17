import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { VirtualMachinesConfiguration } from '../models/virtual-machines-configuration';
import { PageVirtualMachinesConfiguration } from '../models/page-virtual-machines-configuration';

@Injectable({
  providedIn: 'root'
})
export class VirtualMachinesConfigurationService {

// baseUrls = environment.apiUrl + '/deployments';
baseUrls = environment.azureDevopsUrl + '/virtual-machines-configuration';

maxSize: number = 10;
constructor(private http: HttpClient) { }

httpOptions = {
  headers: new HttpHeaders({
      'Content-Type': 'application/json'
  })
};

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


// POST
addVirtualMachinesConfiguration(data): Observable<ResponseMessage> {

// console.log("Inside add opp service");

return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//GET
getVirtualMachinesConfigurationById(id): Observable<VirtualMachinesConfiguration> {
return this.http.get<VirtualMachinesConfiguration>(this.baseUrls + '/id' + '/' + id)
    .pipe(
        catchError(this.errorHandl)
    );
}


//PUT
updateVirtualMachinesConfiguration(id, data): Observable<ResponseMessage> {
return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
    .pipe(
        retry(1),
        catchError(this.errorHandl)
    );
}

//GET
getVirtualMachineConfigurationListByPagenation(page: number, rowSize: number, searchText): Observable<PageVirtualMachinesConfiguration> {
this.maxSize = rowSize || this.maxSize;
let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
return this.http.get<PageVirtualMachinesConfiguration>(url)
    .pipe(
        map(response => {
            const data = response;
            return data;
        }));
}

}
