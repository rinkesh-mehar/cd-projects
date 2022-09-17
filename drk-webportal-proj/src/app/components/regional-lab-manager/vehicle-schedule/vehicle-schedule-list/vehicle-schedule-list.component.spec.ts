import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleScheduleListComponent } from './vehicle-schedule-list.component';

describe('VehicleScheduleListComponent', () => {
  let component: VehicleScheduleListComponent;
  let fixture: ComponentFixture<VehicleScheduleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehicleScheduleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleScheduleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
