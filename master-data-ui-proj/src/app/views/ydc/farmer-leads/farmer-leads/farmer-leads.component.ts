import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { FarmerLeadsService } from '../../services/farmer-leads.service';

@Component({
  selector: 'app-farmer-leads',
  templateUrl: './farmer-leads.component.html',
  styleUrls: ['./farmer-leads.component.scss']
})
export class FarmerLeadsComponent implements OnInit {

  farmerLeadsList: any = [];


  ngOnInit() {
    this.loadAllFarmerLeads();
  }

  constructor(
    public farmerLeadsService: FarmerLeadsService
  ) { }

  // FarmerLeads list
  loadAllFarmerLeads() {
    return this.farmerLeadsService.GetAllFarmerLeads().subscribe((data: {}) => {
      this.farmerLeadsList = data;
    })
  }

  // // Delete Company
  // deleteCompany(data) {
  //   var index = index = this.companyList.map(x => { return x.name }).indexOf(data.name);
  //   return this.FarmerLeadsService.DeleteCompany(data.id).subscribe(res => {
  //     this.companyList.splice(index, 1)
  //     console.log('Company deleted!')
  //   })
  // }

  sortData(sort: Sort) {
    const data = this.farmerLeadsList.slice();
    if (!sort.active || sort.direction == '') {
      this.farmerLeadsList = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.farmerLeadsList = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
          case 'farmerName':
            return compare(firstValue.farmerName, secondValue.farmerName, isAsc);
        case 'referenceName':
          return compare(firstValue.referenceName, secondValue.referenceName, isAsc);
        case 'referenceMobile':
          return compare(firstValue.referenceMobile, secondValue.referenceMobile, isAsc);
        case 'govtProof':
          return compare(firstValue.govtProof, secondValue.govtProof, isAsc);
        case 'language':
          return compare(firstValue.language, secondValue.language, isAsc);
          case 'ownLandSize':
            return compare(firstValue.ownLandSize, secondValue.ownLandSize, isAsc);
          case 'irrigatedLandSize':
          return compare(firstValue.irrigatedLandSize, secondValue.irrigatedLandSize, isAsc);
          case 'vipDesignation':
          return compare(firstValue.vipDesignation, secondValue.vipDesignation, isAsc);
          case 'vipStatus':
          return compare(firstValue.vipStatus, secondValue.vipStatus, isAsc);
        default:
          return 0;
      }
    });
  }
  

}
