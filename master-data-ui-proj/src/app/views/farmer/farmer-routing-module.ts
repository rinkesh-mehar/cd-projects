import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FarmerEducationComponent } from './farmer-education/farmer-education.component';
import { AddFarmerEducationComponent } from './add-farmer-education/add-farmer-education.component';
import { EditFarmerEducationComponent } from './edit-farmer-education/edit-farmer-education.component';
import { FarmerEnrollmentPlaceComponent } from './farmer-enrollment-place/farmer-enrollment-place.component';
import { AddFarmerEnrollmentPlaceComponent } from './add-farmer-enrollment-place/add-farmer-enrollment-place.component';
import { EditFarmerEnrollmentPlaceComponent } from './edit-farmer-enrollment-place/edit-farmer-enrollment-place.component';
import { FarmerFarmOwnershipTypeComponent } from './farmer-farm-ownership-type/farmer-farm-ownership-type.component';
import { AddFarmerFarmOwenershipTypeComponent } from './add-farmer-farm-owenership-type/add-farmer-farm-owenership-type.component';
import { EditFarmerFarmOwnershipTypeComponent } from './edit-farmer-farm-ownership-type/edit-farmer-farm-ownership-type.component';
import { FarmerIdProofComponent } from './farmer-id-proof/farmer-id-proof.component';
import { AddFarmerIdProofComponent } from './add-farmer-id-proof/add-farmer-id-proof.component';
import { EditFarmerIdProofComponent } from './edit-farmer-id-proof/edit-farmer-id-proof.component';
import { FarmerInsuranceComponent } from './farmer-insurance/farmer-insurance.component';
import { AddFarmerInsuranceComponent } from './add-farmer-insurance/add-farmer-insurance.component';
import { EditFarmerInsuranceComponent } from './edit-farmer-insurance/edit-farmer-insurance.component';
import { FarmerInsuranceCompanyComponent } from './farmer-insurance-company/farmer-insurance-company.component';
import { AddFarmerInsuranceCompanyComponent } from './add-farmer-insurance-company/add-farmer-insurance-company.component';
import { EditFarmerInsuranceCompanyComponent } from './edit-farmer-insurance-company/edit-farmer-insurance-company.component';
import { FarmerLanguageComponent } from './farmer-language/farmer-language.component';
import { AddFarmerLanguageComponent } from './add-farmer-language/add-farmer-language.component';
import { EditFarmerLanguageComponent } from './edit-farmer-language/edit-farmer-language.component';
import { FarmerLoanPurposeComponent } from './farmer-loan-purpose/farmer-loan-purpose.component';
import { AddFarmerLoanPurposeComponent } from './add-farmer-loan-purpose/add-farmer-loan-purpose.component';
import { EditFarmerLoanPurposeComponent } from './edit-farmer-loan-purpose/edit-farmer-loan-purpose.component';
import { AddFarmerLoanSourceComponent } from './add-farmer-loan-source/add-farmer-loan-source.component';
import { FarmerLoanSourceComponent } from './farmer-loan-source/farmer-loan-source.component';
import { EditFarmerLoanSourceComponent } from './edit-farmer-loan-source/edit-farmer-loan-source.component';
import { FarmerVIPDesignationComponent } from './farmer-vip-designation/farmer-vip-designation.component';
import { AddEditFarmerVIPDesignationComponent } from './farmer-vip-designation/add-edit-farmer-vip-designation/add-edit-farmer-vip-designation.component';
import { FarmerGovtDepartmentComponent } from './farmer-govt-department/farmer-govt-department.component';
import { AddEditFarmerGovtDepartmentComponent } from './farmer-govt-department/add-edit-farmer-govt-department/add-edit-farmer-govt-department.component';
import { FarmerIncomeSourceComponent } from './farmer-income-source/farmer-income-source.component';
import { AddEditFarmerIncomeSourceComponent } from './farmer-income-source/add-edit-farmer-income-source/add-edit-farmer-income-source.component';
import { FarmerMobileTypeComponent } from './mobile-type/farmer-mobile-type/farmer-mobile-type.component';
import { AddEditFarmerMobileTypeComponent } from './mobile-type/add-edit-farmer-mobile-type/add-edit-farmer-mobile-type.component';
import { FarmerProxyRelationTypeComponent } from './proxy-relation-type/farmer-proxy-relation-type/farmer-proxy-relation-type.component';
import { AddEditFarmerProxyRelationTypeComponent } from './proxy-relation-type/add-edit-farmer-proxy-relation-type/add-edit-farmer-proxy-relation-type.component';
import { FarmerVipStatusComponent } from './vip-status/farmer-vip-status/farmer-vip-status.component';
import { AddEditFarmerVipStatusComponent } from './vip-status/add-edit-farmer-vip-status/add-edit-farmer-vip-status.component';
import { AddEditGovtOfficialDesignationComponent } from './govt-official-designation/add-edit-govt-official-designation/add-edit-govt-official-designation.component';
import { GovtOfficialDesignationComponent } from './govt-official-designation/govt-official-designation/govt-official-designation.component';
import { FarmerMaritalStatusComponent } from './farmer-marital-status/farmer-marital-status/farmer-marital-status.component';
import { AddEditFarmerMaritalStatusComponent } from './farmer-marital-status/add-edit-farmer-marital-status/add-edit-farmer-marital-status.component';
import { ValidateStressComponent } from './validate-stress/validate-stress.component';
import { EditValidateStressComponent } from './edit-validate-stress/edit-validate-stress.component';




const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Farmer'
    },
    children: [
      {
        path: '',
        redirectTo: 'education'
      },

      {
        path: 'education',
        component: FarmerEducationComponent,
        data: {
          title: 'Education'
        }
      },
      {
        path: 'education-add',
        component: AddFarmerEducationComponent,
        data: {
          title: 'Add Education'
        }
      },
      {
        path: 'education-edit/:id',
        component: EditFarmerEducationComponent,
        data: {
          title: 'Edit Education'
        }
      },
      {
        path: 'enrollment-place',
        component: FarmerEnrollmentPlaceComponent,
        data: {
          title: 'Enrollment-Place'
        }
      },
      {
        path: 'enrollment-place-add',
        component: AddFarmerEnrollmentPlaceComponent,
        data: {
          title: 'Add Enrollment-Place'
        }
      },
      {
        path: 'enrollment-place-edit/:id',
        component: EditFarmerEnrollmentPlaceComponent,
        data: {
          title: 'Edit Enrollment-Place'
        }
      },
      {
        path: 'farm-ownership-type',
        component: FarmerFarmOwnershipTypeComponent,
        data: {
          title: 'Farm-Ownership-Type'
        }
      },
      {
        path: 'farm-ownership-type-add',
        component: AddFarmerFarmOwenershipTypeComponent,
        data: {
          title: 'Add Farm-Ownership-Type'
        }
      },
      {
        path: 'farm-ownership-type-edit/:id',
        component: EditFarmerFarmOwnershipTypeComponent,
        data: {
          title: 'Edit Farm-Ownership-Type'
        }
      },
      {
        path: 'idproof',
        component: FarmerIdProofComponent,
        data: {
          title: 'Id-Proof'
        }
      },
      {
        path: 'idproof-add',
        component: AddFarmerIdProofComponent,
        data: {
          title: 'Add Id-Proof'
        }
      },
      {
        path: 'idproof-edit/:id',
        component: EditFarmerIdProofComponent,
        data: {
          title: 'Edit Id-Proof'
        }
      },
      {
        path: 'insurance-type',
        component: FarmerInsuranceComponent,
        data: {
          title: 'Insurance Type'
        }
      },
      {
        path: 'insurance-type-add',
        component: AddFarmerInsuranceComponent,
        data: {
          title: 'Add Farmer Insurance Type'
        }
      },
      {
        path: 'insurance-type-edit/:id',
        component: EditFarmerInsuranceComponent,
        data: {
          title: 'Edit Farmer Insurance Type'
        }
      },
      {
        path: 'insurance-company',
        component: FarmerInsuranceCompanyComponent,
        data: {
          title: 'Insurance-Company'
        }
      },
      {
        path: 'insurance-company-add',
        component: AddFarmerInsuranceCompanyComponent,
        data: {
          title: 'Add Insurance-Company'
        }
      },
      {
        path: 'insurance-company-edit/:id',
        component: EditFarmerInsuranceCompanyComponent,
        data: {
          title: 'Edit Insurance-Company'
        }
      },
      {
        path: 'language',
        component: FarmerLanguageComponent,
        data: {
          title: 'Language'
        }
      },
      {
        path: 'language-add',
        component: AddFarmerLanguageComponent,
        data: {
          title: 'Add Language'
        }
      },
      {
        path: 'language-edit/:id',
        component: EditFarmerLanguageComponent,
        data: {
          title: 'Edit Language'
        }
      },
      {
        path: 'loan-purpose',
        component: FarmerLoanPurposeComponent,
        data: {
          title: 'Loan-Purpose'
        }
      },
      {
        path: 'loan-purpose-add',
        component: AddFarmerLoanPurposeComponent,
        data: {
          title: 'Add Loan-Purpose'
        }
      },
      {
        path: 'loan-purpose-edit/:id',
        component: EditFarmerLoanPurposeComponent,
        data: {
          title: 'Edit Loan-Purpose'
        }
      },
      {
        path: 'loan-source',
        component: FarmerLoanSourceComponent,
        data: {
          title: 'Loan-Source'
        }
      },
      {
        path: 'loan-source-add',
        component: AddFarmerLoanSourceComponent,
        data: {
          title: 'Add Loan-Source'
        }
      },
      {
        path: 'loan-source-edit/:id',
        component: EditFarmerLoanSourceComponent,
        data: {
          title: 'Edit Loan-Source'
        }
      },
      {
        path: 'vip-designation',
        component: FarmerVIPDesignationComponent,
        data: {
          title: 'VIP Designations'
        }
      },
      {
        path: 'vip-designation-add',
        component: AddEditFarmerVIPDesignationComponent,
        data: {
          title: 'Add VIP Designations'
        }
      },
      {
        path: 'vip-designation-edit/:id',
        component: AddEditFarmerVIPDesignationComponent,
        data: {
          title: 'Edit VIP Designations'
        }
      },
      {
        path: 'govt-department',
        component: FarmerGovtDepartmentComponent,
        data: {
          title: 'Govt Department'
        }
      },
      {
        path: 'govt-department-add',
        component: AddEditFarmerGovtDepartmentComponent,
        data: {
          title: 'Add Govt Department'
        }
      },
      {
        path: 'govt-department-edit/:id',
        component: AddEditFarmerGovtDepartmentComponent,
        data: {
          title: 'Edit Govt Department'
        }
      },
      {
        path: 'income-source',
        component: FarmerIncomeSourceComponent,
        data: {
          title: 'Income Source'
        }
      },
      {
        path: 'income-source-add',
        component: AddEditFarmerIncomeSourceComponent,
        data: {
          title: 'Add Income Source'
        }
      },
      {
        path: 'income-source-edit/:id',
        component: AddEditFarmerIncomeSourceComponent,
        data: {
          title: 'Edit Income Source'
        }
      },
      {
        path: 'mobile-type',
        component: FarmerMobileTypeComponent,
        data: {
          title: 'Mobile Type'
        }
      },
      {
        path: 'mobile-type-add',
        component: AddEditFarmerMobileTypeComponent,
        data: {
          title: 'Add Mobile Type'
        }
      },
      {
        path: 'mobile-type-edit/:id',
        component: AddEditFarmerMobileTypeComponent,
        data: {
          title: 'Edit Mobile Type'
        }
      },
      {
        path: 'proxy-relation-type',
        component: FarmerProxyRelationTypeComponent,
        data: {
          title: 'Proxy Relation Type'
        }
      },
      {
        path: 'proxy-relation-type-add',
        component: AddEditFarmerProxyRelationTypeComponent,
        data: {
          title: 'Add Proxy Relation Type'
        }
      },
      {
        path: 'proxy-relation-type-edit/:id',
        component: AddEditFarmerProxyRelationTypeComponent,
        data: {
          title: 'Edit Proxy Relation Type'
        }
      },
      {
        path: 'vip-status',
        component: FarmerVipStatusComponent,
        data: {
          title: 'VIP Status'
        }
      },
      {
        path: 'vip-status-add',
        component: AddEditFarmerVipStatusComponent,
        data: {
          title: 'Add VIP Status'
        }
      },
      {
        path: 'vip-status-edit/:id',
        component: AddEditFarmerVipStatusComponent,
        data: {
          title: 'Edit VIP Status'
        }
      },

      {
        path: 'govt-official-designation',
        component: GovtOfficialDesignationComponent,
        data: {
          title: 'Govt Official Designation'
        }
      },
      {
        path: 'govt-official-designation-add',
        component: AddEditGovtOfficialDesignationComponent,
        data: {
          title: 'Add Govt Official Designation'
        }
      },
      {
        path: 'govt-official-designation-edit/:id',
        component: AddEditGovtOfficialDesignationComponent,
        data: {
          title: 'Edit Govt Official Designation'
        }
      },

      {
        path: 'marital-status',
        component: FarmerMaritalStatusComponent,
        data: {
          title: 'Marital Status'
        }
      },
      {
        path: 'marital-status-add',
        component: AddEditFarmerMaritalStatusComponent,
        data: {
          title: 'Add Marital Status'
        }
      },
      {
        path: 'marital-status-edit/:id',
        component: AddEditFarmerMaritalStatusComponent,
        data: {
          title: 'Edit Marital Status'
        }
      },
      {
        path: 'validate-stress',
        component: ValidateStressComponent,
        data: {
          title: 'Validate Stress'
        }
      },
      {
        path: 'edit-stress/:id',
        component: EditValidateStressComponent,
        data: {
          title: 'Edit Validate Stress'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgriRoutingModule { }
