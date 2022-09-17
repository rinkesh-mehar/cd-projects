import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleScheduleComponent } from './vehicle-schedule.component';

describe('VehicleScheduleComponent', () => {
  let component: VehicleScheduleComponent;
  let fixture: ComponentFixture<VehicleScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehicleScheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
