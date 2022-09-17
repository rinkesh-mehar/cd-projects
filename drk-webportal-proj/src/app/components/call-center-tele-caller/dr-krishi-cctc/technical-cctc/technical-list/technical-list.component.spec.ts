import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NonTechnicalListComponent } from './non-technical-list.component';

describe('NonTechnicalListComponent', () => {
  let component: NonTechnicalListComponent;
  let fixture: ComponentFixture<NonTechnicalListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NonTechnicalListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NonTechnicalListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
