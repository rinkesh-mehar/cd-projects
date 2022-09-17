import { Component, OnInit } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { BookingService } from '../service/booking.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit {

  bookingList: any = []
  warehouseId: any;
  constructor(public bookingService: BookingService,
    private userRightsService: UserRightsService, private actRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.warehouseId = this.actRoute.snapshot.paramMap.get('id');
    if (this.warehouseId) { }
    this.bookingService.getBookingList().subscribe(data => {
      this.bookingList = data.data;
    })
  }

  bookingDetails(deatails){

  }
}