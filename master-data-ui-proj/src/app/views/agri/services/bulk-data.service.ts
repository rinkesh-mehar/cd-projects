import { Injectable, ViewChild } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
declare var $
@Injectable({
  providedIn: 'root'
})
export class BulkDataService {
  strControlRecStatus: any = [];
  responseSuccess: any;

  constructor(private http: HttpClient) { }
 
  public disbled:any = true
    // Base url
    baseUrl = environment.apiUrl+'/api/util/update-status';
    getData(data): Observable<any>{
      return this.http.put<any>(this.baseUrl,data)
    }

    checked(ele){

        var parentCheckbox = (document.querySelector('thead th input') as HTMLInputElement).checked
        if(!parentCheckbox){
          var checkboxes = document.querySelectorAll('input:checked');
              if (checkboxes.length > 0) {
                          this.disbled = false
              } else {
                          this.disbled = true
              }

              if(checkboxes.length > 1){
                $('[title="Edit"]').prop("disabled", "true")
                $('[title="Image Preview"]').prop("disabled", "true")
              }else {
                $('[title="Edit"]').removeAttr("disabled")
                $('[title="Image Preview"]').removeAttr("disabled")
              }
        }

      
     
    }
    
    checkAll(ele) {

      var checkboxes = document.querySelectorAll('input');
      if (ele.target.checked) {
          for (var i = 0; i < checkboxes.length; i++) {
              if (checkboxes[i].type == 'checkbox') {
                  $(checkboxes[i]).closest('tr').find('[title="Edit"]').prop("disabled", "true")
                  $(checkboxes[i]).closest('tr').find('[title="Image Preview"]').prop("disabled", "true")
                  checkboxes[i].checked = true;
                  this.disbled = false


              }
          }
      } else {
          for (var i = 0; i < checkboxes.length; i++) {
              if (checkboxes[i].type == 'checkbox') {
                $(checkboxes[i]).closest('tr').find('[title="Edit"]').removeAttr("disabled")
                $(checkboxes[i]).closest('tr').find('[title="Image Preview"]').removeAttr("disabled")                
                  checkboxes[i].checked = false;
                  this.disbled = true

              }
          }
      }
  }

    
}
