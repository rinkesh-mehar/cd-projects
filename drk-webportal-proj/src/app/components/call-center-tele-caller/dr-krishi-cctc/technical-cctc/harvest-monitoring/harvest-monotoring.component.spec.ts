import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HarvestMonotoringComponent } from './harvest-monotoring.component';

describe('HarvestMonotoringComponent', () => {
  let component: HarvestMonotoringComponent;
  let fixture: ComponentFixture<HarvestMonotoringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HarvestMonotoringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HarvestMonotoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
