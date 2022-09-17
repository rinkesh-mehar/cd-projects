import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-custome-error',
  templateUrl: './custome-error.component.html',
})
export class CustomeErrorComponent implements OnInit {
  error: any;

  constructor( private actRoute: ActivatedRoute,private location: Location) { }

  ngOnInit() {
    this.error = JSON.parse(this.actRoute.snapshot.paramMap.get('id'));

  }

  cancel() {
    this.location.back(); // <-- go back to previous location on cancel
  }

}
