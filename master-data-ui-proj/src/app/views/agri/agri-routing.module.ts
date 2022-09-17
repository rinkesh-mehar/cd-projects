import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgriSeasonComponent } from './agri-season/agri-season.component';
// import { AgriCommodityComponent } from './agri-commodity/agri-commodity.component';
// import { AgriPhenophaseComponent } from './agri-phenophase/agri-phenophase.component';
// import { AgriVarietyComponent } from './agri-variety/agri-variety.component';
// import { AddAgriCommodityComponent } from './add-agri-commodity/add-agri-commodity.component';
// import { EditAgriCommodityComponent } from './edit-agri-commodity/edit-agri-commodity.component';
import { AddAgriSeasonComponent } from './add-agri-season/add-agri-season.component';
import { EditAgriSeasonComponent } from './edit-agri-season/edit-agri-season.component';
// import { AddAgriPhenophaseComponent } from './add-agri-phenophase/add-agri-phenophase.component';
// import { EditAgriPhenophaseComponent } from './edit-agri-phenophase/edit-agri-phenophase.component';
// import { AddAgriVarietyComponent } from './add-agri-variety/add-agri-variety.component';
// import { EditAgriVarietyComponent } from './edit-agri-variety/edit-agri-variety.component';
import { AgriActivityComponent } from './agri-activity/agri-activity.component';
import { AddAgriActivityComponent } from './add-agri-activity/add-agri-activity.component';
import { EditAgriActivityComponent } from './edit-agri-activity/edit-agri-activity.component';
// import { AgriAgrochemicalMasterComponent } from './agri-commodity-agrochemical/agri-commodity-agrochemical.component';
// import { AddAgriAgrochemicalMasterComponent } from './add-agri-commodity-agrochemical/add-agri-commodity-agrochemical.component';
// import { EditAgriAgrochemicalMasterComponent } from './edit-agri-commodity-agrochemical/edit-agri-commodity-agrochemical.component';
import { AgriDisposalMethodComponent } from './agri-disposal-method/agri-disposal-method.component';
import { AddAgriDisposalMethodComponent } from './add-agri-disposal-method/add-agri-disposal-method.component';
import { EditAgriDisposalMethodComponent } from './edit-agri-disposal-method/edit-agri-disposal-method.component';
import { AgriFarmMachineryComponent } from './agri-farm-machinery/agri-farm-machinery.component';
import { AddAgriFarmMachineryComponent } from './add-agri-farm-machinery/add-agri-farm-machinery.component';
import { EditAgriFarmMachineryComponent } from './edit-agri-farm-machinery/edit-agri-farm-machinery.component';
// import { AgriFieldActivityComponent } from './agri-field-activity/agri-field-activity.component';
// import { AddAgriFieldActivityComponent } from './add-agri-field-activity/add-agri-field-activity.component';
// import { EditAgriFieldActivityComponent } from './edit-agri-field-activity/edit-agri-field-activity.component';
import { AgriIrrigationMethodComponent } from './agri-irrigation-method/agri-irrigation-method.component';
import { AddAgriIrrigationMethodComponent } from './add-agri-irrigation-method/add-agri-irrigation-method.component';
import { EditAgriIrrigationMethodComponent } from './edit-agri-irrigation-method/edit-agri-irrigation-method.component';
// import { AgriPlantPartComponent } from './agri-plant-part/agri-plant-part.component';
// import { AddAgriPlantPartComponent } from './add-agri-plant-part/add-agri-plant-part.component';
// import { EditAgriPlantPartComponent } from './edit-agri-plant-part/edit-agri-plant-part.component';
// import { AgriRemedialMeasuresComponent } from './agri-remedial-measures/agri-remedial-measures.component';
// import { AddAgriRemedialMeasuresComponent } from './add-agri-remedial-measures/add-agri-remedial-measures.component';
// import { EditAgriRemedialMeasuresComponent } from './edit-agri-remedial-measures/edit-agri-remedial-measures.component';
import { AgriSeedSourceComponent } from './agri-seed-source/agri-seed-source.component';
import { AddAgriSeedSourceComponent } from './add-agri-seed-source/add-agri-seed-source.component';
import { EditAgriSeedSourceComponent } from './edit-agri-seed-source/edit-agri-seed-source.component';
// import { AgriSeedTreatmentComponent } from './agri-seed-treatment/agri-seed-treatment.component';
// import { AddAgriSeedTreatmentComponent } from './add-agri-seed-treatment/add-agri-seed-treatment.component';
// import { EditAgriSeedTreatmentComponent } from './edit-agri-seed-treatment/edit-agri-seed-treatment.component';
import { AgriSourceOfWaterComponent } from './agri-source-of-water/agri-source-of-water.component';
import { AddAgriSourceOfWaterComponent } from './add-agri-source-of-water/add-agri-source-of-water.component';
import { EditAgriSourceOfWaterComponent } from './edit-agri-source-of-water/edit-agri-source-of-water.component';
// import { AgriStressStageComponent } from './agri-commodity-stress-stage/agri-commodity-stress-stage.component';
// import { EditAgriStressStageComponent } from './edit-agri-commodity-stress-stage/edit-agri-commodity-stress-stage.component';
// import { AgriStressTypeComponent } from './agri-commodity-stress-type/agri-commodity-stress-type.component';
// import { AddAgriStressTypeComponent } from './add-agri-commodity-stress-type/add-agri-commodity-stress-type.component';
// import { EditAgriStressTypeComponent } from './edit-agri-commodity-stress-type/edit-agri-commodity-stress-type.component';
// import { AgriCommodityPlantPartComponent } from './agri-commodity-plant-part/agri-commodity-plant-part.component';
// import { AddAgriCommodityPlantPartComponent } from './add-agri-commodity-plant-part/add-agri-commodity-plant-part.component';
// import { EditAgriCommodityPlantPartComponent } from './edit-agri-commodity-plant-part/edit-agri-commodity-plant-part.component';
// import { EditAgriFertilizerComponent } from './edit-agri-fertilizer/edit-agri-fertilizer.component';
// import { AgriVarietyStressComponent } from './agri-variety-stress/agri-variety-stress.component';
// import { AddAgriVarietyStressComponent } from './add-agri-variety-stress/add-agri-variety-stress.component';
// import { EditAgriVarietyStressComponent } from './edit-agri-variety-stress/edit-agri-variety-stress.component';
// import { AddAgriStressStageComponent } from './add-agri-commodity-stress-stage/add-agri-commodity-stress-stage.component';
// import { AgriGeneralCommodityComponent } from './agri-general-commodity/agri-general-commodity.component';
// import { AddAgriGeneralCommodityComponent } from './add-agri-general-commodity/add-agri-general-commodity.component';
// import { EditAgriGeneralCommodityComponent } from './edit-agri-general-commodity/edit-agri-general-commodity.component';
// import { AgriCommodityClassComponent } from './agri-commodity-class/agri-commodity-class.component';
// import { AddAgriCommodityClassComponent } from './add-agri-commodity-class/add-agri-commodity-class.component';
// import { EditAgriCommodityClassComponent } from './edit-agri-commodity-class/edit-agri-commodity-class.component';
// import { AgriHsCodeComponent } from './agri-hs-code/agri-hs-code.component';
// import { AddAgriHsCodeComponent } from './add-agri-hs-code/add-agri-hs-code.component';
// import { EditAgriHsCodeComponent } from './edit-agri-hs-code/edit-agri-hs-code.component';
// import { AgriCommodityPhenophaseComponent } from './agri-commodity-phenophase/agri-commodity-phenophase.component';
// import { AddAgriCommodityPhenophaseComponent } from './add-agri-commodity-phenophase/add-agri-commodity-phenophase.component';
// import { EditAgriCommodityPhenophaseComponent } from './edit-agri-commodity-phenophase/edit-agri-commodity-phenophase.component';
import { EcosystemListComponent } from './ecosystem/list/list.component';
import { AddEcosystemComponent } from './ecosystem/add/add.component';
import { EditEcosystemComponent } from './ecosystem/edit/edit.component';
import { AgriMeteorologicalWeekComponent } from './agri-meteorological-week/agri-meteorological-week/agri-meteorological-week.component';
import { AddEditAgriMeteorologicalWeekComponent } from './agri-meteorological-week/add-edit-agri-meteorological-week/add-edit-agri-meteorological-week.component';
import { AgriBenchmarkVarietyComponent } from './agri-benchmark-variety/agri-benchmark-variety/agri-benchmark-variety.component';
import { AddEditAgriBenchmarkVarietyComponent } from './agri-benchmark-variety/add-edit-agri-benchmark-variety/add-edit-agri-benchmark-variety.component';
// import { AddPhenophaseDurationComponent } from './add-phenophase-duration/add-phenophase-duration.component';
// import { EditPhenophaseDurationComponent } from './edit-phenophase-duration/edit-phenophase-duration.component';
// import { AgriPhenophaseDurationComponent } from './agri-phenophase-duration/agri-phenophase-duration.component';
// import { AddAgrochemicalApplicationComponent } from './agro-chemical-application-type/add-agrochemical-application/add-agrochemical-application.component';
// import { EditAgrochemicalApplicationComponent } from './agro-chemical-application-type/edit-agrochemical-application/edit-agrochemical-application.component';
// import { StressSeverityComponent } from './stress-severity/stress-severity/stress-severity.component';
// import { AddStressSeverityComponent } from './stress-severity/add-stress-severity/add-stress-severity.component';
// import { AgrochemicalApplicationComponent } from './agro-chemical-application-type/agrochemical-application/agrochemical-application.component';
// import { EditStressSeverityComponent } from './stress-severity/edit-stress-severity/edit-stress-severity.component';
// import { AgrochemicalTypeComponent } from './agrochemical-type/agrochemical-type/agrochemical-type.component';
// import { AddAgrochemicalTypeComponent } from './agrochemical-type/add-agrochemical-type/add-agrochemical-type.component';
// import { EditAgrochemicalTypeComponent } from './agrochemical-type/edit-agrochemical-type/edit-agrochemical-type.component';
// import { CondusiveWeatherComponent } from './condusive-weather/condusive-weather.component';
// import { AddEditCondusiveWeatherComponent } from './condusive-weather/add-edit-condusive-weather/add-edit-condusive-weather.component';
// import { AgriHealthParameterComponent } from './agri-health-parameter/agri-health-parameter/agri-health-parameter.component';
// import { AddEditAgriHealthParameterComponent } from './agri-health-parameter/add-edit-agri-health-parameter/add-edit-agri-health-parameter.component';
// import { AgriHealthComponent } from './agri-health/agri-health/agri-health.component';
// import { AddEditAgriHealthComponent } from './agri-health/add-edit-agri-health/add-edit-agri-health.component';
// import { AgriFavourableWeatherComponent } from './agri-favourable-weather/agri-favourable-weather/agri-favourable-weather.component';
// import { AddEditAgriFavourableWeatherComponent } from './agri-favourable-weather/add-edit-agri-favourable-weather/add-edit-agri-favourable-weather.component';
// import { AgriStressComponent } from './agri-commodity-stress/agri-commodity-stress.component';
// import { AddEditAgriStressComponent } from './agri-commodity-stress/add-edit-agri-commodity-stress/add-edit-agri-commodity-stress.component';
// import { AgriStressControlMeasuresComponent } from './agri-commodity-stress-control-measures/agri-commodity-stress-control-measures/agri-commodity-stress-control-measures.component';
// import { AddEditAgriStressControlMeasuresComponent } from './agri-commodity-stress-control-measures/add-edit-agri-commodity-stress-control-measures/add-edit-agri-commodity-stress-control-measures.component';
// import { AgriRecommendationComponent } from './agri-recommendation/agri-recommendation/agri-recommendation.component';
// import { AddEditAgriRecommendationComponent } from './agri-recommendation/add-edit-agri-recommendation/add-edit-agri-recommendation.component';
// import { StressControlRecommendationComponent } from './agri-commodity-stress-control-recommendation/stress-control-recommendation/stress-control-recommendation.component';
// import { AddEditAgriStressControlRecommendationComponent } from './agri-commodity-stress-control-recommendation/add-edit-agri-commodity-stress-control-recommendation/add-edit-agri-commodity-stress-control-recommendation.component';
// import { AgriQuantityLossChartComponent } from './agri-quantity-loss-chart/agri-quantity-loss-chart/agri-quantity-loss-chart.component';
// import { AddEditAgriQuantityLossChartComponent } from './agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart.component';
// import { AgriStandardQuantityChartComponent } from './agri-standard-quantity-chart/agri-standard-quantity-chart/agri-standard-quantity-chart.component';
// import { AddEditAgriStandardQuantityChartComponent } from './agri-standard-quantity-chart/add-edit-agri-standard-quantity-chart/add-edit-agri-standard-quantity-chart.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Agriculture'
    },
    children: [
      {
        path: '',
        redirectTo: 'season'
      },

      // {
      //   path: 'commodities',
      //   component: AgriCommodityComponent,
      //   data: {
      //     title: 'Commodities'
      //   }
      // },
      // {
      //   path: 'commodities-add',
      //   component: AddAgriCommodityComponent,
      //   data: {
      //     title: 'Add Commodity'
      //   }
      // },
      // {
      //   path: 'commodities-edit/:id',
      //   component: EditAgriCommodityComponent,
      //   data: {
      //     title: 'Edit Commodity'
      //   }
      // },
      {
        path: 'season',
        component: AgriSeasonComponent,
        data: {
          title: 'Seasons'
        }
      },
      {
        path: 'season-add',
        component: AddAgriSeasonComponent,
        data: {
          title: 'Add Season'
        }
      },
      {
        path: 'season-edit/:id',
        component: EditAgriSeasonComponent,
        data: {
          title: 'Edit Season'
        }
      },
      // {
      //   path: 'phenophase',
      //   component: AgriPhenophaseComponent,
      //   data: {
      //     title: 'Phenophases'
      //   }
      // },
      // {
      //   path: 'phenophase-add',
      //   component: AddAgriPhenophaseComponent,
      //   data: {
      //     title: 'Add Phenophase'
      //   }
      // },
      // {
      //   path: 'phenophase-edit/:id',
      //   component: EditAgriPhenophaseComponent,
      //   data: {
      //     title: 'Edit Phenophase'
      //   }
      // },
      // {
      //   path: 'variety',
      //   component: AgriVarietyComponent,
      //   data: {
      //     title: 'Varieties'
      //   }
      // },
      // {
      //   path: 'variety-add',
      //   component: AddAgriVarietyComponent,
      //   data: {
      //     title: 'Add Variety'
      //   }
      // },
      // {
      //   path: 'variety-edit/:id',
      //   component: EditAgriVarietyComponent,
      //   data: {
      //     title: 'Edit Variety'
      //   }
      // },
      {
        path: 'activity',
        component: AgriActivityComponent,
        data: {
          title: 'Activity'
        }
      },
      {
        path: 'activity-add',
        component: AddAgriActivityComponent,
        data: {
          title: 'Add Activity'
        }
      },
      {
        path: 'activity-edit/:id',
        component: EditAgriActivityComponent,
        data: {
          title: 'Edit Activity'
        }
      },
      // {
      //   path: 'agrochemical',
      //   component: AgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'AgroChemicalMaster'
      //   }
      // },
      // {
      //   path: 'agrochemical-add',
      //   component: AddAgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'Add AgroChemicalMaster'
      //   }
      // },
      // {
      //   path: 'agrochemical-edit/:id',
      //   component: EditAgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'Edit AgroChemicalMaster'
      //   }
      // },
      // {
      //   path: 'stress',
      //   component: AgriStressComponent,
      //   data: {
      //     title: 'Stress'
      //   }
      // },
      // {
      //   path: 'stress-add',
      //   component: AddEditAgriStressComponent,
      //   data: {
      //     title: 'Add Stress'
      //   }
      // },
      // {
      //   path: 'stress-edit/:id',
      //   component: AddEditAgriStressComponent,
      //   data: {
      //     title: 'Edit Stress'
      //   }
      // },
      {
        path: 'disposal-method',
        component: AgriDisposalMethodComponent,
        data: {
          title: 'Disposal-Method'
        }
      },
      {
        path: 'disposal-method-add',
        component: AddAgriDisposalMethodComponent,
        data: {
          title: 'Add Disposal-Method'
        }
      },
      {
        path: 'disposal-method-edit/:id',
        component: EditAgriDisposalMethodComponent,
        data: {
          title: 'Edit Disposal-Method'
        }
      },
      {
        path: 'farm-machinery',
        component: AgriFarmMachineryComponent,
        data: {
          title: 'Farm-Machinery'
        }
      },
      {
        path: 'farm-machinery-add',
        component: AddAgriFarmMachineryComponent,
        data: {
          title: 'Add Farm-Machinery'
        }
      },
      {
        path: 'farm-machinery-edit/:id',
        component: EditAgriFarmMachineryComponent,
        data: {
          title: 'Edit Farm-Machinery'
        }
      },
      // {
      //   path: 'fertilizer',
      //   component: AgriFertilizerComponent,
      //   data: {
      //     title: 'Fertilizer'
      //   }
      // },
      // {
      //   path: 'fertilizer-add',
      //   component: AddAgriFertilizerComponent,
      //   data: {
      //     title: 'Add Fertilizer'
      //   }
      // },
      // {
      //   path: 'fertilizer-edit/:id',
      //   component: EditAgriFertilizerComponent,
      //   data: {
      //     title: 'Edit Fertilizer'
      //   }
      // },
      // {
      //   path: 'field-activity',
      //   component: AgriFieldActivityComponent,
      //   data: {
      //     title: 'Field-Activity'
      //   }
      // },
      // {
      //   path: 'field-activity-add',
      //   component: AddAgriFieldActivityComponent,
      //   data: {
      //     title: 'Add Field-Activity'
      //   }
      // },
      // {
      //   path: 'field-activity-edit/:id',
      //   component: EditAgriFieldActivityComponent,
      //   data: {
      //     title: 'Edit Field-Activity'
      //   }
      // },
      {
        path: 'irrigation-method',
        component: AgriIrrigationMethodComponent,
        data: {
          title: 'Irrigation-Method'
        }
      },
      {
        path: 'irrigation-method-add',
        component: AddAgriIrrigationMethodComponent,
        data: {
          title: 'Add Irrigation-Method'
        }
      },
      {
        path: 'irrigation-method-edit/:id',
        component: EditAgriIrrigationMethodComponent,
        data: {
          title: 'Edit Irrigation-Method'
        }
      },
      // {
      //   path: 'commodity-plant-part',
      //   component: AgriCommodityPlantPartComponent,
      //   data: {
      //     title: 'Plant-Part'
      //   }
      // },
      // {
      //   path: 'commodity-plant-part-add',
      //   component: AddAgriCommodityPlantPartComponent,
      //   data: {
      //     title: 'Add Plant-Part'
      //   }
      // },
      // {
      //   path: 'commodity-plant-part-edit/:id',
      //   component: EditAgriCommodityPlantPartComponent,
      //   data: {
      //     title: 'Edit Plant-Part'
      //   }
      // },
      // {
      //   path: 'plant-part',
      //   component: AgriPlantPartComponent,
      //   data: {
      //     title: 'Plant-Part'
      //   }
      // },
      // {
      //   path: 'plant-part-add',
      //   component: AddAgriPlantPartComponent,
      //   data: {
      //     title: 'Add Plant-Part'
      //   }
      // },
      // {
      //   path: 'plant-part-edit/:id',
      //   component: EditAgriPlantPartComponent,
      //   data: {
      //     title: 'Edit Plant-Part'
      //   }
      // },
      // {
      //   path: 'agrochemical-brand',
      //   component: AgriRemedialMeasuresComponent,
      //   data: {
      //     title: 'Agrochemical Brand'
      //   }
      // },
      // {
      //   path: 'agrochemical-brand-add',
      //   component: AddAgriRemedialMeasuresComponent,
      //   data: {
      //     title: 'Add Agrochemical Brand'
      //   }
      // },
      // {
      //   path: 'agrochemical-brand-edit/:id',
      //   component: AddAgriRemedialMeasuresComponent,
      //   data: {
      //     title: 'Edit Agrochemical Brand'
      //   }
      // },
      {
        path: 'seed-source',
        component: AgriSeedSourceComponent,
        data: {
          title: 'Seed-Source'
        }
      },
      {
        path: 'seed-source-add',
        component: AddAgriSeedSourceComponent,
        data: {
          title: 'Add Seed-Source'
        }
      },
      {
        path: 'seed-source-edit/:id',
        component: EditAgriSeedSourceComponent,
        data: {
          title: 'Edit Seed-Source'
        }
      },
      // {
      //   path: 'seed-treatment',
      //   component: AgriSeedTreatmentComponent,
      //   data: {
      //     title: 'SeedTreatment'
      //   }
      // },
      // {
      //   path: 'seed-treatment-add',
      //   component: AddAgriSeedTreatmentComponent,
      //   data: {
      //     title: 'Add SeedTreatment'
      //   }
      // },
      // {
      //   path: 'seed-treatment-edit/:id',
      //   component: EditAgriSeedTreatmentComponent,
      //   data: {
      //     title: 'Edit SeedTreatment'
      //   }
      // },
      {
        path: 'source-of-water',
        component: AgriSourceOfWaterComponent,
        data: {
          title: 'Source-Of-Water'
        }
      },
      {
        path: 'source-of-water-add',
        component: AddAgriSourceOfWaterComponent,
        data: {
          title: 'Add Source-Of-Water'
        }
      },
      {
        path: 'source-of-water-edit/:id',
        component: EditAgriSourceOfWaterComponent,
        data: {
          title: 'Edit Source-Of-Water'
        }
      },
      // {
      //   path: 'stress-stage',
      //   component: AgriStressStageComponent,
      //   data: {
      //     title: 'Stress-Stage'
      //   }
      // },
      // {
      //   path: 'stress-stage-add',
      //   component: AddAgriStressStageComponent,
      //   data: {
      //     title: 'Add Stress-Stage'
      //   }
      // },
      // {
      //   path: 'stress-stage-edit/:id',
      //   component: EditAgriStressStageComponent,
      //   data: {
      //     title: 'Edit Stress-Stage'
      //   }
      // },
      // {
      //   path: 'stress-type',
      //   component: AgriStressTypeComponent,
      //   data: {
      //     title: 'Stress-Type'
      //   }
      // },
      // {
      //   path: 'stress-type-add',
      //   component: AddAgriStressTypeComponent,
      //   data: {
      //     title: 'Add Stress-Type'
      //   }
      // },
      // {
      //   path: 'stress-type-edit/:id',
      //   component: EditAgriStressTypeComponent,
      //   data: {
      //     title: 'Edit Stress-Type'
      //   }
      // },
      // {
      //   path: 'variety-stress',
      //   component: AgriVarietyStressComponent,
      //   data: {
      //     title: 'Variety-Stress'
      //   }
      // },
      // {
      //   path: 'variety-stress-add',
      //   component: AddAgriVarietyStressComponent,
      //   data: {
      //     title: 'Add Variety-Stress'
      //   }
      // },
      // {
      //   path: 'variety-stress-edit/:id',
      //   component: EditAgriVarietyStressComponent,
      //   data: {
      //     title: 'Edit Variety-Stress'
      //   }
      // },
      // {
      //   path: 'general-commodity',
      //   component: AgriGeneralCommodityComponent,
      //   data: {
      //     title: 'General-Commodity'
      //   }
      // },
      // {
      //   path: 'general-commodity-add',
      //   component: AddAgriGeneralCommodityComponent,
      //   data: {
      //     title: 'Add General-Commodity'
      //   }
      // },
      // {
      //   path: 'general-commodity-edit/:id',
      //   component: EditAgriGeneralCommodityComponent,
      //   data: {
      //     title: 'Edit General-Commodity'
      //   }
      // },
      // {
      //   path: 'commodity-class',
      //   component: AgriCommodityClassComponent,
      //   data: {
      //     title: 'Commodity-Class'
      //   }
      // },
      // {
      //   path: 'commodity-class-add',
      //   component: AddAgriCommodityClassComponent,
      //   data: {
      //     title: 'Add Commodity-Class'
      //   }
      // },
      // {
      //   path: 'commodity-class-edit/:id',
      //   component: EditAgriCommodityClassComponent,
      //   data: {
      //     title: 'Edit Commodity-Class'
      //   }
      // },
      // {
      //   path: 'hs-code',
      //   component: AgriHsCodeComponent,
      //   data: {
      //     title: 'HS-Code'
      //   }
      // },
      // {
      //   path: 'hs-code-add',
      //   component: AddAgriHsCodeComponent,
      //   data: {
      //     title: 'Add HS-Code'
      //   }
      // },
      // {
      //   path: 'hs-code-edit/:id',
      //   component: EditAgriHsCodeComponent,
      //   data: {
      //     title: 'Edit HS-Code'
      //   }
      // },
      // {
      //   path: 'commodity-phenophase',
      //   component: AgriCommodityPhenophaseComponent,
      //   data: {
      //     title: 'Commodity-Phenophase'
      //   }
      // },
      // {
      //   path: 'commodity-phenophase-add',
      //   component: AddAgriCommodityPhenophaseComponent,
      //   data: {
      //     title: 'Add Commodity-Phenophase'
      //   }
      // },
      // {
      //   path: 'commodity-phenophase-edit/:id',
      //   component: EditAgriCommodityPhenophaseComponent,
      //   data: {
      //     title: 'Edit Commodity-Phenophase'
      //   }
      // },
      {
        path: 'ecosystem',
        component: EcosystemListComponent,
        data: {
          title: 'Ecosystem'
        }
      },
      {
        path: 'ecosystem-add',
        component: AddEcosystemComponent,
        data: {
          title: 'Add Ecosystem'
        }
      },
      {
        path: 'ecosystem-edit/:id',
        component: EditEcosystemComponent,
        data: {
          title: 'Edit Ecosystem'
        }
      },
      // {
      //   path: 'phenophase-duration',
      //   component: AgriPhenophaseDurationComponent,
      //   data: {
      //     title: 'Phenophase-Duration'
      //   }
      // },
      // {
      //   path: 'phenophase-duration-add',
      //   component: AddPhenophaseDurationComponent,
      //   data: {
      //     title: 'Add Phenophase-Duration'
      //   }
      // },
      // {
      //   path: 'phenophase-duration-edit/:id',
      //   component: EditPhenophaseDurationComponent,
      //   data: {
      //     title: 'Edit Phenophase-Duration'
      //   }
      // },
      // {
      //   path: 'agrochemical-application-type',
      //   component: AgrochemicalApplicationComponent,
      //   data: {
      //     title: 'AgroChemical-Application'
      //   }
      // },
      // {
      //   path: 'agrochemical-application-type-add',
      //   component: AddAgrochemicalApplicationComponent,
      //   data: {
      //     title: 'Add AgroChemical-Application'
      //   }
      // },
      // {
      //   path: 'agrochemical-application-type-edit/:id',
      //   component: EditAgrochemicalApplicationComponent,
      //   data: {
      //     title: 'Edit AgroChemical-Application'
      //   }
      // },
      // {
      //   path: 'stress-severity',
      //   component: StressSeverityComponent,
      //   data: {
      //     title: 'Stress-Severity'
      //   }
      // },
      // {
      //   path: 'stress-severity-add',
      //   component: AddStressSeverityComponent,
      //   data: {
      //     title: 'Add Stress-Severity'
      //   }
      // },
      // {
      //   path: 'stress-severity-edit/:id',
      //   component: EditStressSeverityComponent,
      //   data: {
      //     title: 'Edit Stress-Severity'
      //   }
      // },
      // {
      //   path: 'agrochemical',
      //   component: AgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'AgroChemical'
      //   }
      // },
      // {
      //   path: 'agrochemical-add',
      //   component: AddAgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'Add AgroChemical'
      //   }
      // },
      // {
      //   path: 'agrochemical-edit/:id',
      //   component: EditAgriAgrochemicalMasterComponent,
      //   data: {
      //     title: 'Edit AgroChemical'
      //   }
      // },
      // {
      //   path: 'agrochemical-type',
      //   component: AgrochemicalTypeComponent,
      //   data: {
      //     title: 'AgroChemical-Type'
      //   }
      // },
      // {
      //   path: 'agrochemical-type-add',
      //   component: AddAgrochemicalTypeComponent,
      //   data: {
      //     title: 'Add AgroChemical-Type'
      //   }
      // },
      // {
      //   path: 'agrochemical-type-edit/:id',
      //   component: EditAgrochemicalTypeComponent,
      //   data: {
      //     title: 'Edit AgroChemical-Type'
      //   }
      // },
      // {
      //   path: 'condusive-weather',
      //   component: CondusiveWeatherComponent,
      //   data: {
      //     title: 'Condusive Weather'
      //   }
      // },
      // {
      //   path: 'condusive-weather-add',
      //   component: AddEditCondusiveWeatherComponent,
      //   data: {
      //     title: 'Add Condusive Weather'
      //   }
      // },
      // {
      //   path: 'condusive-weather-edit/:id',
      //   component: AddEditCondusiveWeatherComponent,
      //   data: {
      //     title: 'Edit Condusive Weather'
      //   }
      // },
      // {
      //   path: 'health-parameter',
      //   component: AgriHealthParameterComponent,
      //   data: {
      //     title: 'Health-Parameter'
      //   }
      // },
      // {
      //   path: 'health-parameter-add',
      //   component: AddEditAgriHealthParameterComponent,
      //   data: {
      //     title: 'Add Health-Parameter'
      //   }
      // },
      // {
      //   path: 'health-parameter-edit/:id',
      //   component: AddEditAgriHealthParameterComponent,
      //   data: {
      //     title: 'Edit Health-Parameter'
      //   }
      // },

      // {
      //   path: 'health-parameter',
      //   component: AgriHealthParameterComponent,
      //   data: {
      //     title: 'Health-Parameter'
      //   }
      // },
      // {
      //   path: 'health-parameter-add',
      //   component: AddEditAgriHealthParameterComponent,
      //   data: {
      //     title: 'Add Health-Parameter'
      //   }
      // },
      // {
      //   path: 'health-parameter-edit/:id',
      //   component: AddEditAgriHealthParameterComponent,
      //   data: {
      //     title: 'Edit Health-Parameter'
      //   }
      // },
      // {
      //   path: 'health',
      //   component: AgriHealthComponent,
      //   data: {
      //     title: 'Health'
      //   }
      // },
      // {
      //   path: 'health-add',
      //   component: AddEditAgriHealthComponent,
      //   data: {
      //     title: 'Add Health'
      //   }
      // },
      // {
      //   path: 'health-edit/:id',
      //   component: AddEditAgriHealthComponent,
      //   data: {
      //     title: 'Edit Health'
      //   }
      // },
      // {
      //   path: 'favourable-weather',
      //   component: AgriFavourableWeatherComponent,
      //   data: {
      //     title: 'Favourable Weather'
      //   }
      // },
      // {
      //   path: 'favourable-weather-add',
      //   component: AddEditAgriFavourableWeatherComponent,
      //   data: {
      //     title: 'Add Favourable Weather'
      //   }
      // },
      // {
      //   path: 'favourable-weather-edit/:id',
      //   component: AddEditAgriFavourableWeatherComponent,
      //   data: {
      //     title: 'Edit Favourable Weather'
      //   }
      // },
      // {
      //   path: 'stress-control-measures',
      //   component: AgriStressControlMeasuresComponent,
      //   data: {
      //     title: 'Stress ControlMeasures'
      //   }
      // },
      // {
      //   path: 'stress-control-measures-add',
      //   component: AddEditAgriStressControlMeasuresComponent,
      //   data: {
      //     title: 'Add Stress ControlMeasures'
      //   }
      // },
      // {
      //   path: 'stress-control-measures-edit/:id',
      //   component: AddEditAgriStressControlMeasuresComponent,
      //   data: {
      //     title: 'Edit Stress ControlMeasures'
      //   }
      // },

      // {
      //   path: 'recommendation',
      //   component: AgriRecommendationComponent,
      //   data: {
      //     title: 'Recommendation'
      //   }
      // },
      // {
      //   path: 'recommendation-add',
      //   component: AddEditAgriRecommendationComponent,
      //   data: {
      //     title: 'Add Recommendation'
      //   }
      // },
      // {
      //   path: 'recommendation-edit/:id',
      //   component: AddEditAgriRecommendationComponent,
      //   data: {
      //     title: 'Edit Recommendation'
      //   }
      // },

      // {
      //   path: 'stress-control-recommendation',
      //   component: StressControlRecommendationComponent,
      //   data: {
      //     title: 'Stress Control Recommendation'
      //   }
      // },
      // {
      //   path: 'stress-control-recommendation-add',
      //   component: AddEditAgriStressControlRecommendationComponent,
      //   data: {
      //     title: 'Add Stress Control Recommendation'
      //   }
      // },
      // {
      //   path: 'stress-control-recommendation-edit/:id',
      //   component: AddEditAgriStressControlRecommendationComponent,
      //   data: {
      //     title: 'Edit Stress Control Recommendation'
      //   }
      // },

      // {

      //   path: 'standard-quantity-chart',
      //   component: AgriStandardQuantityChartComponent,
      //   data: {
      //     title: 'Standard Quantity Chart'
      //   }
      // },
      // {
      //   path: 'standard-quantity-chart-add',
      //   component: AddEditAgriStandardQuantityChartComponent,
      //   data: {
      //     title: 'Add Standard Quantity Chart'
      //   }
      // },
      // {
      //   path: 'standard-quantity-chart-edit/:id',
      //   component: AddEditAgriStandardQuantityChartComponent,
      //   data: {
      //     title: 'Edit Standard Quantity Chart'
      //   }
      // },

      // {
      //   path: 'quantity-loss-chart',
      //   component: AgriQuantityLossChartComponent,
      //   data: {
      //     title: 'Quantity Loss Chart'
      //   }
      // },
      // {
      //   path: 'quantity-loss-chart-add',
      //   component: AddEditAgriQuantityLossChartComponent,
      //   data: {
      //     title: 'Add Quantity Loss Chart'
      //   }
      // },
      // {
      //   path: 'quantity-loss-chart-edit/:id',
      //   component: AddEditAgriQuantityLossChartComponent,
      //   data: {
      //     title: 'Edit Quantity Loss Chart'
      //   }
      // },

      {
        path: 'meteorological-week',
        component: AgriMeteorologicalWeekComponent,
        data: {
          title: 'Meteorological Week'
        }
      },
      {
        path: 'meteorological-week-add',
        component: AddEditAgriMeteorologicalWeekComponent,
        data: {
          title: 'Add Meteorological Week'
        }
      },
      {
        path: 'meteorological-week-edit/:id',
        component: AddEditAgriMeteorologicalWeekComponent,
        data: {
          title: 'Edit Meteorological Week'
        }
      },

      {
        path: 'benchmark-variety',
        component: AgriBenchmarkVarietyComponent,
        data: {
          title: 'Benchmark Variety'
        }
      },
      {
        path: 'benchmark-variety-add',
        component: AddEditAgriBenchmarkVarietyComponent,
        data: {
          title: 'Add Benchmark Variety'
        }
      },
      {
        path: 'benchmark-variety-edit/:id',
        component: AddEditAgriBenchmarkVarietyComponent,
        data: {
          title: 'Edit Benchmark Variety'
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgriRoutingModule { }
