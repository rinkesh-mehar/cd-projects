import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { IndividualKycDetails } from './individual-kyc-details.model';
import { IndividualKycDetailsService } from './individual-kyc-details.service';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { ErrorMessages } from '../../../form-validation-messages';
declare var $, datatable;


@Component({
  selector: 'app-individual-kyc-details',
  templateUrl: './individual-kyc-details.component.html',
  styleUrls: ['./individual-kyc-details.component.less']
})
export class IndividualKycDetailsComponent implements OnInit {
  data: Date;
  ageDate: Date;
  myForm: FormGroup;
  modalRef: BsModalRef;
  newDate: any;
  age: any;
  panDetails: FormGroup;
  submitted = false;
  public loggedInUser = {} as any;
  public maxDate: Date;
  // added for user model
  public individual = {} as IndividualKycDetails;
  public valueCompare: boolean = false;
  public kycDob;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public comparenumbermessage: string;
  public httperrorresponsemessages: string;

  public farmerId: string;
  public taskId: number;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private modalService: BsModalService,
    public individualKycDetailsService: IndividualKycDetailsService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.comparenumbermessage = ErrorMessages.compareNumberError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));

    this.maxDate = new Date(this.loggedInUser.date); //this.loggedInUser.date;  

    const routeparam = this.route.params.subscribe(params => {
      this.farmerId = params['farmerId'];
      this.taskId = params['taskId'];
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    this.getIndividualKycDetailsData();
    this.panDetails = this.formBuilder.group({
      documenttype: [''],
      farmername: ['', [Validators.required, Validators.pattern("^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$")]],
      // fathername: ['', [Validators.required, Validators.pattern("^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$")]],
      fathername: [''],

      Gender: [''],
      dateofbirth: ['', [Validators.required]],
      age: [''],
      mobilenumber: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10)]],
      alternatemobilenumber: ['', [Validators.pattern("^[0-9]*$"), Validators.minLength(10)]],
      address: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]],
      comments: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]]
    });
    this.panDetails.get('age').setValue('');
    this.age = '';

  }
  get f() { return this.panDetails.controls; }

  onValueChange(value: Date): void {
    const data = new Date(value);

    this.age = this.maxDate.getFullYear() - data.getFullYear();
    this.panDetails.get('age').setValue(this.age);
    const m = this.maxDate.getMonth() - data.getMonth();
    if (data.getFullYear() > 2000) {
      ++this.age;
    }
    if (m > 0 || (m == 0 && this.maxDate.getDate() < data.getDate())) {
      this.age--;

    }



  }

  onDateChange() {
    $(".dob-datepicker").addClass("dob-changed");
    localStorage.setItem('dataSource', JSON.stringify(this.individual.dateOfBirth));
    this.kycDob = JSON.parse(localStorage.getItem('dataSource'));
  }



  slideConfig = {
    "slidesToShow": 1,
    "slidesToScroll": 1,
    "arrows": false,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": false,
    "infinite": true,
    "asNavFor": '.secound',
    "draggable": false,
    "speed": 100,
  };
  slideConfigNew = {
    "slidesToShow": 3,
    "slidesToScroll": 1,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": false,
    "infinite": true,
    "asNavFor": '.first',
    "focusOnSelect": true,
    "centerMode": false,
    "draggable": false,
    responsive: [
      {
        "breakpoint": 991,
        "settings": {
          "slidesToShow": 4
        }
      }
    ]
  };


  onSave() {
    this.submitted = true;
    // stop here if form is invalid
    if (!this.panDetails.invalid) {

      if (this.individual.mobileNumber === this.individual.alternateMobileNumber) {
        var headerHeight = $("header").height();
        $('html,body').animate({ scrollTop: $('.form-control.is-invalid:visible:first').offset().top - headerHeight - 30 }, 200);
        return;
      } else {

        if ($(".dob-datepicker").hasClass("dob-changed") && this.individual.dateOfBirth != "NaN-NaN-NaN") {
          this.individual.dateOfBirth = this.dateFormatChange(this.individual.dateOfBirth);
        }
        if ($(".dob-datepicker").hasClass("dob-changed") && this.individual.dateOfBirth == "NaN-NaN-NaN") {
          this.individual.dateOfBirth = this.kycDob;
        }

        this.individualKycDetailsService.submitIndividualKycDetails(this.individual).subscribe(
          (data) => {
            if(data.statusCode == "success"){
              const initialState = {
                title: "Success",
                content: data.message,
                action: "/kyc-list"
              };
              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
              return;
            }else if(data.statusCode == "error"){
              const initialState = {
                title: "Error",
                content: data.message,
                action: "/individual-kyc-details/" + this.farmerId + "/" + this.taskId
              };
              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
              return;
            }
          }, (err) => {
            const initialState = {
              title: "Error",
              content: this.httperrorresponsemessages,
              action: "/individual-kyc-details/" + this.farmerId + "/" + this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          });
      }
    } else {
      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.form-control.ng-invalid, .form-control.is-invalid:visible:first').offset().top - headerHeight - 30 }, 200);
      return;
    }

  }
  public getIndividualKycDetailsData() {

    this.individualKycDetailsService.getIndividualKycDetailsData(this.farmerId, this.taskId, this.loggedInUser.userId).subscribe(
      (data) => {
        if(data.statusCode == "success"){
          this.individual = data.data;
          localStorage.setItem('dataSource', JSON.stringify(this.individual.dateOfBirth));
          this.kycDob = JSON.parse(localStorage.getItem('dataSource'));
        }else if(data.statusCode == "error"){
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/individual-kyc-details/" + this.farmerId + "/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/individual-kyc-details/" + this.farmerId + "/" + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  public dateFormatChange(value: string) {
    var date = new Date(value),
      month = '' + (date.getMonth() + 1),
      day = '' + date.getDate(),
      year = date.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    var selectedDate = [day, month, year].join('-');
    return selectedDate;

  }


}
