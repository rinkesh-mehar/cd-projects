import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../geo/models/ResponseMessage';
import {RlUserpage} from '../../manage-rl/model/rl-userpage';

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {
    baseUrl = environment.logisticHubApiUrl + '/logisticHub/emp';

    constructor(private httpClient: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'content-type': 'application/json'
        })
    };
    maxSize: number = 10;
    // getEmployeeList()

    getRegion(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/geo-region')
            .pipe(retry(1),
            catchError(this.errorHandl));
    }

    getActiveLhDrkUser(regionId): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/active-lh-drkUser' + '/' + regionId)
            .pipe(retry(1),
            catchError(this.errorHandl));
    }

   /* getDrkUser(regionId): Observable<any>{
        return this.httpClient.get<any>(this.baseUrl + '/drk-user/' + regionId)
            .pipe(retry(1),
            catchError(this.errorHandl));
    }*/

   storeDrkEmployee(employeeDetails): Observable<any>{
       return this.httpClient.post(this.baseUrl + '/drk-employee-details',
           JSON.stringify(employeeDetails), this.httpOptions)
           .pipe(retry(1),
               catchError(this.errorHandl));
   }

    storeExtEmployeeDetails(data: any, bankData: any, userImage: File, aadharImage: File, panImage: File, drivingImage: File, accountImage: File): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        if (userImage != undefined) {
            formData.append('userProfileImage', userImage);
        }
        if (aadharImage != undefined) {
            formData.append('userAadharImage', aadharImage);
        }
        if (panImage != undefined) {
            formData.append('userPanImage', panImage);
        }
        if (drivingImage != undefined) {
            formData.append('userDLImage', drivingImage);
        }
        if (accountImage != undefined) {
            formData.append('accountImage', accountImage);
        }
        formData.append('data', JSON.stringify(data));
        formData.append('bankDetails', JSON.stringify(bankData));
        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/ext-employee-details/' , formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    updateDrkEmployee(id, employeeDetails): Observable<any>{
        return this.httpClient.post(this.baseUrl + '/update/drk-employee/' + id,
            JSON.stringify(employeeDetails), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    updateExtEmployeeDetails(id: any, data: any, bankData: any, userImage: File, aadharImage: File, panImage: File, drivingImage: File, accountImage: File): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        if (userImage != undefined) {
            formData.append('userProfileImage', userImage);
        }
        if (aadharImage != undefined) {
            formData.append('userAadharImage', aadharImage);
        }
        if (panImage != undefined) {
            formData.append('userPanImage', panImage);
        }
        if (drivingImage != undefined) {
            formData.append('userDLImage', drivingImage);
        }
        if (accountImage != undefined) {
            formData.append('accountImage', accountImage);
        }
        formData.append('data', JSON.stringify(data));
        formData.append('bankDetails', JSON.stringify(bankData));
        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/update/ext-employee/' + id, formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }
   getLhRole(): Observable<any>{
       return this.httpClient.get<any>(this.baseUrl + '/lh-role/')
           .pipe(retry(1),
           catchError(this.errorHandl));
   }
   getLhEmployeeById(id): Observable<any>{
       return this.httpClient.get<any>(this.baseUrl + '/lh-employee' + '/' + id)
           .pipe(retry(1),
               catchError(this.errorHandl));
   }
    getAssignLhUser(page: number, rowSize: number, searchText: string): Observable<RlUserpage> {
        this.maxSize = rowSize || this.maxSize;
        let url = this.baseUrl + '/getAssign-lhUser' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.httpClient.get<RlUserpage>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
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