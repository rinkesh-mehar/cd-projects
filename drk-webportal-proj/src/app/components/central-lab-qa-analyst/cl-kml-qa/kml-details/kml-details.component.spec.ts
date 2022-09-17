import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KmlDetailsComponent } from './kml-details.component';

describe('KmlDetailsComponent', () => {
  let component: KmlDetailsComponent;
  let fixture: ComponentFixture<KmlDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KmlDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KmlDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
