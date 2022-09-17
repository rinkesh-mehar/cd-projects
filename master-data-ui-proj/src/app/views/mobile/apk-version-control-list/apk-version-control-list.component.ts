import { Component, OnInit, ViewChild } from '@angular/core';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApkVersionService } from '../../mobile/services/apk-version.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import {VersionList} from './pagination/versionList';

@Component({
  selector: 'app-apk-version-control-list',
  templateUrl: './apk-version-control-list.component.html',
  styleUrls: ['./apk-version-control-list.component.css']
})
export class ApkVersionControlListComponent implements OnInit {

    searchText: any = '';
    appList: any = [];
    selectedPage: number;
    maxSize: number = 10;
    pageVersion: VersionList;

  constructor(public apkVersionService: ApkVersionService) { }

  ngOnInit() {
    this.loadAllApp();
    this.getVersion(0);
  }
    getVersion(page: number): void {
        this.apkVersionService.getVersionWithPage(page, this.searchText)
            .subscribe(page => this.pageVersion = page);
    }

  loadAllApp() {
    return this.apkVersionService.getAllApp().subscribe((data: {}) => {
        this.appList = data;
        console.log(this.appList);
    });
}
    searchVersion() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getVersion(this.selectedPage - 1);
    }
    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getVersion(page);
    }

// searchNews() {
//     this.selectedPage = 1;
//     console.log(this.searchText);
//     this.getPageNews(this.selectedPage - 1);
// }


  


}
