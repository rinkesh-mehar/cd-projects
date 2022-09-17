import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NonTechnicalInformationFormComponent } from './non-technical-information-form.component';

describe('NonTechnicalInformationFormComponent', () => {
  let component: NonTechnicalInformationFormComponent;
  let fixture: ComponentFixture<NonTechnicalInformationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NonTechnicalInformationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NonTechnicalInformationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
