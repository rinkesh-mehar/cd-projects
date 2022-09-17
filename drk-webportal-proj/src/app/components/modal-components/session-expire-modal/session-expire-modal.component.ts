import { Component, OnInit } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { Router } from "@angular/router";
import { TokenService } from './token-service'

@Component({
  selector: 'app-session-expire-modal',
  templateUrl: './session-expire-modal.component.html',
  styleUrls: ['./session-expire-modal.component.less']
})
export class SessionExpireModalComponent implements OnInit {

  constructor(
    private bsModalRef: BsModalRef,
    private router: Router,
    private tokenService:TokenService,
    private bsModalService: BsModalService
  ) { }

  ngOnInit() {
  }

  stay() {
    this.bsModalRef.hide();
    this.tokenService.getRefreshToken().subscribe(res => {
      let cTime = new Date().getTime()
      let newSession = {
        at:res, 
        st:cTime+(1000*60*60),
        vt:cTime+(1000*60*60)
      }
      localStorage.setItem('session', JSON.stringify(newSession));
    });
  }

  close() {    
    this.bsModalRef.hide();
    this.router.navigate(['/'])
    localStorage.clear();
    sessionStorage.clear();

  }

}
