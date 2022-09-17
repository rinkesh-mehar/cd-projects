import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';
import {ResponseMessage} from '../../general/models/ResponseMessage';
import {AgriCommodityService} from '../../agri/services/agri-commodity.service';
import {PricingMspGroup} from '../models/PricingMspGroup';
import {GeoState} from '../../geo/models/GeoState';
import {Pricing} from '../model/pricing';
import {PricingPage} from '../model/pricing-page';
import {PageBuyerConstant} from '../models/PageBuyerConstant';

@Injectable({
    providedIn: 'root'
})
export class PricingMspGroupService {
    public getData = [];
// Base url
    baseUrl = environment.apiUrl + '/pricing/msp-group';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

// Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

// POST
    addPrice(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// GET By ID
    GetMspGroupByCommodityStateMsp(id): Observable<PricingMspGroup> {
        return this.http.get<PricingMspGroup>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// GET
    GetAllMspGroup(): Observable<PricingMspGroup> {
        return this.http.get<PricingMspGroup>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// Setting data for edit for patch values
    storeData(data) {
        this.cleanStoredData();
        this.getData.push(data);
    }

    // Clearing data for refreshing page
    cleanStoredData() {
        this.getData = [];
    }

    GetAllMspGroupPaginated(page): Observable<PricingMspGroup> {
        return this.http.get<PricingMspGroup>(this.baseUrl + '/get-pricing-paginated')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//For-Pagination
//   getPagePricingMspGroup(page): Observable<any> {
//     return this.http.get<any>(this.baseUrl + '/get-pricing-paginated?page=' + page)
//         .pipe(
//             map(response => {
//               const data = response;
//               console.log(data.content);
//               return data;
//             }));
//   }

    getStateAndRegion(): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/get-state-region')
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    getMSPAndMFP(sCode, rId, cId, flag): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/msp-mfp?sId=' + sCode + '&rId=' + rId + '&cId=' + cId + '&flag=' + flag)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    getConstants(sCode, rId, cId, flag): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/constants?sId=' + sCode + '&rId=' + rId + '&cId=' + cId + '&flag=' + flag)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    getMbepAndPmp(sCode, rId, cId, flag): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/mbep-pmp?sId=' + sCode + '&rId=' + rId + '&cId=' + cId + '&flag=' + flag)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    getPriceSpread(sCode, rId, cId, flag): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/price-spread?sId=' + sCode + '&rId=' + rId + '&cId=' + cId + '&flag=' + flag)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    editMSP(data): Observable<any> {
        return this.http.post<any>(this.baseUrl + '/edit-msp', JSON.stringify(data), this.httpOptions)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    // get all active states
    loadActiveState(): Observable<PricingMspGroup> {
        return this.http.get<PricingMspGroup>('/geo/state/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//Get
    GetAllCommodities(): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/getCommodities')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    GetAllRegion(stateCode): Observable<GeoState> {
        return this.http.get<GeoState>(this.baseUrl + '/region?sId=' + stateCode)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    GetAllVariety(stateCode, commodityID): Observable<GeoState> {
        return this.http.get<GeoState>(this.baseUrl + '/variety?sId=' + stateCode + '&cId=' + commodityID)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// PUT
    UpdateMspGroup(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// DELETE
    DeleteMspGroup(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// Reject
    RejectMspGroup(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//  Finalize
    FinalizeMspGroup(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


// Approve
    ApproveMspGroup(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    listOfPageNumbers(screen: any) {
        return this.http.get<ResponseMessage>(this.baseUrl + '/page/' + screen, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getPageBuyerConstantPaginated(page: number, searchText): Observable<PageBuyerConstant> {
        const url = this.baseUrl + '/buyer-constant-paginated?page=' + page + '&size=' + environment.pageSize + '&searchText=' + searchText;
        return this.http.get<PageBuyerConstant>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }

    getBuyerConstant(): Observable<any>{
        return this.http.get<any>(this.baseUrl + '/buyer-constant')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getBuyerConstantById(id: any): Observable<any>{
        return this.http.get<any>(this.baseUrl + '/buyer-constant/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    addBuyerConstant(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add-buyer-constant', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    UpdateBuyerConstant(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/buyer-constant/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }
    exportMbepPmpSpreadToExcel(stateCode: number, regionId: number, commodityID: number) {
        console.log('state code ' + stateCode + ' region id ' + regionId + ' commodity id ' + commodityID);
        window.location.href = this.baseUrl + '/download-mbep-pmp?stateCode=' + stateCode + '&regionID=' + regionId + '&commodityID=' + commodityID;
    }

    exportConstantToExcel(stateCode: number, regionId: number, commodityID: number) {
        console.log('state code ' + stateCode + ' region id ' + regionId + ' commodity id ' + commodityID);
        window.location.href = this.baseUrl + '/download-constant?stateCode=' + stateCode + '&regionID=' + regionId + '&commodityID=' + commodityID;
    }

    exportMspMfpToExcel(stateCode: number, regionId: number, commodityID: number) {
        console.log('state code ' + stateCode + ' region id ' + regionId + ' commodity id ' + commodityID);
        window.location.href = this.baseUrl + '/download-msp-mfp?stateCode=' + stateCode + '&regionID=' + regionId + '&commodityID=' + commodityID;
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
}
