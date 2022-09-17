import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleDiagnosisApprovalListComponent } from './sample-diagnosis-approval-list.component';

describe('SampleDiagnosisApprovalListComponent', () => {
  let component: SampleDiagnosisApprovalListComponent;
  let fixture: ComponentFixture<SampleDiagnosisApprovalListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SampleDiagnosisApprovalListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleDiagnosisApprovalListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
