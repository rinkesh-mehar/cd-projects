import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RlmDashboardComponent } from './rlm-dashboard.component';

describe('RlmDashboardComponent', () => {
  let component: RlmDashboardComponent;
  let fixture: ComponentFixture<RlmDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RlmDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RlmDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
