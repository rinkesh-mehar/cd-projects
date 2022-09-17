import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RltSampleReceivedComponent } from './sample-received.component';

describe('SampleReceivedComponent', () => {
  let component: RltSampleReceivedComponent;
  let fixture: ComponentFixture<RltSampleReceivedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RltSampleReceivedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RltSampleReceivedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
