import { Component, OnInit, OnDestroy, EventEmitter, ViewChild, ElementRef} from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SessionExpireModalComponent } from './components/modal-components/session-expire-modal/session-expire-modal.component'
import { fromEvent, Observable, Subscription } from 'rxjs';
import { Router, NavigationEnd } from '@angular/router'
import {  HttpClient } from '@angular/common/http'
//import { AppConstants } from './components/constants'
import { TokenService } from './components/modal-components/session-expire-modal/token-service'
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})

export class AppComponent implements OnInit, OnDestroy {
  @ViewChild('divClick') divClick: ElementRef;

  title = 'Dr Krishi';

  onlineEvent: Observable<Event>;
  offlineEvent: Observable<Event>;
  subscriptions: Subscription[] = [];
  connectionStatusMessage: string;
  connectionStatus: string;
  modalRef:BsModalRef;
  previousUrl: string;

  constructor(
    private modalService: BsModalService, 
    private router:Router,
    private http:HttpClient,
    private tokenService:TokenService
    ) {
      router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.previousUrl = event.url;
        
      });

    }

  isModalVisible = false

  ngOnInit() {
    if(this.previousUrl == undefined){
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigate(['/'])      
    }
    setInterval(() => {
      if (localStorage.getItem("session") != null) {
        try {
          let session = JSON.parse(localStorage.getItem("session"))
          let cTime = new Date().getTime();
          if (session['st'] - 60000 < cTime) {
            if(!this.isModalVisible && this.router.url != '/') {
              this.isModalVisible = true
              this.modalRef = this.modalService.show(SessionExpireModalComponent, { backdrop: 'static', keyboard: false });
            } 
            if(session['st'] < cTime) {
              this.modalRef.hide()
              localStorage.clear();
              sessionStorage.clear();
              this.router.navigate(['/']);
              location.reload();
            }
          } else {
            this.isModalVisible = false
          }
          if(session['vt']-60000 < cTime && session['st']-60000 > cTime) {
            this.tokenService.getRefreshToken().subscribe(res => {
              let newSession = {
                at:res, 
                st:cTime+(1000*60*60),
                vt:cTime+(1000*60*60)
              }
              localStorage.setItem('session', JSON.stringify(newSession));
            });
          }
        } catch (e) {
          localStorage.clear();
          sessionStorage.clear();
        }
      }
    }, 20000)

    //Get the online/offline status from browser window

   this.onlineEvent = fromEvent(window, 'online');
   this.offlineEvent = fromEvent(window, 'offline');

   this.subscriptions.push(this.onlineEvent.subscribe(e => {
     this.connectionStatusMessage = 'Back Online';
     this.connectionStatus = 'online';
     setTimeout(() => {
      this.divClick.nativeElement.click();
      }, 10000);
   }));

   this.subscriptions.push(this.offlineEvent.subscribe(e => {
     this.connectionStatusMessage = 'No Internet Connection';
     this.connectionStatus = 'offline';
   }));


  }


  ngOnDestroy(): void {
    /**
    * Unsubscribe all subscriptions to avoid memory leak
    */
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  showConnectionStatus(){
    this.connectionStatus = 'connectionstatus-hide';
  }
}
