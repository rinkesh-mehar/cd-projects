import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {Commodity} from '../models/commodity';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import {PageCommodityStress} from '../models/PageCommodityStress';
import {MarketPrice} from '../models/marketPrice';

@Injectable({
    providedIn: 'root'
})

export class TickerService {

    // Base url
    baseUrl = environment.apiUrl + '/site/ticker';
    maxSize: number = 10;

    constructor(private httpClient: HttpClient) {

    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    addCommodityStress(data): Observable<ResponseMessage> {

        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/addCommodityStress', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Update Commodity Stress*/
    updateCommodityStress(id, data): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/updateCommodityStress?id=' + id, JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Delete Commodity Stress*/
    deleteCommodityStress(id): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/commodityStress/delete?id=' + id, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Delete market price */
    deleteMarketPrice(id): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/market-price/delete?id=' + id, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getCommodityStressList(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getCommodityStressList')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getMarketPriceList(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getMarketPriceList')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getAllCommodity(): Observable<Commodity> {
        return this.httpClient.get<Commodity>(this.baseUrl + '/getCommodities')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    getPhenophase(id: any): Observable<Commodity> {
        console.log('base url is ', this.baseUrl + '/getPhenophase?commodityId=' + id);
        return this.httpClient.get<Commodity>(this.baseUrl + '/getPhenophase?commodityId=' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    // get Stress by commodity id and phenophase id
    getStress(phenophaseId, commodityId): Observable<Commodity> {
        console.log('base url is ', this.baseUrl + '/getStress?commodityId=' + commodityId + '&phenophaseId=' + phenophaseId);

        return this.httpClient.get<Commodity>(this.baseUrl + '/getStress?commodityId=' + commodityId + '&phenophaseId=' + phenophaseId)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    // market price services
    getAllVarietyByCommodity(id: any): Observable<Commodity> {
        console.log('base url is ', this.baseUrl + '/getVarietiesByCommodity?commodityId=' + id);
        return this.httpClient.get<Commodity>(this.baseUrl + '/getVarietiesByCommodity?commodityId=' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    getAllState(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getAllState')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getAllDistrict(stateCode): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getDistrictByState?stateCode=' + stateCode)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    loadAllMarket(districtCode, stateCode): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getMarketName?stateCode=' + stateCode + '&districtCode=' + districtCode)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    getMarketPriceById(id): Observable<MarketPrice> {
        return this.httpClient.get<MarketPrice>(this.baseUrl + '/getMarketPriceById/' + id)
            .pipe(
                catchError(this.errorHandle)
            );
    }

    getCommodityStressById(id): Observable<Commodity> {
        return this.httpClient.get<Commodity>(this.baseUrl + '/getCommodityStressById/' + id)
            .pipe(
                catchError(this.errorHandle)
            );
    }

    // update market price
    updateMarketPrice(id, data): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/updateMarketPrice?id=' + id, JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    addMarketPrice(data): Observable<ResponseMessage> {
        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/addMarketPrice', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    //GET
getCommodityStressPagenatedList(page: number, rowSize: number, searchText): Observable<any> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/getCommodityStressListPaginated' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<any>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }

  //GET
getMarketPricePagenatedList(page: number, rowSize: number, searchText): Observable<any> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/getMarketPriceListPaginated' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<any>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
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
