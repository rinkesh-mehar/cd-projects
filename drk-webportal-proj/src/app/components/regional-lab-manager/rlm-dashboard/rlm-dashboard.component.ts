import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";

@Component({
  selector: 'app-rlm-dashboard',
  templateUrl: './rlm-dashboard.component.html',
  styleUrls: ['./rlm-dashboard.component.less']
})
export class RlmDashboardComponent implements OnInit {

  constructor(private router: Router,) { }

  ngOnInit() {
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
  }

}
