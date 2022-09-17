import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndividualKycDetailsComponent } from './individual-kyc-details.component';

describe('IndividualKycDetailsComponent', () => {
  let component: IndividualKycDetailsComponent;
  let fixture: ComponentFixture<IndividualKycDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndividualKycDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndividualKycDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
