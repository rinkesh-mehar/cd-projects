import { Injectable, ViewChild } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';

@Injectable({
  providedIn: 'root'
})
export class PhysicalVerificationImagesUploadService {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  baseUrl = environment.logisticHubApiUrl + '/logisticHub';

  fileToUpload: File = null;

  constructor(private httpClient: HttpClient) { }
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  addLogisticHubImages(hubId: any, WMS_007Image: File, WMS_009Image: File, WMS_015Image: File, WMS_031Image: File, WMS_041Image: File, WMS_043Image: File,
    WMS_045Image: File, WMS_047Image: File, WMS_051Image: File, WMS_075Image: File, WMS_076Image: File, WMS_077Image: File,
    WMS_083Image: File, WMS_085Image: File, WMS_086Image: File, WMS_087Image: File, WMS_089Image: File, WMS_090Image: File, WMS_097Image: File, WMS_098Image: File, WMS_070Image: File): Observable<any>  {
    const formData: FormData = new FormData;
    console.log('In service');

    if (WMS_007Image != undefined &&  WMS_007Image !== null) {
      formData.append('WMS_007', WMS_007Image);
    }
    if (WMS_009Image != undefined &&  WMS_009Image !== null) {
      formData.append('WMS_009', WMS_009Image);
    }
    if (WMS_015Image != undefined &&  WMS_015Image !== null) {
      formData.append('WMS_015', WMS_015Image);
    }
    if (WMS_031Image != undefined && WMS_031Image !== null) {
      formData.append('WMS_031', WMS_031Image);
    }
    if (WMS_041Image != undefined &&  WMS_041Image !== null) {
      formData.append('WMS_041', WMS_041Image);
    }
    if (WMS_043Image != undefined &&  WMS_043Image !== null) {
      formData.append('WMS_043', WMS_043Image);
    }
    if (WMS_045Image != undefined &&  WMS_045Image !== null) {
      formData.append('WMS_045', WMS_045Image);
    }
    if (WMS_047Image != undefined &&  WMS_047Image !== null) {
      formData.append('WMS_047', WMS_047Image);
    }
    if (WMS_051Image != undefined &&  WMS_051Image !== null) {
      formData.append('WMS_051', WMS_051Image);
    }
    if (WMS_075Image != undefined &&  WMS_075Image !== null) {
      formData.append('WMS_075', WMS_075Image);
    }
    if (WMS_076Image != undefined &&  WMS_076Image !== null) {
      formData.append('WMS_076', WMS_076Image);
    }
    if (WMS_077Image != undefined && WMS_077Image !== null) {
      formData.append('WMS_077', WMS_077Image);
    }
    if (WMS_083Image != undefined &&  WMS_083Image !== null) {
      formData.append('WMS_083', WMS_083Image);
    }
    if (WMS_085Image != undefined && WMS_085Image !== null) {
      formData.append('WMS_085', WMS_085Image);
    }
    if (WMS_086Image != undefined && WMS_086Image !== null) {
      formData.append('WMS_086', WMS_086Image);
    }
    if (WMS_087Image != undefined &&  WMS_087Image !== null) {
      formData.append('WMS_087', WMS_087Image);
    }
    if (WMS_089Image != undefined && WMS_089Image !== null) {
      formData.append('WMS_089', WMS_089Image);
    }
    if (WMS_097Image != undefined &&  WMS_097Image !== null) {
      formData.append('WMS_097', WMS_097Image);
    }
    if (WMS_098Image != undefined &&  WMS_098Image !== null) {
      formData.append('WMS_098', WMS_098Image);
    }
    if (WMS_070Image != undefined &&  WMS_070Image !== null) {
      formData.append('WMS_070Image', WMS_070Image);
    }

    return this.httpClient.post<any>(this.baseUrl + '/uploadLogisticHubImages/' + hubId, formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  uploadLogisticHubImages(selectedFile: File[], hubId: any, metaExcerptDesc: string) {
    const formData: FormData = new FormData;
    formData.append('metaExcerptDescription', metaExcerptDesc);
    formData.append('picture1', selectedFile[0]);
    formData.append('picture2', selectedFile[1]);
    formData.append('picture3', selectedFile[2]);
    console.log(JSON.stringify(formData));
    return this.httpClient.post<any>(this.baseUrl + '/uploadLogisticHubImagesOneByOne/' + hubId, formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );

  }

  uploadOtherImages(selectedFile: File[], hubId: any, metaExcerptDesc: string) {
    const formData: FormData = new FormData;
    formData.append('metaExcerptDescription', metaExcerptDesc);
    formData.append('picture1', selectedFile[0]);
    formData.append('picture2', selectedFile[1]);
    formData.append('picture3', selectedFile[2]);
    console.log(JSON.stringify(formData));
    return this.httpClient.post<any>(this.baseUrl + '/uploadOtherImagesOneByOne/' + hubId + '/' + metaExcerptDesc, formData)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );

  }

  updateStagePhysicalVerificationComplete(hubId: any): Observable<any> {
    console.log(' I am in updateStagePhysicalVerificationComplete');
    console.log('hub id ' + hubId);
    const formData: FormData = new FormData;
    formData.append('hubID', hubId);
    return this.httpClient.post<any>(this.baseUrl + '/updateStagePhysicalVerificationComplete/' + hubId, this.httpOptions)
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

  getOtherLhMetaData(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + '/otherLh-data')
        .pipe(retry(1),
            catchError(this.errorHandl));
  }

}
