import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bc-ratio',
  templateUrl: './bc-ratio.component.html',
  styleUrls: ['./bc-ratio.component.scss']
})
export class BCRatioComponent implements OnInit {
  isShowDivIf = false;

  DisplayDivIf() {
    this.isShowDivIf = !this.isShowDivIf;
  }
  
  constructor() { }

  ngOnInit() {
    
  } 

}
