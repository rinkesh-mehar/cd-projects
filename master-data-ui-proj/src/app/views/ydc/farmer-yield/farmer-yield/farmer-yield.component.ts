import { Component, OnInit } from '@angular/core';
import { FarmerLeadsService } from '../../services/farmer-leads.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-farmer-yield',
  templateUrl: './farmer-yield.component.html',
  styleUrls: ['./farmer-yield.component.scss']
})
export class FarmerYieldComponent implements OnInit {

  FarmerYieldList: any = [];


  ngOnInit() {
    var uuid = this.actRoute.snapshot.paramMap.get('id');
    this.loadAllFarmerYDC(uuid);
  }

  constructor(
    private actRoute: ActivatedRoute,
    public farmerLeadsService: FarmerLeadsService
  ) { }

  // FarmerYDC list
  loadAllFarmerYDC(uuid) {
    return this.farmerLeadsService.GetFarmerYDCById(uuid).subscribe((data: {}) => {
      this.FarmerYieldList = data;
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

}
