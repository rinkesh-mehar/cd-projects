import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NonTechnicalFormComponent } from './non-technical-form.component';

describe('NonTechnicalFormComponent', () => {
  let component: NonTechnicalFormComponent;
  let fixture: ComponentFixture<NonTechnicalFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NonTechnicalFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NonTechnicalFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
