import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ClusterService} from '../../services/cluster.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-add-edit-source',
  templateUrl: './add-edit-source.component.html',
  styleUrls: ['./add-edit-source.component.scss']
})
export class AddEditSourceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('closebutton') closebutton;

  sourceForm: FormGroup;
  stateList: any;
  countryList: any;
  editId: string;
  mode: string = 'add';

  constructor(private formBuilder: FormBuilder, private actRoute: ActivatedRoute,
              private clusterService: ClusterService, public router: Router) { }

  ngOnInit(): void {

    this.getCountryList();
    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId){
      this.sourceForm = this.formBuilder.group({
        countryCode: ['', Validators.required],
        stateCode: ['', Validators.required],
        name: ['', Validators.required],
        address: ['', Validators.required],
        headquarter: ['', Validators.required],
        description: ['']
      });
      this.mode = 'edit';

      this.clusterService.getSourceById(this.editId).subscribe( data => {
        this.getStatesListByCountryCode(data.countryCode);
        this.sourceForm.patchValue({
          countryCode: data.countryCode,
          stateCode: data.stateCode,
          address: data.address,
          headquarter: data.headquarter,
          name: data.name,
          description: data.description
        });
      });

    } else {
      this.sourceForm = this.formBuilder.group({
        countryCode: ['', Validators.required],
        stateCode: ['', Validators.required],
        name: ['', Validators.required],
        address: ['', Validators.required],
        headquarter: ['', Validators.required],
        description: ['']
      });
    }
  }

  submitForm() {
    for (const controller in this.sourceForm.controls) {
      this.sourceForm.get(controller).markAllAsTouched();
    } if (this.sourceForm.invalid){
      return;
    }
    if (this.mode == 'add'){

      this.add();
    } else {

      this.edit();
    }
  }

  add() {

    this.clusterService.storeSourceData(this.sourceForm).subscribe( res => {
      if (res.success){
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.message, '');
      }
    });

  }

  edit(){
    this.clusterService.updateSourceDetails(this.editId, this.sourceForm).subscribe( res => {
      if (res.success) {
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.message, '');
      }
    })
  }
  getCountryList(){
    this.clusterService.getCountryList().subscribe((data: any) => {
      this.countryList = data;
    });
  }

  getStatesListByCountryCode(id){
    this.clusterService.getStateListByCountryCode(id).subscribe(
        (data: any) => {
          this.stateList = data;
        }
    );
  }

  modalSuccess($event: any) {
    this.router.navigate(['/cluster/source-list']);
  }
}
