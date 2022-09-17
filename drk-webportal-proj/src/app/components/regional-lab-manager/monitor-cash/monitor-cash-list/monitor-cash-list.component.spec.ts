import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitorCashListComponent } from './monitor-cash-list.component';

describe('MonitorCashListComponent', () => {
  let component: MonitorCashListComponent;
  let fixture: ComponentFixture<MonitorCashListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitorCashListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitorCashListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
