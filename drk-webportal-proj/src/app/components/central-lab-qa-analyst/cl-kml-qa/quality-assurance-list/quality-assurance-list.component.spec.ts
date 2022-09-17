import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QualityAssuranceListComponent } from './quality-assurance-list.component';

describe('QualityAssuranceListComponent', () => {
  let component: QualityAssuranceListComponent;
  let fixture: ComponentFixture<QualityAssuranceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QualityAssuranceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QualityAssuranceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
