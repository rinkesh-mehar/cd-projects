import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherBasedTravelTimeListComponent } from './weather-based-travel-time-list.component';

describe('WeatherBasedTravelTimeListComponent', () => {
  let component: WeatherBasedTravelTimeListComponent;
  let fixture: ComponentFixture<WeatherBasedTravelTimeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeatherBasedTravelTimeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherBasedTravelTimeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
