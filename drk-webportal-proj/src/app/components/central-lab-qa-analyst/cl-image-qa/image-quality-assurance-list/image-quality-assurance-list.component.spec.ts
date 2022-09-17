import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageQualityAssuranceListComponent } from './image-quality-assurance-list.component';

describe('ImageQualityAssuranceListComponent', () => {
  let component: ImageQualityAssuranceListComponent;
  let fixture: ComponentFixture<ImageQualityAssuranceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageQualityAssuranceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageQualityAssuranceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
