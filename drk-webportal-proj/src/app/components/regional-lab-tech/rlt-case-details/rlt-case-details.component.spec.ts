import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RltCaseDetailsComponent } from './rlt-case-details.component';

describe('RltCaseDetailsComponent', () => {
  let component: RltCaseDetailsComponent;
  let fixture: ComponentFixture<RltCaseDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RltCaseDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RltCaseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
