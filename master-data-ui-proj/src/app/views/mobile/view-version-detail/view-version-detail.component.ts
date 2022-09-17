import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApkVersionService } from '../services/apk-version.service';

@Component({
  selector: 'app-view-version-detail',
  templateUrl: './view-version-detail.component.html',
  styleUrls: ['./view-version-detail.component.css']
})
export class ViewVersionDetailComponent implements OnInit {

  viewId: string;
  viewApp: FormGroup;
  dataObj: any;

  constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
    public apkVersionService: ApkVersionService,  public router: Router) { }

  ngOnInit() {

    this.viewApp = this.formBuilder.group({
      appName: ['', Validators.required],
      versionNumber: ['', Validators.required],
      versionName: ['', Validators.required],
      versionLog: ['', Validators.required],
      releaseDate: ['', Validators.required],
      apkUrl: ['', Validators.required],
      googleAppUrl: ['', Validators.required],
      keyStoreFile: ['', Validators.required],
      encryptedKey: ['', Validators.required]
  });

    this.viewId = this.actRoute.snapshot.paramMap.get('id');

    if (this.viewId) {
      
      this.apkVersionService.getApkById(this.viewId).subscribe(data => {
        console.log('data------------>',data)
        this.dataObj=data;
          // this.viewApp.patchValue(data);
          // this.viewApp.patchValue({
          //   googleAppUrl:data.googleAppUrl,
          //   encryptedKey:data.encryptedKey
          //  });    
      });
  }

  }

}
