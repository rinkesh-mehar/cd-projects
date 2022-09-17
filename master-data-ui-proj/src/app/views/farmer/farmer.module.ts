import {MatButtonModule, MatFormFieldModule, MatInputModule, MatSortModule} from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FarmerLoanSourceComponent } from './farmer-loan-source/farmer-loan-source.component';
import { FarmerLoanPurposeComponent } from './farmer-loan-purpose/farmer-loan-purpose.component';
import { FarmerLanguageComponent } from './farmer-language/farmer-language.component';
import { FarmerInsuranceCompanyComponent } from './farmer-insurance-company/farmer-insurance-company.component';
import { FarmerInsuranceComponent } from './farmer-insurance/farmer-insurance.component';
import { FarmerIdProofComponent } from './farmer-id-proof/farmer-id-proof.component';
import { FarmerFarmOwnershipTypeComponent } from './farmer-farm-ownership-type/farmer-farm-ownership-type.component';
import { FarmerEnrollmentPlaceComponent } from './farmer-enrollment-place/farmer-enrollment-place.component';
import { FarmerEducationComponent } from './farmer-education/farmer-education.component';

import { EditFarmerLoanPurposeComponent } from './edit-farmer-loan-purpose/edit-farmer-loan-purpose.component';
import { AddFarmerLoanPurposeComponent } from './add-farmer-loan-purpose/add-farmer-loan-purpose.component';
import { AddFarmerLoanSourceComponent } from './add-farmer-loan-source/add-farmer-loan-source.component';
import { EditFarmerLoanSourceComponent } from './edit-farmer-loan-source/edit-farmer-loan-source.component';
import { EditFarmerLanguageComponent } from './edit-farmer-language/edit-farmer-language.component';
import { AddFarmerLanguageComponent } from './add-farmer-language/add-farmer-language.component';
import { AddFarmerInsuranceCompanyComponent } from './add-farmer-insurance-company/add-farmer-insurance-company.component';
import { EditFarmerInsuranceCompanyComponent } from './edit-farmer-insurance-company/edit-farmer-insurance-company.component';
import { AddFarmerInsuranceComponent } from './add-farmer-insurance/add-farmer-insurance.component';
import { EditFarmerInsuranceComponent } from './edit-farmer-insurance/edit-farmer-insurance.component';
import { AddFarmerIdProofComponent } from './add-farmer-id-proof/add-farmer-id-proof.component';
import { EditFarmerIdProofComponent } from './edit-farmer-id-proof/edit-farmer-id-proof.component';
import { AddFarmerFarmOwenershipTypeComponent } from './add-farmer-farm-owenership-type/add-farmer-farm-owenership-type.component';
import { EditFarmerFarmOwnershipTypeComponent } from './edit-farmer-farm-ownership-type/edit-farmer-farm-ownership-type.component';
import { AddFarmerEnrollmentPlaceComponent } from './add-farmer-enrollment-place/add-farmer-enrollment-place.component';
import { EditFarmerEnrollmentPlaceComponent } from './edit-farmer-enrollment-place/edit-farmer-enrollment-place.component';
import { AddFarmerEducationComponent } from './add-farmer-education/add-farmer-education.component';
import { EditFarmerEducationComponent } from './edit-farmer-education/edit-farmer-education.component';
import { AgriRoutingModule } from './farmer-routing-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FarmerVIPDesignationComponent } from './farmer-vip-designation/farmer-vip-designation.component';
import { AddEditFarmerVIPDesignationComponent } from './farmer-vip-designation/add-edit-farmer-vip-designation/add-edit-farmer-vip-designation.component';
import { FarmerGovtDepartmentComponent } from './farmer-govt-department/farmer-govt-department.component';
import { AddEditFarmerGovtDepartmentComponent } from './farmer-govt-department/add-edit-farmer-govt-department/add-edit-farmer-govt-department.component';
import { FarmerIncomeSourceComponent } from './farmer-income-source/farmer-income-source.component';
import { AddEditFarmerIncomeSourceComponent } from './farmer-income-source/add-edit-farmer-income-source/add-edit-farmer-income-source.component';
import { AddEditFarmerVipStatusComponent } from './vip-status/add-edit-farmer-vip-status/add-edit-farmer-vip-status.component';
import { FarmerVipStatusComponent } from './vip-status/farmer-vip-status/farmer-vip-status.component';
import { AddEditFarmerProxyRelationTypeComponent } from './proxy-relation-type/add-edit-farmer-proxy-relation-type/add-edit-farmer-proxy-relation-type.component';
import { FarmerProxyRelationTypeComponent } from './proxy-relation-type/farmer-proxy-relation-type/farmer-proxy-relation-type.component';
import { FarmerMobileTypeComponent } from './mobile-type/farmer-mobile-type/farmer-mobile-type.component';
import { AddEditFarmerMobileTypeComponent } from './mobile-type/add-edit-farmer-mobile-type/add-edit-farmer-mobile-type.component';

import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { GovtOfficialDesignationComponent } from './govt-official-designation/govt-official-designation/govt-official-designation.component';
import { AddEditGovtOfficialDesignationComponent } from './govt-official-designation/add-edit-govt-official-designation/add-edit-govt-official-designation.component';
import { FarmerGovtOfficialDesignation } from './models/FarmerGovtOfficialDesignation';
import { FarmerMaritalStatusComponent } from './farmer-marital-status/farmer-marital-status/farmer-marital-status.component';
import { AddEditFarmerMaritalStatusComponent } from './farmer-marital-status/add-edit-farmer-marital-status/add-edit-farmer-marital-status.component';
import { ValidateStressComponent } from './validate-stress/validate-stress.component';
import { EditValidateStressComponent } from './edit-validate-stress/edit-validate-stress.component';
import {CarouselModule } from 'ngx-bootstrap/carousel';

@NgModule({
  declarations: [FarmerLoanSourceComponent, 
    FarmerLoanPurposeComponent, 
    FarmerLanguageComponent, 
    FarmerInsuranceCompanyComponent, 
    FarmerInsuranceComponent, 
    FarmerIdProofComponent, 
    FarmerFarmOwnershipTypeComponent, 
    FarmerEnrollmentPlaceComponent, 
    FarmerEducationComponent, 
    AddFarmerLoanPurposeComponent, 
    EditFarmerLoanPurposeComponent, 
    EditFarmerLoanPurposeComponent, 
    AddFarmerLoanPurposeComponent, 
    AddFarmerLoanSourceComponent, 
    EditFarmerLoanSourceComponent, 
    EditFarmerLanguageComponent, 
    AddFarmerLanguageComponent, 
    AddFarmerInsuranceCompanyComponent, 
    EditFarmerInsuranceCompanyComponent, 
    AddFarmerInsuranceComponent, 
    EditFarmerInsuranceComponent, 
    AddFarmerIdProofComponent, 
    EditFarmerIdProofComponent, 
    AddFarmerFarmOwenershipTypeComponent, 
    EditFarmerFarmOwnershipTypeComponent, 
    AddFarmerEnrollmentPlaceComponent, 
    EditFarmerEnrollmentPlaceComponent, 
    AddFarmerEducationComponent, 
    EditFarmerEducationComponent, 
    FarmerVIPDesignationComponent,
     AddEditFarmerVIPDesignationComponent, 
     FarmerGovtDepartmentComponent, 
     AddEditFarmerGovtDepartmentComponent, 
     FarmerIncomeSourceComponent, 
     AddEditFarmerIncomeSourceComponent, 
     AddEditFarmerVipStatusComponent, 
     FarmerVipStatusComponent, 
     AddEditFarmerProxyRelationTypeComponent, 
     FarmerProxyRelationTypeComponent, 
     FarmerMobileTypeComponent,
     AddEditFarmerMobileTypeComponent,
     GovtOfficialDesignationComponent,
     AddEditGovtOfficialDesignationComponent,
     FarmerMaritalStatusComponent,
     AddEditFarmerMaritalStatusComponent,
     ValidateStressComponent,
     EditValidateStressComponent

   ],
  imports: [
      CommonModule,
      AgriRoutingModule,
      FormsModule,
      ReactiveFormsModule,
      GlobalModule,
      PipeModule,
      PaginationModule.forRoot(),
      MatSortModule,
      CarouselModule,
      MatFormFieldModule,
      MatButtonModule,
      MatInputModule,
  ]
  
})
export class FarmerModule { }
