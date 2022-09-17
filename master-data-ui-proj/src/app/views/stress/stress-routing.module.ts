import { AgriAgrochemicalInstructionsComponent } from './agri-agrochemical-instructions/agri-agrochemical-instructions.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgriStressTypeComponent } from '../agri/agri-stress-type/agri-stress-type.component';
import { AddAgriStressTypeComponent } from '../agri/add-agri-stress-type/add-agri-stress-type.component';
import { EditAgriStressTypeComponent } from '../agri/edit-agri-stress-type/edit-agri-stress-type.component';
import { AgriVarietyStressComponent } from '../agri/agri-variety-stress/agri-variety-stress.component';
import { AddAgriVarietyStressComponent } from '../agri/add-agri-variety-stress/add-agri-variety-stress.component';
import { EditAgriVarietyStressComponent } from '../agri/edit-agri-variety-stress/edit-agri-variety-stress.component';
import { AgriControlMeasuresComponent } from '../agri/agri-control-measures/agri-control-measures/agri-control-measures.component';
import { AddEditAgriControlMeasuresComponent } from '../agri/agri-control-measures/add-edit-agri-control-measures/add-edit-agri-control-measures.component';
import { AgriStressControlMeasuresComponent } from '../agri/agri-stress-control-measures/agri-stress-control-measures/agri-stress-control-measures.component';
import { AddEditAgriStressControlMeasuresComponent } from '../agri/agri-stress-control-measures/add-edit-agri-stress-control-measures/add-edit-agri-stress-control-measures.component';
import { AgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/agrochemical-application/agrochemical-application.component';
import { AddAgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/add-agrochemical-application/add-agrochemical-application.component';
import { EditAgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/edit-agrochemical-application/edit-agrochemical-application.component';
import { AgriSeedTreatmentComponent } from '../agri/agri-seed-treatment/agri-seed-treatment.component';
import { AddAgriSeedTreatmentComponent } from '../agri/add-agri-seed-treatment/add-agri-seed-treatment.component';
import { EditAgriSeedTreatmentComponent } from '../agri/edit-agri-seed-treatment/edit-agri-seed-treatment.component';
import { AgriCommodityStressStageComponent } from '../agri/agri-commodity-stress-stage/agri-commodity-stress-stage.component';
import { AddAgriCommodityStressStageComponent } from '../agri/add-agri-commodity-stress-stage/add-agri-commodity-stress-stage.component';
import { EditAgriCommodityStressStageComponent } from '../agri/edit-agri-commodity-stress-stage/edit-agri-commodity-stress-stage.component';
import { StressSeverityComponent } from '../agri/stress-severity/stress-severity/stress-severity.component';
import { AddStressSeverityComponent } from '../agri/stress-severity/add-stress-severity/add-stress-severity.component';
import { EditStressSeverityComponent } from '../agri/stress-severity/edit-stress-severity/edit-stress-severity.component';
import { AgriDoseFactorComponent } from '../agri/agri-dose-factor/agri-dose-factor/agri-dose-factor.component';
import { AddEditAgriDoseFactorComponent } from '../agri/agri-dose-factor/add-edit-agri-dose-factor/add-edit-agri-dose-factor.component';
import {AgriStressComponent} from '../agri/agri-stress/agri-stress.component';
import {AddEditAgriStressComponent} from '../agri/agri-stress/add-edit-agri-stress/add-edit-agri-stress.component';
import { AgriRecommendationComponent } from './agri-recommendation/agri-recommendation.component';
import { AddEditAgriRecommendationComponent } from './agri-recommendation/add-edit-agri-recommendation/add-edit-agri-recommendation.component';
import { AgriStressStageComponent } from './agri-stress-stage/agri-stress-stage.component';
import { AddEditAgriStressStageComponent } from './agri-stress-stage/add-edit-agri-stress-stage/add-edit-agri-stress-stage.component';
import { AddEditAgriAgrochemicalInnstructionsComponent } from './agri-agrochemical-instructions/add-edit-agri-agrochemical-innstructions/add-edit-agri-agrochemical-innstructions.component';
import { AgriCommodityStressComponent } from './agri-commodity-stress/agri-commodity-stress.component';
import { AddEditAgriCommodityStressComponent } from './agri-commodity-stress/add-edit-agri-commodity-stress/add-edit-agri-commodity-stress.component';
import { AgriStageComponent } from './agri-stage/agri-stage.component';
import { AddEditAgriStageComponent } from './agri-stage/add-edit-agri-stage/add-edit-agri-stage.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Stress'
    },
    children: [
      {
        path: '',
        redirectTo: 'stress'
      },
      {
        path: 'stress',
        component: AgriStressComponent,
        data: {
          title: 'Stress'
        }
      },
      {
        path: 'stress-add',
        component: AddEditAgriStressComponent,
        data: {
          title: 'Add Stress'
        }
      },
      {
        path: 'stress-edit/:id',
        component: AddEditAgriStressComponent,
        data: {
          title: 'Edit Stress'
        }
      },
           {
        path: 'stress-type',
        component: AgriStressTypeComponent,
        data: {
          title: 'Stress-Type'
        }
      },
      {
        path: 'stress-type-add',
        component: AddAgriStressTypeComponent,
        data: {
          title: 'Add Stress-Type'
        }
      },
      {
        path: 'stress-type-edit/:id',
        component: EditAgriStressTypeComponent,
        data: {
          title: 'Edit Stress-Type'
        }
      },
      {
        path: 'variety-stress',
        component: AgriVarietyStressComponent,
        data: {
          title: 'Variety-Stress'
        }
      },
      {
        path: 'variety-stress-add',
        component: AddAgriVarietyStressComponent,
        data: {
          title: 'Add Variety-Stress'
        }
      },
      {
        path: 'variety-stress-edit/:id',
        component: EditAgriVarietyStressComponent,
        data: {
          title: 'Edit Variety-Stress'
        }
      },
           {
        path: 'control-measures',
        component: AgriControlMeasuresComponent,
        data: {
          title: 'Control Measures'
        }
      },
      {
        path: 'control-measures-add',
        component: AddEditAgriControlMeasuresComponent,
        data: {
          title: 'Add Control Measures'
        }
      },
      {
        path: 'control-measures-edit/:id',
        component: AddEditAgriControlMeasuresComponent,
        data: {
          title: 'Edit Control Measures'
        }
      },{
        path: 'stress-control-measures',
        component: AgriStressControlMeasuresComponent,
        data: {
          title: 'Stress Control Measures'
        }
      },
      {
        path: 'stress-control-measures-add',
        component: AddEditAgriStressControlMeasuresComponent,
        data: {
          title: 'Add Stress Control Measures'
        }
      },
      {
        path: 'stress-control-measures-edit/:id',
        component: AddEditAgriStressControlMeasuresComponent,
        data: {
          title: 'Edit Stress Control Measures'
        }
      },
            {
        path: 'agrochemical-application-type',
        component: AgrochemicalApplicationComponent,
        data: {
          title: 'AgroChemical-Application'
        }
      },
      {
        path: 'agrochemical-application-type-add',
        component: AddAgrochemicalApplicationComponent,
        data: {
          title: 'Add AgroChemical-Application'
        }
      },
      {
        path: 'agrochemical-application-type-edit/:id',
        component: EditAgrochemicalApplicationComponent,
        data: {
          title: 'Edit AgroChemical-Application'
        }
      },
      {
        path: 'seed-treatment',
        component: AgriSeedTreatmentComponent,
        data: {
          title: 'SeedTreatment'
        }
      },
      {
        path: 'seed-treatment-add',
        component: AddAgriSeedTreatmentComponent,
        data: {
          title: 'Add SeedTreatment'
        }
      },
      {
        path: 'seed-treatment-edit/:id',
        component: EditAgriSeedTreatmentComponent,
        data: {
          title: 'Edit SeedTreatment'
        }
      },
       {
        path: 'commodity-stress-stage',
        component: AgriCommodityStressStageComponent,
        data: {
          title: 'Commodity Stress Stage'
        }
      },
      {
        path: 'commodity-stress-stage-add',
        component: AddAgriCommodityStressStageComponent,
        data: {
          title: 'Add Commodity Stress Stage'
        }
      },
      {
        path: 'commodity-stress-stage-edit/:id',
        component: EditAgriCommodityStressStageComponent,
        data: {
          title: 'Edit Commodity Stress Stage'
        }
      },
      {
        path: 'stress-severity',
        component: StressSeverityComponent,
        data: {
          title: 'Stress-Severity'
        }
      },
      {
        path: 'stress-severity-add',
        component: AddStressSeverityComponent,
        data: {
          title: 'Add Stress-Severity'
        }
      },
      {
        path: 'stress-severity-edit/:id',
        component: EditStressSeverityComponent,
        data: {
          title: 'Edit Stress-Severity'
        }
      },

      {
        path: 'dose-factor',
        component: AgriDoseFactorComponent,
        data: {
          title: 'Dose-Factor'
        }
      },
      {
        path: 'dose-factor-add',
        component: AddEditAgriDoseFactorComponent,
        data: {
          title: 'Add Dose-Factor'
        }
      },
      {
        path: 'dose-factor-edit/:id',
        component: AddEditAgriDoseFactorComponent,
        data: {
          title: 'Edit Dose-Factor'
        }
      },
      {
        path: 'recommendation',
        component: AgriRecommendationComponent,
        data: {
          title: 'Recommendation'
        }
      },
      {
        path: 'recommendation/add-recommendation',
        component: AddEditAgriRecommendationComponent,
        data: {
          title: 'Add Recommendation'
        }
      },
      {
        path: 'recommendation/edit-recommendation/:id',
        component: AddEditAgriRecommendationComponent,
        data: {
          title: 'Edit Recommendation'
        }
      },
      {
        path: 'stress-stage',
        component: AgriStressStageComponent,
        data: {
          title: 'Stress Stage'
        }
      },
      {
        path: 'stress-stage/add-stress-stage',
        component: AddEditAgriStressStageComponent,
        data: {
          title: 'Add Stress Stage'
        }
      },
      {
        path: 'stress-stage/edit-stress-stage/:id',
        component: AddEditAgriStressStageComponent,
        data: {
          title: 'Edit Stress Stage'
        }
      },
      {
        path: 'agrochemical-instructions',
        component: AgriAgrochemicalInstructionsComponent,
        data: {
          title: 'Agrochemical Instructions'
        }
      },
      {
        path: 'agrochemical-instructions/add-agrochemical-instructions',
        component: AddEditAgriAgrochemicalInnstructionsComponent,
        data: {
          title: 'Add Agrochemical Instructions'
        }
      },
      {
        path: 'agrochemical-instructions/edit-agrochemical-instructions/:id',
        component: AddEditAgriAgrochemicalInnstructionsComponent,
        data: {
          title: 'Edit Agrochemical Instructions'
        }
      },
      {
        path: 'commodity-stress',
        component: AgriCommodityStressComponent,
        data: {
          title: 'Commodity Stress'
        }
      },
      {
        path: 'commodity-stress/add-commodity-stress',
        component: AddEditAgriCommodityStressComponent,
        data: {
          title: 'Add Commodity Stress'
        }
      },
      {
        path: 'commodity-stress/edit-commodity-stress/:id',
        component: AddEditAgriCommodityStressComponent,
        data: {
          title: 'Edit Commodity Stress'
        }
      },
      {
        path: 'stage',
        component: AgriStageComponent,
        data: {
          title: 'Stage'
        }
      },
      {
        path: 'stage/add-stage',
        component: AddEditAgriStageComponent,
        data: {
          title: 'Add Stage'
        }
      },
      {
        path: 'stage/edit-stage/:id',
        component: AddEditAgriStageComponent,
        data: {
          title: 'Edit Stage'
        }
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StressRoutingModule {}
