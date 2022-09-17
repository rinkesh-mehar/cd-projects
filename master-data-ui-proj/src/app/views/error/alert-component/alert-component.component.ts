import { Component, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../alert.service';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-alert-component',
  templateUrl: './alert-component.component.html',
  styleUrls: ['./alert-component.component.scss']
})
export class AlertComponentComponent implements OnInit {
  private subscription: Subscription;
  message: any;

  @ViewChild('confirmModal') public confirmModal: ModalDirective;


  constructor(private alertService: AlertService) { }
  title: "Error"
  ngOnInit() {
    this.subscription = this.alertService.getAlert()
      .subscribe(message => {
        switch (message && message.type) {
          case 'success':
            message.cssClass = 'modal-success';
            // message.cssClass = 'alert alert-success';
            break;
          case 'error':
            message.cssClass = 'modal-danger';
            // message.cssClass = 'alert alert-danger';
            break;
        }
        this.message = message;
        if (this.message) {
          this.confirmModal.show()
        } else {
          this.confirmModal.hide();
        }
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
