import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {ClusterService} from '../services/cluster.service';
import {SourcePage} from '../models/source-page';
import {globalConstants} from '../../global/globalConstants';
import {UserRightsService} from '../../services/user-rights.service';

@Component({
  selector: 'app-source-onboard',
  templateUrl: './source-onboard.component.html',
  styleUrls: ['./source-onboard.component.scss']
})
export class SourceOnboardComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('confirmModal', {static: false}) public confirmModal: ConfirmationMadalComponent;

  searchText: any = '';
  sourceList: SourcePage;
  selectedPage: number = 1;
  size: number = 10;
  sourceStatus;

  constructor(private clusterService: ClusterService,private userRightsService: UserRightsService) { }

  ngOnInit(): void {
    this.sourceStatus = globalConstants;
    this.getSourceList(0);
  }

  searchNews() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getSourceList(this.selectedPage - 1);
  }

  getSourceList(page: number): void{
    this.clusterService.getSourceList(page, this.size, this.searchText).subscribe(
        page => this.sourceList = page);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getSourceList(page);
  }
}
