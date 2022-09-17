import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ValidateStressService } from '../services/validate-stress.service';
import { StressDetail } from '../models/StressDetail';
import { CarouselConfig } from 'ngx-bootstrap/carousel';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {globalConstants} from '../../global/globalConstants';
import {InfoModalComponent} from '../../global/info-modal/info-modal.component';

declare var $: any;
@Component({
    selector: 'app-edit-validate-stress',
    templateUrl: './edit-validate-stress.component.html',
    styleUrls: ['./edit-validate-stress.component.scss'],
    providers: [
        { provide: CarouselConfig, useValue: { noPause: false, showIndicators: false, keyboard: true } }
    ],
})
export class EditValidateStressComponent implements OnInit, AfterViewInit  {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('infoModal') public infoModal: InfoModalComponent;

    details: StressDetail;
    allSymptomOfSPEC: any;
    symptomIDOfRejection: any;
    imgID = 'Angular';
    taskStressDetailID: any;
    imgUrl: string;
    modelStressName: string;
    referenceImageArray: any;
    symptomImageURLList: any;
    modelStressNameList: any;
    isFarmerImage = false;
    noWrapSlides = false;
    showIndicator = true;
    magnifierZoomLevel = 5;

    constructor(private actRoute: ActivatedRoute, private validateStressService: ValidateStressService, private router: Router) {
    }

    ngOnInit(): void {
        this.taskStressDetailID = this.actRoute.snapshot.paramMap.get('id');

        this.getDetails(this.taskStressDetailID);
    }

    getDetails(taskStressID) {
        this.validateStressService.getDetailsByTaskSterssDetailID(taskStressID).subscribe(data => {
            console.log('data is in get details', data);
            this.details = data;
            this.referenceImageArray = this.details.referenceImages.split(',');
        });
    }

    getImageUrl(event: any, stressName: any) {
        if (stressName !== undefined) {
            this.modelStressName = stressName;
            this.isFarmerImage = false;
        } else {
            this.isFarmerImage = true;
        }
        this.imgUrl = event.target.src;
    }

    getSymptomBySpec() {

        this.infoModal.showModal('INFO',  'Please Select Correct Stress From Reference Images Of All Stresses', '');

        this.validateStressService.getSymptomBySpec(this.details.commodityID, this.details.varietyID, this.details.phenophaseID,
            this.details.districtID).subscribe(data => {
                console.log('symptom data by spec is ', data);

            this.allSymptomOfSPEC = data;

            const symptomImageURLListTemp = [];
            const modelStressNameListTemp = [];

            for (const dataValue of this.allSymptomOfSPEC) {
                symptomImageURLListTemp.push(dataValue.symptomImageUrl);
                modelStressNameListTemp.push(dataValue.stressName);
            }
            this.symptomImageURLList = symptomImageURLListTemp;
            this.modelStressNameList = modelStressNameListTemp;

            console.log('symptom images in get symptom by spec', this.symptomImageURLList);
        });
    }

    setRejectionSymptomID(event) {
        this.scrollToTop();
        // window.scrollTo(0, 0);
        if (event.target !== undefined) {
            if (event.target.checked === true) {
                this.symptomIDOfRejection = event.target['value'];
            }
        } else {
            this.symptomIDOfRejection = event;
        }

        this.getSymptomDetailsBySymptom(this.symptomIDOfRejection);
    }

    getSymptomDetailsBySymptom(symptomID: number) {
        this.validateStressService.getSymptomDetailsBySymptom(symptomID).subscribe(data => {
            console.log('Symptom Details By Symptom is for card 2 -> ', data);

            // this.details.plantPartName = data.plantPartName;
            // this.details.stressTypeName = data.stressTypeName;
            // this.details.stressName = data.stressName;
            // this.details.stageName = data.stageName;
            this.referenceImageArray = data.referenceImages.split(',');
        });
    }

    scrollToTop() {
        (function smoothscroll() {

            const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;

            if (currentScroll > 0) {
                window.requestAnimationFrame(smoothscroll);
                window.scrollTo(0, currentScroll - (currentScroll / 40));
            }

        })();
    }

    approveSymptom(id: string, symptomID: number, flag: string) {
        // alert(' id ' + id + ' symptomId '+ symptomID);

        if ($('.item.active #afterRejectsymptomID').text() != '') {
            symptomID = $('.item.active #afterRejectsymptomID').text();
            console.log(symptomID);
        }

        const data: any = {
            'id': id,
            'symptomID': symptomID,
            'approvalStatus': flag === 'A' ? 'true' : 'false'
        };

        if (flag === 'NR') {
            this.confirmModal.showModal(globalConstants.notFoundDataTitle, 'Are You Sure There Is No Image Found For Farmer Given Image?', data);
        } else if (flag === 'A') {
            this.confirmModal.showModal(globalConstants.notFoundDataTitle, globalConstants.approveDataMsg, data);
        }
    }

    modalSuccess($event: any) {
        this.router.navigate(['/farmer/validate-stress']);
    }

    getPreviousImage(imgUrl: string) {
        const imgURLIndex = this.symptomImageURLList.indexOf(imgUrl) - 1;
        this.imgUrl = this.symptomImageURLList[imgURLIndex];
        this.modelStressName = this.modelStressNameList[imgURLIndex];
    }

    getNextImage(imgUrl: string) {
        const imgURLIndex = this.symptomImageURLList.indexOf(imgUrl) + 1;
        this.imgUrl = this.symptomImageURLList[imgURLIndex];
        this.modelStressName = this.modelStressNameList[imgURLIndex];
    }

    modalConfirmation(data) {
        this.validateStressService.approveSymptom(data).subscribe(data => {
            // console.log('Response data', data);
            if (data.success) {
                this.successModal.showModal('SUCCESS', data.message, '');

            } else {
                this.errorModal.showModal('ERROR', data.message, '');
            }
        });
    }

    magnify(imgID, zoom) {
        var img, glass, w, h, bw;
        img = document.getElementById('myimage');
        /*create magnifier glass:*/
        glass = document.createElement('DIV');
        glass.setAttribute('class', 'img-magnifier-glass');
        /*insert magnifier glass:*/
        img.parentElement.insertBefore(glass, img);
        /*set background properties for the magnifier glass:*/
        glass.style.backgroundImage = 'url(\'' + img.src + '\')';
        glass.style.backgroundRepeat = 'no-repeat';
        glass.style.backgroundSize =
            img.width * zoom + 'px ' + img.height * zoom + 'px';
        bw = 3;
        w = glass.offsetWidth / 2;
        h = glass.offsetHeight / 2;
        /*execute a function when someone moves the magnifier glass over the image:*/
        glass.addEventListener('mousemove', moveMagnifier);
        img.addEventListener('mousemove', moveMagnifier);
        /*and also for touch screens:*/
        glass.addEventListener('touchmove', moveMagnifier);
        img.addEventListener('touchmove', moveMagnifier);

        function moveMagnifier(e) {
            var pos, x, y;
            /*prevent any other actions that may occur when moving over the image*/
            e.preventDefault();
            /*get the cursor's x and y positions:*/
            pos = getCursorPos(e);
            x = pos.x;
            y = pos.y;
            /*prevent the magnifier glass from being positioned outside the image:*/
            if (x > img.width - w / zoom) {
                x = img.width - w / zoom;
            }
            if (x < w / zoom) {
                x = w / zoom;
            }
            if (y > img.height - h / zoom) {
                y = img.height - h / zoom;
            }
            if (y < h / zoom) {
                y = h / zoom;
            }
            /*set the position of the magnifier glass:*/
            glass.style.left = x - w + 'px';
            glass.style.top = y - h + 'px';
            /*display what the magnifier glass "sees":*/
            glass.style.backgroundPosition =
                '-' + (x * zoom - w + bw) + 'px -' + (y * zoom - h + bw) + 'px';
        }

        function getCursorPos(e) {
            var a,
                x = 0,
                y = 0;
            e = e || window.event;
            /*get the x and y positions of the image:*/
            a = img.getBoundingClientRect();
            /*calculate the cursor's x and y coordinates, relative to the image:*/
            x = e.pageX - a.left;
            y = e.pageY - a.top;
            /*consider any page scrolling:*/
            x = x - window.pageXOffset;
            y = y - window.pageYOffset;
            return {x: x, y: y};
        }
    }

    ngAfterViewInit(): void {
        this.magnify('myimage', this.magnifierZoomLevel);
    }

    increaseZoomLevel() {
        this.magnifierZoomLevel ++;
    }

    decreaseZoomLevel() {
        this.magnifierZoomLevel --;
    }
}
