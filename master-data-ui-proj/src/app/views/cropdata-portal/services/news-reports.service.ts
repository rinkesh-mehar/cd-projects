import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {NewsReports} from '../models/newsReports';
import {catchError, map, retry} from 'rxjs/operators';
import {AgriDoseFactor} from '../../agri/models/AgriDoseFactor';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import {PageNewsReports} from '../models/pageNewsReports';


@Injectable({
    providedIn: 'root'
})
export class newsReportsService {

    // baseUrl = environment.apiUrl + '/get/news/list';
    baseUrls = environment.apiUrl + '/site';

    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    getAllNews(): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/news')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getPlatFormList(): Observable<any> {
        return this.http.get<any>(this.baseUrls + '/platform')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getPageNews(page: number, rowSize: number, searchText): Observable<PageNewsReports> {
        this.maxSize = rowSize || this.maxSize;
        let url = this.baseUrls + '/news/list' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageNewsReports>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }

    getPageReport(page: number, rowSize: number, searchText): Observable<PageNewsReports> {
        this.maxSize = rowSize || this.maxSize;
        const url = this.baseUrls + '/reports/list' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageNewsReports>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }

    getAllReport(): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/reports')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }
        getNewsById(id): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/news' + '/' + id)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    getReportById(id): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/reports' + '/' + id)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    createNews(data: any, imageFile: any): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
       if (imageFile != undefined){

           formData.append('image', imageFile);
       }
        formData.append('data', JSON.stringify(data));
        return this.http.post<ResponseMessage>(this.baseUrls + '/news', formData)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    createReport(data: any, uploadedFile: any): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        console.log("uploadedFile : " + uploadedFile);
       if (uploadedFile != undefined){

           formData.append('reportFile', uploadedFile);
       }
        formData.append('data', JSON.stringify(data));
        return this.http.post<ResponseMessage>(this.baseUrls + '/reports', formData)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    updateNews(id, data, imageFile): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        if (imageFile != undefined){

            formData.append('image', imageFile);
        }
        formData.append('data', JSON.stringify(data));
        return this.http.put<ResponseMessage>(this.baseUrls + '/news/' + id, formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }
    updateReport(id, data, uploadedFile): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        if (uploadedFile != undefined){

            formData.append('reportFile', uploadedFile);
        }
        formData.append('data', JSON.stringify(data));
        return this.http.put<ResponseMessage>(this.baseUrls  + '/reports/' + id, formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // PUT
    deactiveReport(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/deactive-report/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
    }
    // PUT
    activeReport(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/active-report/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
    }

     // PUT
     deactiveNews(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/deactive-news/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
    }
    // PUT
    activeNews(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/active-news/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
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

    cropdataPriorityNewsList(): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/cropdata-priority-news')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    updateCropdataPriorityNews(data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/update-cropdata-priority-news/', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
      }

    cropdataLatestNewsPriorityList(): Observable<NewsReports> {
        return this.http.get<NewsReports>(this.baseUrls + '/cropdata-latest-news-priority')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    updateCropdataLatestNewsPriority(data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/update-cropdata-latest-news-priority/', JSON.stringify(data),
            this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
      }
}
