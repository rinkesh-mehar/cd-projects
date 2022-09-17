import { Component, OnInit, ViewChild } from '@angular/core';
import { moveItemInArray, CdkDragDrop } from '@angular/cdk/drag-drop';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { newsReportsService } from '../../services/news-reports.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cropdata-latest-news-priority',
  templateUrl: './cropdata-latest-news-priority.component.html',
  styleUrls: ['./cropdata-latest-news-priority.component.css']
})
export class CropdataLatestNewsPriorityComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  NewsList: any = [];
  imgUrl : string = '';
  searchText: any = '';

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  constructor(public newsService: newsReportsService,  public router: Router) { }

  ngOnInit() {
    this.cropdataLatestNewsPriorityList();
  }

  cropdataLatestNewsPriorityList() {
    return this.newsService.cropdataLatestNewsPriorityList().subscribe((data: {}) => {
        this.NewsList = data;
    });
}

getImageUrl(event: any){
  this.imgUrl = event.target.src;
  console.log("image found..." + this.imgUrl);
}

searchNews() {
  // this.selectedPage = 1;
  console.log(this.searchText);
  this.cropdataLatestNewsPriorityList();
}

onDrop(event: CdkDragDrop<string[]>) {
  moveItemInArray(this.NewsList, event.previousIndex, event.currentIndex);
  this.NewsList.forEach((news, idx) => {
    news.priority = idx + 1;
  });
}

submit(data){
  // for(let news of data){
  //   console.log(news);
  //   }

  return this.newsService.updateCropdataLatestNewsPriority(data).subscribe( res => {
    this.isSubmitted = true;
    if (res) {
        this.isSuccess = res.success;
        if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
        } else {
            this.errorModal.showModal('ERROR', res.error, '');
        }
    }
});
}

modalSuccess($event: any) {
  // this.router.navigate(['/cropdata-portal/news']);
}

}
