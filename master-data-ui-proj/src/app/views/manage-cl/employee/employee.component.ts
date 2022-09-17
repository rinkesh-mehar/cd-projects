import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {
  employeeForm: FormGroup;
  officialDataFrom: FormGroup;
 
  employeeDetailHidden: boolean = true;
  constructor(public fb: FormBuilder) { }
 

  RoleList: any  = [{id:1,name:"Intern"},{ id:2,name:"Developer"},{ id:3,name:"Manager"},{ id:3,name:"HR"},{ id:3,name:"Admin"}];
  OfficeList: any =[{id:1,name:"Nagpur"},{ id:2,name:"Chandigarh"}];
  DepartmentList: any = [{id:1,name:"AgData"},{ id:2,name:"DrKrishi"},{ id:3,name:"Agriota"},{ id:4,name:"Admin"},{ id:5,name:"Data Enter"}]
  DesignationList: any =[{id:1,name:"Developer"},{ id:2,name:"Intern"},{ id:3,name:"HR"},{ id:4,name:"Opertor"},{ id:5,name:"Manager"}]
  ReportingManagerList: any =[{id:1,name:"Nilanjan Sinha"},{ id:2,name:"Pallavi"}]
  ngOnInit(): void {
    this.CreateEmployeeFG();
    this.EmployeeDetailFG();
  }

  CreatempForm()
  {
    console.log("inside submitForm");
  
    for(let controller in this.employeeForm.controls){

      this.employeeForm.get(controller).markAsTouched();
      
    }
   
    if(this.employeeForm.invalid){
      console.log("inside 1st if");
      return;
    }
    this.employeeDetailHidden = false;
  }

  submitForm()
  {
   
    for(let controller in this.officialDataFrom.controls){

      this.officialDataFrom.get(controller).markAsTouched();
      
    }
   
    if(this.officialDataFrom.invalid){
      console.log("inside 1st if");
      return;
    }
   
  }
  
 

  CreateEmployeeFG()
  {
    this.employeeForm = this.fb.group({
      EmployeeName: ['', Validators.required],
      EmployeeRole: ['', Validators.required],
      OfficailEmail: ['', [Validators.required, Validators.pattern('^[a-z0-9.]+@cropdata\.[a-z]{2,3}$')]],
     
      
    })
  }
  EmployeeDetailFG()
  {
    this.officialDataFrom = this.fb.group({
      PersonalEmail: ['',[Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      primaryNumber: ['', [Validators.required, Validators.pattern('^[0-9 + ( ) -]*$')]],
      office: ['', Validators.required],
      department: ['', Validators.required],
      designation: ['', Validators.required],
      reportingManager: ['', Validators.required],
      mctLink: ['', Validators.required],
     
     
      
    })
  }


 
}
