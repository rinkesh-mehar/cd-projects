import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {newsReportsService} from '../../services/news-reports.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { urlValidator } from '../../../validators/urlValidator.validator';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';

@Component({
    selector: 'app-add-news',
    templateUrl: './add-edit-news.component.html',
    styleUrls: ['./add-edit-news.component.css']
})
export class AddEditNewsComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('closebutton') closebutton;

    addNews: FormGroup;
    platFormList: any =[];
    latestNews: any;
    mode: string = 'add';
    imageFile: any;
    isCheckBox: any;
    image: any;
    isImage: boolean;
    editId: string;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    date: any;
    dd : any;
    mm: any;
    yyyy: any;
    isCropdata: boolean = false;
    platFormId: any;
    isImageFileRequired: boolean = false;
    isSmallDescriptionRequired: boolean = true;
    isPriorityHidden: boolean = false;
    isImageFileHidden: boolean = true;
    isExternalImageUrlHidden: boolean = true;

    constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
                public newsService: newsReportsService, public router: Router) {
    }

    ngOnInit(): void {
        var today = new Date();
         this.dd = today.getDate();
        this.mm = today.getMonth() + 1;
        this.yyyy = today.getFullYear();
         if (this.dd < 10) {
           this.dd = '0' + this.dd;
         }

         if (this.mm < 10) {
           this.mm = '0' + this.mm;
         }
        this.date = this.yyyy + '-'+this.mm+ '-'+ this.dd;
        this.isImage = true;
        this.getPlatForm();

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.addNews = this.formBuilder.group({
                platformId: ['', Validators.required],
                priority: [''],
                subject: ['', Validators.required],
                publishedDate: ['', Validators.required],
                description: ['', Validators.required],
                externalUrl: ['', [Validators.required, urlValidator]],
                imageUrl: [''],
                imageSource: ['', Validators.required],
                isLatestNews: [''],
                externalImageUrl: ['']
            });
            
            this.mode = 'edit';
            // this.addNews.get('imageUrl').disable();
            this.newsService.getNewsById(this.editId).subscribe(data => {
                this.image = data.imageUrl;
                if (data.platformId == '5'){
                    this.isPriorityHidden = true;
                    // console.log("priority : " + data.priority);
                    this.addNews.patchValue({priority: data.priority});
                    this.platFormId = data.platformId;
                    this.addNews.get('description').clearValidators();
                    // this.addNews.get('imageUrl').setValidators([Validators.required]);

                    this.addNews.get('description').updateValueAndValidity();
                    // this.addNews.get('imageUrl').updateValueAndValidity();
                    
                    this.isImage = false;
                    this.isImageFileHidden = false;
                    this.isExternalImageUrlHidden = false;
                } else {
                    this.addNews.get('imageUrl').clearValidators();
                    this.addNews.get('imageUrl').updateValueAndValidity();

                    this.isImage = true;
                    this.isImageFileHidden = true;
                    this.isExternalImageUrlHidden = true;
                }
                 this.addNews.patchValue({
                     platformId: data.platformId, subject: data.subject, publishedDate: data.publishedDate,
                  description: data.description, externalUrl: data.externalUrl, id: data.id, imageSource: data.imageSource,
                  externalImageUrl: data.imageUrl
              });
                this.isCheckBox = data.platformId;
                if (data.latestNews == 'Yes'){
                    this.addNews.patchValue({isLatestNews: true});
                }
                // this.addNews.setValue({imageUrl: ''});
                this.addNews.patchValue(data);
                this.addNews.patchValue({publishedDate: data.publishedDate});
                // console.log('image url---->', this.image);
                
            });
        } else {
            this.addNews = this.formBuilder.group({
                platformId: ['', Validators.required],
                subject: ['', Validators.required],
                publishedDate: ['', Validators.required],
                description: ['', Validators.required],
                externalUrl: ['', [Validators.required, urlValidator]],
                imageUrl: [''],
                imageSource: ['', Validators.required],
                isLatestNews: [''],
                externalImageUrl: ['']
            });
        }

        // console.log('id ' + this.editId);
    }

    getPlatForm() {
        return this.newsService.getPlatFormList().subscribe((data: {}) => {
            this.platFormList = data;

        });
    }

    /*Upload images file*/
    imageFileChange(element) {
        console.log("inside imageFileChange..!!");
        if(this.mode === 'edit'){
            this.isImage = true;
        }
        this.isCropdata = true;
        let file: File = element.target.files[0];
        this.addNews.get('imageUrl').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
        this.addNews.get('imageUrl').updateValueAndValidity();
        // console.log('image url--->', this.imageFile);
        this.imageFile = file;
        this.image = this.imageFile.name;
        this.addNews.setValue({imageUrl: element.target.value});
        this.closebutton.nativeElement.click();
      /* const extension = this.imageFile.lastIndexOf('.');
       console.log('find extension---->', extension);*/

    }

    submitForm() {
        for (const controller in this.addNews.controls) {
            this.addNews.get(controller).markAllAsTouched();
        }if (this.addNews.invalid) {
            return;
        }

        if(this.addNews.value.platformId !== 5 && this.addNews.value.externalImageUrl === this.addNews.value.externalUrl){
            console.log("imageUrl : " + this.addNews.value.externalImageUrl + " externalUrl : " + this.addNews.value.externalUrl);
            this.errorModal.showModal('ERROR', 'Image URL should not be same as External URL. Please Enter proper Image URL.', '');
            return;
        }

        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        return this.newsService.createNews(this.addNews.value, this.imageFile).subscribe(res => {
            this.isSubmitted = true;
            if (res.success) {
                this.successModal.showModal('SUCCESS', res.message, '');
            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });
    }

    update() {
              /*  alert(JSON.stringify(this.addNews.value.latestNews));*/
     let data = {
         'platformId' : this.addNews.controls.platformId.value,
         'priority' : this.addNews.controls.priority.value,
         'subject': this.addNews.value.subject,
         'publishedDate': this.addNews.value.publishedDate,
         'description' : this.addNews.value.description,
         'externalUrl' : this.addNews.value.externalUrl,
         'imageUrl' : this.addNews.value.imageUrl,
         'imageSource' : this.addNews.value.imageSource,
         'isLatestNews' : this.addNews.value.isLatestNews,
         'externalImageUrl' : this.addNews.value.externalImageUrl
     };
        return this.newsService.updateNews(this.editId, data, this.imageFile).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    hide() {
        this.addNews.get('imageUrl').enable();
        this.closebutton.nativeElement.click();
        this.isImage = true;
        // this.hideModal.emit(this.manageForm);
    }
    change(event){
        this.addNews.patchValue({isLatestNews: false});
        let platFormId = event.target.value;
        this.isCheckBox = platFormId;

        if (platFormId == 5) {
            if(this.mode === 'add'){
                this.isImage = true;
            }else{
                this.isImage = false;
            }
            this.isCropdata = false;
            this.addNews.get('description').clearValidators();
            this.addNews.get('imageUrl').setValidators([Validators.required]);

            this.addNews.get('description').updateValueAndValidity();
            this.addNews.get('imageUrl').updateValueAndValidity();

            this.isImageFileRequired = true;
            this.isSmallDescriptionRequired = false;
            this.isImageFileHidden = false;
            this.isExternalImageUrlHidden = false;
            this.isPriorityHidden = false;
        

            // this.addNews.patchValue({externalImageUrl: ''});
            // this.addNews.patchValue({imageUrl: ''});
            // this.image = this.addNews.value.imageUrl;


        }else {
            this.addNews.get('description').setValidators([Validators.required]);
            this.addNews.get('imageUrl').clearValidators();

            this.addNews.get('description').updateValueAndValidity();
            this.addNews.get('imageUrl').updateValueAndValidity();

            this.isImageFileRequired = false;
            this.isSmallDescriptionRequired = true;
            this.isImageFileHidden = true;
            this.isImage = true;
            this.isExternalImageUrlHidden = true;
            this.isPriorityHidden = false;

            // this.addNews.patchValue({externalImageUrl: ''});

        }
    }

    onChangeExternalImageUrl(){
        // console.log("external url : " + this.addNews.get('externalImageUrl').value);

        if(this.addNews.get('externalImageUrl').value !== ''){
            console.log("inside if");
            this.addNews.get('externalImageUrl').setValidators([urlValidator]);
            this.addNews.get('externalImageUrl').updateValueAndValidity();
        }else{
            this.addNews.get('externalImageUrl').clearValidators();
            this.addNews.get('externalImageUrl').updateValueAndValidity(); 
        }
    }

    modalSuccess($event: any) {
        this.router.navigate(['/cropdata-portal/news']);
    }
}
