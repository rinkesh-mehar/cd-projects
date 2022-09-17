import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriYieldCorrectionCriteriaService } from '../../services/agri-yield-correction-criteria.service';


@Component({
  selector: 'app-agri-yield-correction-criteria',
  templateUrl: './agri-yield-correction-criteria.component.html',
  styleUrls: ['./agri-yield-correction-criteria.component.scss']
})
export class AgriYieldCorrectionCriteriaComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  YieldCorrectionCriteriaList: any = [];


  ngOnInit() {
    this.loadAllAgriYieldCorrectionCriteria();
  }

  constructor(
    private agriYieldCorrectionCriteriaService: AgriYieldCorrectionCriteriaService,
    private userRightsService: UserRightsService,
  ){ }

   // loadAllAgriYieldCorrectionCriteria list
   loadAllAgriYieldCorrectionCriteria() {
    return this.agriYieldCorrectionCriteriaService.GetAllYieldCorrectionCriteria().subscribe((data: any) => {
      this.YieldCorrectionCriteriaList = data;
    })
  }

    // // Delete AgriYieldCorrectionCriteria
    // deleteAgriYieldCorrectionCriteria(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriYieldCorrectionCriteriaService.DeleteAgriYieldCorrectionCriteria(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

      // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.YieldCorrectionCriteria, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.YieldCorrectionCriteria, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.YieldCorrectionCriteria, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.YieldCorrectionCriteria, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriYieldCorrectionCriteriaService.ApproveYieldCorrectionCriteria(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriYieldCorrectionCriteriaService.FinalizeYieldCorrectionCriteria(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriYieldCorrectionCriteriaService.DeleteYieldCorrectionCriteria(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriYieldCorrectionCriteriaService.RejectYieldCorrectionCriteria(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            setTimeout(() => {
              this.isSubmitted = false;
              this.isSuccess = false;
              this._statusMsg = "";
            }, 4000);
          } else {
            this._statusMsg = res.error;
          }
        }
      }, err => {
        this._statusMsg = err.error;
      })
    }

  }


}
