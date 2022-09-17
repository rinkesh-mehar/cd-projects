// import { Component, OnInit } from '@angular/core';
// import { Router } from '@angular/router';
// import { ActivatedRoute } from '@angular/router';
// import { ValidateStress } from '../../models/ValidateStress';
// import { ValidateStressService } from '../../services/validate-stress.service';
// import { FormGroup } from '@angular/forms';
//
// @Component({
//   selector: 'app-edit-validate-stress',
//   templateUrl: './edit-validate-stress.component.html',
//   styleUrls: ['./edit-validate-stress.component.scss']
// })
// export class EditValidateStressComponent implements OnInit {
//
//   constructor(private validateStressService: ValidateStressService, private actRoute: ActivatedRoute, private router: Router) { }
//
//   id: string;
//   validateStress: ValidateStress;
//   stressID: any =[];
//
//
//
//
//   ngOnInit(): void {
//
//     //Get Case Details
//     this.id = this.actRoute.snapshot.paramMap.get('id');
//     if (this.id) {
//       this.validateStressService.getValidateStressCaseDetails(this.id).subscribe(data => {
//         this.validateStress = data;
//       });
//       console.log('id -> ' + this.id);
//     }
//   }
//
// }
