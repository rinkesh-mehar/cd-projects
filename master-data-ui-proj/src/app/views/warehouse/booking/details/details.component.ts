import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../service/booking.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {
  booking: any = {};
  bookingTest: any = {};

  constructor(public bookingService: BookingService, ) { }

  ngOnInit() {
    this.booking = this.bookingService.getBookingDetails();
  }

}
