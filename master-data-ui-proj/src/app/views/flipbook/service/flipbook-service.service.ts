import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import {catchError, retry} from 'rxjs/operators';
import {throwError} from 'rxjs/internal/observable/throwError';


@Injectable({
  providedIn: 'root'
})
export class FlipbookServiceService {
    constructor(private restApi: HttpClient) {}
      private _dropDownURL           = environment.flipbookApiURL + '/get-dropdown';
      private _getImages             = environment.flipbookApiURL + '/get-image';
      private _tagImages             = environment.flipbookApiURL + '/tag-image';
      private _unTagImages           = environment.flipbookApiURL + '/un-tag-image';
      private _deleteSymptom         = environment.flipbookApiURL + '/remove-symptom';
      private _tagImagesToSymptom    = environment.flipbookApiURL + '/tag-image-to-symptom';
      private _editSymptomDescription    = environment.flipbookApiURL + '/edit_symptom_description';
      private _tagGenericImage       = environment.flipbookApiURL + '/is-generic';
      private _getStress             = environment.flipbookApiURL + '/get-stress';

    getDropDownData(): Observable<any> {
      const headers = new HttpHeaders({ 'content-type' : 'application/text'});
      return  this.restApi.get(this._dropDownURL, {headers});
    }

    getStressData(cmd: number, strtyp: number , phn: number , strs: number , plntprt: number, flg: number): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/text'});
        return  this.restApi.get(this._getStress + '/' + cmd + '/' + strtyp + '/' + phn + '/' + strs + '/' + plntprt + '/' + flg,  {headers});
    }

    getImages(_data: any): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.post(this._getImages , JSON.stringify(_data), {headers});
    }
    tagImages(_data: any): Observable<any> {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.post(this._tagImages, JSON.stringify(_data), {headers});
    }

    untagImage(_name: String, _qId: any) {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.delete(this._unTagImages + '/' + _qId + '/' + _name, { headers });
    }

    removeSymptom(_name: String, _qId: any) {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.delete(this._deleteSymptom + '/' + _qId + '/' + _name, { headers });
    }

    tagToSypmtom(_data: any) {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.post(this._tagImagesToSymptom, JSON.stringify(_data), {headers});
    }

    tagGenericImage(_name: String, _qId: any) {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.get(this._tagGenericImage + '/' + _qId + '?imageName=' + _name, { headers });
    }

    uploadZipFile(file) {
        const formData: FormData = new FormData;
        if (file) {
            formData.append('zipFile', file);
        }

        return this.restApi.post<ResponseMessage>(environment.flipbookApiURL + '/upload-bm-image', formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
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

    editSymptom(_data: any) {
        const headers = new HttpHeaders({ 'content-type' : 'application/json'});
        return  this.restApi.post(this._editSymptomDescription, JSON.stringify(_data), {headers});
    }
}
