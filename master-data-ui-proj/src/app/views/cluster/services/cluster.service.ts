import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../../../environments/environment';


import {catchError, map, retry} from 'rxjs/operators';
import {GeoState} from '../../geo/models/GeoState';
import {SourcePage} from '../models/source-page';



@Injectable({
    providedIn: 'root'
})
export class ClusterService {

    maxSize = 10;
    constructor(private http: HttpClient) {}

    private geoCountry = environment.apiUrl + '/geo/country';
    private _geoState = environment.apiUrl + '/geo/state';
    private _getDetails    = environment.apiUrl + '/get-details?regionId=';
    private _genrateRegion = environment.apiUrl + '/generate-region?regionId=';
    private _getList       = environment.apiUrl + '/get-list' ;
    private _regionAction  = environment.apiUrl + '/region-action';
    private _state         = environment.apiUrl + '/region-state';
    private _district      = environment.apiUrl + '/region-district';
    private  _imageID = environment.apiUrl + '/region-image';
    private _geoDistrict = environment.apiUrl + '/geo/district/';

    /*implemented source on-boarding end-point*/
    private source = environment.apiUrl + '/source';

    getList(): Observable<number> {
        const headers = new HttpHeaders({ 'content-type' : 'application/text'});
        return this.http.get<any>(this._getList, {headers});
    }

    getDetails(regionId: any, _data: any ): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return this.http.post(this._getDetails + regionId, JSON.stringify(_data), { headers} )
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }
    saveRegion(regionId: any, _data: any ): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return this.http.post(this._genrateRegion + regionId , JSON.stringify(_data), { headers} );
    }
    getData(_data: any , csvFile: any, image: any, shapeFile: any): Observable<any> {

        // console.log(file.length);
        const formData: FormData = new FormData;

        if (csvFile != undefined) {

            formData.append('csvFile', csvFile);
        }
        if (image != undefined) {

            formData.append('image', image);
        }
        if (shapeFile != undefined) {
            formData.append('shapeFile', shapeFile);
        }


        // console.log(file)
        // if (file) {
        // formData.append("myFile", file);
        // }
        formData.append('data', JSON.stringify(_data));

        const headers = new HttpHeaders({});
        return this.http.post(this._regionAction , formData , {headers});
    }  
   
    getState(): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return this.http.get(this._state ,  { headers} );
    }

    getDistrict(): Observable<any> {
        const  headers = new HttpHeaders({'content-type' : 'application/json'});
        return this.http.get(this._district, {headers});
    }
    // GET
    GetAllState(): Observable<GeoState> {
        return this.http.get<GeoState>(this._geoState + '/list');
    }

    getAllDistrict(stateCode): Observable<any> {
        return this.http.get<any>( this._geoDistrict + 'state-code' + '/' + stateCode);
    }
    getAllState(): Observable<any> {
        return this.http.get<any>(this._state + '/manage/list')
            .pipe(
                retry(1),
            );
    }

    getImage(_data): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return this.http.get(this._imageID + '/' + _data ,  { headers} );
    }

    /**implemented source onboard process and connection to regions -Start*/

    getSourceList(page: number, size: number, searchText): Observable<SourcePage> {
        this.maxSize = size || this.maxSize;
        return this.http.get<SourcePage>(this.source + '/list' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText)
            .pipe(
                map(response => {
                    return response;
                }));
    }

    getSources(): Observable<any> {
        return this.http.get<any>(this.source + '/cluster/list')
            .pipe(
                map(response => {
                    return response;
                })
            );
    }

    getCountryList(): Observable<any> {
        const headers = new HttpHeaders({'content-type': 'application/text'});
        return this.http.get<any>(this.geoCountry + '/list', {headers});
    }

    getStateListByCountryCode(countryCode: any): Observable<any> {
        const headers = new HttpHeaders({'content-type': 'application/text'});
        return this.http.get<any>(this._geoState + '/list/' + countryCode, {headers});
    }

    storeSourceData(source): Observable<any> {
        const headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post(this.source + '/store', JSON.stringify(source.value), {headers})
            .pipe(
                map(response => {
                    return response;
                }));
    }

    getSourceById(id): Observable<any> {
        const headers = new HttpHeaders({'content-type': 'application/json'});
        return this.http.get<any>(this.source + '/' + id, {headers});
    }

    updateSourceDetails(id: any, sourceDetails: any): Observable<any> {
        const headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post(this.source + '/update/' + id, JSON.stringify(sourceDetails.value), {headers})
            .pipe(
                map(response => {
                    return response;
                })
            );
    }
// Error handling
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
    /**implemented source onboard process and connection to regions -End*/
}
