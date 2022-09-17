import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWeatherBasedtravelTimeComponent } from './add-weather-basedtravel-time.component';

describe('AddWeatherBasedtravelTimeComponent', () => {
  let component: AddWeatherBasedtravelTimeComponent;
  let fixture: ComponentFixture<AddWeatherBasedtravelTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddWeatherBasedtravelTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddWeatherBasedtravelTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
