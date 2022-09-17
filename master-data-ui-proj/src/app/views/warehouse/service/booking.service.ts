import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  baseUrl = environment.warehouseApiUrl + '/public';
  // private bookingDetails = new Subject<any>();
  private bookingDetails;
  constructor(private http: HttpClient) { }



  // GTE
  getBookingList(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/booking-list')
      .pipe(
        retry(1)
      )
  }
  getBookingDetails(): Observable<any> {
    // return this.bookingDetails.asObservable();
    return this.bookingDetails;
  }

  setBookingDetails(bookingDetails) {
    // this.bookingDetails.next(bookingDetails);
    this.bookingDetails=bookingDetails;
  }
}
