import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';
import {PageValidateStress} from '../models/PageValidateStress';
import {ResponseMessage} from '../models/ResponseMessage';
import {ValidateStress} from '../models/ValidateStress';
import {PageRegionTerrain} from '../../regional/models/PageTerrain';
import {StressDetail} from '../models/StressDetail';

@Injectable({
    providedIn: 'root'
})
export class ValidateStressService {


    baseUrl = environment.apiUrl + '/farmer/stress-approval';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };


//Get Paginated List
    getValidateStressPagenatedList(page: number, rowSize: number, searchText: any): Observable<PageValidateStress> {
        this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + '/paginated-list' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageValidateStress>(url)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }


// GetAllValidateStress(): Observable<ValidateStress> {
//   return this.http.get<ValidateStress>(this.baseUrl + '/list')
//   .pipe(
//     retry(1),
//     catchError(this.errorHandl)
//   );
// }


// GET By ID
    getValidateStressById(id): Observable<ValidateStress> {
        return this.http.get<ValidateStress>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

// GET Case Details By ID
    getValidateStressCaseDetails(id): Observable<ValidateStress> {
        return this.http.get<ValidateStress>(this.baseUrl + '/case-details' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

// GET By ID
    getValidateStressDetails(id): Observable<ValidateStress> {
        return this.http.get<ValidateStress>(this.baseUrl + '/stress-details' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    // Reject
    RejectActivity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    //  Finalize
    FinalizeActivity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    // Approve
    ApproveActivity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }
   

    getDetailsByTaskSterssDetailID(taskStressDetailID: number): Observable<StressDetail> {
        console.log('url is -> {}', this.baseUrl + '/details?taskStressDetailID=' + taskStressDetailID);
        const url = this.baseUrl + '/details?taskStressDetailID=' + taskStressDetailID;
        return this.http.get<StressDetail>(url)
            .pipe(
                map(response => {
                    console.log('data form validate service is ', response);
                    return response;
                }));
    }

    getSymptomBySpec(commodityID: number, varietyID: number, phenophaseID: number, districtID: number) {
        return this.http.get<StressDetail>(this.baseUrl + '/symptoms?commodityID=' + commodityID + '&varietyID=' + varietyID +
            '&phenophaseID=' + phenophaseID + '&districtID=' + districtID)
            .pipe(
                map(response => {
                    console.log('getSymptomBySpec response is ', response);
                    return response;
                })
            );
    }

    getSymptomDetailsBySymptom(symptomID: number) {
        return this.http.get<StressDetail>(this.baseUrl + '/symptoms-details?symptomID=' + symptomID)
            .pipe(
                map(response => {
                    console.log('getSymptomBySpec response is ', response);
                    return response;
                })
            );
    }


    approveSymptom(data: any) {

        // const data: any = {
        //     "id": id,
        //     "symptomID": symptomID,
        //     "approvalStatus": flag =='A'? 'true': 'false'
        // }

        return this.http.post<ResponseMessage>(this.baseUrl + '/approve-symptoms', data, this.httpOptions)
            .pipe(
                map(response => {
                    console.log('approveSymptom response is', response);
                    return response;
                })
            );
    }
    
    // Error handling
    errorHandle(error) {
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
