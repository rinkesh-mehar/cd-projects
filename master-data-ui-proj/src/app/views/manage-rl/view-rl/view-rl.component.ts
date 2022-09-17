import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RlUser } from '../model/rlUser';
import { RlService } from '../service/rl.service';

@Component({
  selector: 'app-view-rl',
  templateUrl: './view-rl.component.html',
  styleUrls: ['./view-rl.component.scss']
})
export class ViewRlComponent implements OnInit {

  constructor(private rlService: RlService, private actRoute: ActivatedRoute, private router: Router) { }

  viewId: string;
  rlUser: RlUser;

  ngOnInit(): void {
    this.viewId = this.actRoute.snapshot.paramMap.get('id');
    if (this.viewId) {
      this.rlService.getRlManageId(this.viewId).subscribe(data => {
        this.rlUser = data;
      });
      console.log('id -> ' + this.viewId);
    }
  }

  onClickCancel() {
    this.router.navigate(['/rl/rl-users']);
  }

}
