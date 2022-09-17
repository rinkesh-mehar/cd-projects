import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NdviService } from '../services/ndvi.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { KmlStatus } from '../models/KmlStatus';
import { SentinalDownloadData } from '../models/SentinalDownloadData';
import { SentinelAnalysedData } from '../models/SentinelAnalysedData';
import { BenchmarkNDVIData } from '../models/BenchmarkNDVIData';

@Component({
  selector: 'app-ndvi',
  templateUrl: './ndvi.component.html',
  styleUrls: ['./ndvi.component.scss']
})
export class NdviComponent implements OnInit {
  @ViewChild('kmlStatusModal') public kmlStatusModal: ModalDirective;


  NDVIData: any;
  recivedKmlLabels: any = [];
  recivedKmlDatasets: any = [];
  onbordedKmlLabels: any = [];
  onboradedKmlDatasets: any = [];
  dataRetrivalType: string = 'day';
  onbordedDataLabel: any = [];
  onbordedDataDatasets: any = [];

  kmlStatusLabel: any = [];
  kmlStatusData: any = [];

  sentinelDownloadedData: any = [];
  sentinelDownloadedLabel: any = [];

  sentinelAnalysedData: any = [];
  sentinelAnalysedLabel: any = [];

  benchmarkNDVIData: any = [];
  benchmarkNDVILabel: any = [];

  sentinelDownloadedLast12HourlyDataLabel: any = [];
  sentinelDownloadedLast12HourlyDatasets: any = [];

  sentinelAnalysedLast12HourlyDataLabel: any = [];
  sentinelAnalysedLast12HourlyDatasets: any = [];

  processedNDVILast12HourlyDataLabel: any = [];
  processedNDVILast12HourlyDatasets: any = [];

  // hourlyData: any = [];
  // hourlyDatasets: any = [];

  public pieChartType = 'pie';

  public doughnutChartType = 'doughnut';

  public recivedKmlColours: Array<any> = [
    { // grey
      backgroundColor: 'rgba(99, 194, 222,0.5)',
      borderColor: 'rgba(99, 194, 222,2)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];

  // barChart1/////
  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  public barChartType = 'bar';
  public barChartLegend = false;

  /// barChart2///
  public barChart2Options: any = {
    scaleShowVerticalLines2: false,
    responsive: true
  };

  public barChart2Type = 'bar';
  public barChart2Legend = false;

  // barChart3/////
  public barChart3Options: any = {
    scaleShowVerticalLines3: false,
    responsive: true
  };
  public barChart3Type = 'bar';
  public barChart3Legend = false;

  // barChart4/////
  public barChart4Options: any = {
    scaleShowVerticalLines4: false,
    responsive: true
  };
  public barChart4Type = 'bar';
  public barChart4Legend = false;

  kmlStatusList: Array<KmlStatus>;
  kmlStat: any;
  SentinalDownloadDataList:any = [];
  SentinalDownload: any;
  SentinelAnalysedDataList: any = [];
  SentinalAnalysed: any;
  BenchmarkNDVIDataList : any = [];
  BenchmarkNDVI :any;

  ngOnInit() {
    this.getDisplayData();
    this.getWeeklyOfDayKMLOnboardedData();
    this.getLast12HourlyData();
    this.getKmlStartPieChart();
    this.getSentinelDownloadedData();
    this.getSentinelAnalysedData();
    this.getBenchmarkNDVI();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public ndviService: NdviService
  ) { }

  public chartClicked(e: any): void {
    console.log(e);
  }//chartClicked

  public chartHovered(e: any): void {
    console.log(e);
  }//chartHovered

  todayButtonClick() {
    this.dataRetrivalType = 'day';
    this.getDisplayData();
  }//todayButtonClick

  monthlyButtonClick() {
    this.dataRetrivalType = 'month';
    this.getDisplayData();
  }//monthlyButtonClick

  weeklyButtonClick() {
    this.dataRetrivalType = 'week';
    this.getDisplayData();
  }//weeklyButtonClick

  getNDVITodayData() {
    return this.ndviService.GetNdviTodaysData().subscribe((data) => {
      console.log(data);
      this.NDVIData = data;
    })
  }//getNDVITodayData

  getNDVIMonthlyData() {
    return this.ndviService.GetNdviMonthlyAndYearlyData().subscribe((data) => {
      console.log(data);
      this.NDVIData = data;
    })
  }//getNDVIMonthlyData

  getNDVIWeeklyData() {
    return this.ndviService.GetNDVIWeeklyData().subscribe((data) => {
      console.log(data);
      this.NDVIData = data;
    })
  }//getNDVIWeeklyData

  getTodayData() {
    this.getNDVITodayData();
    // this.getTodayPieChartNDVIData();
  }//getTodayData

  getMonthlyData() {
    this.getNDVIMonthlyData();
    // this.getMonthlyPieChartNDVIData();
  }//getMonthlyData

  getWeeklyData() {
    this.getNDVIWeeklyData();
    // this.getWeeklyPieChartNDVIData();
  }//getWeeklyData

  public getDisplayData() {
    if (this.dataRetrivalType == 'day') {
      this.getTodayData();

    } else if (this.dataRetrivalType == 'month') {
      this.getMonthlyData();

    } else if (this.dataRetrivalType == 'week') {
      this.getWeeklyData();
    }
  }//getDisplayData


  // public getKMLDisplayData() {
  //   if (this.dataRetrivalType == 'day') {
  //     this.getTodayKmlStatusData();

  //   } else if (this.dataRetrivalType == 'month') {
  //     this.getMonthlyKmlStatusData();

  //   } else if (this.dataRetrivalType == 'week') {
  //     this.getWeeklyKmlStatusData();
  //   }
  // }//getTodayPieChartNDVIData

  getTodayKmlStatusData() {

    this.kmlStatusData = [];
    this.kmlStatusLabel = [];
    return this.ndviService.GetTodayKMLData().subscribe((data) => {
      this.kmlStatusList = data.RegionData;
      this.kmlStat = data.Stat;
      this.kmlStatusData.push(data.Stat.totalTimeTaken);
      this.kmlStatusData.push(data.Stat.timeToComplete);
      // this.kmlStatusData.push(data.Stat.timePerKml);
      this.kmlStatusLabel.push('Total Time Taken');
      this.kmlStatusLabel.push('Time To Complete');
    })
  }//todayKmlDataClickButton

  getKmlStartPieChart() {
    return this.ndviService.GetTodayKMLData().subscribe((data) => {
      this.kmlStatusList = data.KmlStartData;
    })
  }//todayKmlDataClickButton


  getSentinelDownloadedData() {

    this.sentinelDownloadedData = [];
    this.sentinelDownloadedLabel = [];
    return this.ndviService.GetTimeRequiredSentinalDownload().subscribe((data) => {
      this.SentinalDownloadDataList = data.SentinalDownload;
      // this.sentinelDownloadedData.push(data.SentinalDownload.newCount);
      // this.sentinelDownloadedData.push(data.SentinalDownload.downloadCompleteSentinalCount);
      // this.sentinelDownloadedData.push(data.SentinalDownload.downloadPendingSentinalCount);
      this.sentinelDownloadedData.push(data.SentinalDownload.totalTimeTakenForCompleteSentinal);
      this.sentinelDownloadedData.push(data.SentinalDownload.timeToCompleteSentinal);
       this.sentinelDownloadedLabel.push('Total Time Taken For Complete Sentinal');  
        this.sentinelDownloadedLabel.push('Time To Complete Sentinal');                                                                                                         
    })
  }//getSentinelDownloadedData

  // getSentinelDownloadedDataChart() {
  //   return this.ndviService.GetTimeRequiredSentinalDownload().subscribe((data) => {
  //     this.SentinalDownloadDataList = data.SentinalDownload;
  //   })
  // }

  getSentinelAnalysedData(){

    this.sentinelAnalysedData = [];
    this.sentinelAnalysedLabel = [];
    return this.ndviService.GetTimeRequiredSentinalAnalyse().subscribe((data) => {
      this.SentinelAnalysedDataList = data.SentinalAnalysed;
      this.sentinelAnalysedData.push(data.SentinalAnalysed.totalTimeToCompleteSentinalCount);
      this.sentinelAnalysedData.push(data.SentinalAnalysed.timeToCompleteAnalyzeSentinal);       
      this.sentinelAnalysedLabel.push('Total Time To Complete Sentinal Count');
      this.sentinelAnalysedLabel.push('Time To Completed Sentinel Analysed');
    })
  }//getSentinelAnalysedData

  getBenchmarkNDVI() {

    this.benchmarkNDVIData = [];
    this.benchmarkNDVILabel = [];
    return this.ndviService.GetTimeRequiredBenchmarkNDVI().subscribe((data) => {
      this.BenchmarkNDVIDataList = data.BenchmarkNDVI;
      this.benchmarkNDVIData.push(data.BenchmarkNDVI.totalTimeTakes);
      this.benchmarkNDVIData.push(data.BenchmarkNDVI.timeRequiredToComleteBenchmarks);       
      this.benchmarkNDVILabel.push('Total Time To Takes');
      this.benchmarkNDVILabel.push('Time To Completed Benchmarks');
    })
  }//getBenchmarkNDVI

  // getMonthlyKmlStatusData() {
  //   // return this.ndviService.GetTodayKMLData().subscribe((data) => {
  //   //   this.kmlStatusList = data;
  //   // })
  // }//getMonthlyKmlStatusData

  // getWeeklyKmlStatusData() {
  //   // return this.ndviService.GetTodayKMLData().subscribe((data) => {
  //   //   this.kmlStatusList = data;
  //   // })
  // }//getWeeklyKmlStatusData


  getWeeklyOfDayKMLOnboardedData() {
    return this.ndviService.GetWeeklyOfDayKMLOnboardedData().subscribe((data) => {
      this.onbordedDataLabel = data.OnboradedKmlLabels;
      this.onbordedDataDatasets = data.OnbordedKmlDatasets;
    })
  }//getWeeklyOfDayKMLOnboardedData

  getLast12HourlyData() {
    return this.ndviService.GetetHourlyData().subscribe((data) => {
      this.sentinelDownloadedLast12HourlyDataLabel = data.downloaded.labels;
      this.sentinelDownloadedLast12HourlyDatasets = data.downloaded.data;
      this.sentinelAnalysedLast12HourlyDataLabel = data.analysed.labels;
      this.sentinelAnalysedLast12HourlyDatasets = data.analysed.data;
      this.processedNDVILast12HourlyDataLabel = data.analysed.labels;
      this.processedNDVILast12HourlyDatasets = data.analysed.data;
    })
  }//getLast12HourlySentinelDownloadedData   

}


