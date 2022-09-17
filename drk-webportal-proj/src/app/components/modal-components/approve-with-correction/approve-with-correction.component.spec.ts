import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveWithCorrectionComponent } from './approve-with-correction.component';

describe('ApproveWithCorrectionComponent', () => {
  let component: ApproveWithCorrectionComponent;
  let fixture: ComponentFixture<ApproveWithCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApproveWithCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveWithCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
