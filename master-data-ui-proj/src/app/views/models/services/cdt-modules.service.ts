import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import {Models} from '../model/Models';
import {ModelPage} from '../model/modelPage';

@Injectable({
    providedIn: 'root'
})
export class CdtModulesService {
    // baseUrl = environment.apiUrl + '/get/news/list';
    baseUrls = environment.apiUrl + '/modules';

    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };


    getModels(): Observable<Models> {
        return this.http.get<Models>(this.baseUrls + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //GET
getModelPagenatedList(page: number, rowSize: number, searchText): Observable<ModelPage> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<ModelPage>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }
  


    getAllManage(): Observable<any> {
        return this.http.get<any>(this.baseUrls + '/manage/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getPageWiseManageList(page: number, rowSize: number, searchText: string): Observable<ModelPage> {
        this.maxSize = rowSize || this.maxSize;
        return this.http.get<ModelPage>(this.baseUrls + '/manage' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );

    }
    getModelList(): Observable<any> {
        return this.http.get<any>(this.baseUrls + '/model/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    addManage(csvFile: any, data: any): Observable<any>{
        const formData: FormData = new FormData;

        formData.append('csvFile', csvFile);
        formData.append('data', JSON.stringify(data));
        const headers = new HttpHeaders({
            // 'Content-Type': 'multipart/form-data'
        });
        return this.http.post(this.baseUrls + '/manage/add' , formData , {headers});

    }
    addModel(csvFile: any, data: any): Observable<any>{
        const formData: FormData = new FormData;
        if (csvFile != undefined){
            formData.append('csvFile', csvFile);
        }
        formData.append('data', JSON.stringify(data));
        const headers = new HttpHeaders({
            // 'Content-Type': 'multipart/form-data'
        });
        return this.http.post(this.baseUrls + '/model/add' , formData , {headers});
    }
    deleteRecords(id){
        return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id , this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    getRecordById(id): Observable<any> {
        return this.http.get<any>(this.baseUrls + '/manage' + '/' + id)
            .pipe(
                catchError(this.errorHandl)
            );
    }
    getModelTemplate(id): Observable<any> {
        return this.http.get<any>(this.baseUrls + '/model/template?id=' + id)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    updateRecord(id, data, csvFile): Observable<ResponseMessage> {
        const formData: FormData = new FormData;

        formData.append('csvFile', csvFile);
        formData.append('data', JSON.stringify(data));
        const headers = new HttpHeaders({
            // 'Content-Type': 'multipart/form-data'
        });
        return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, formData, {headers})
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
}
