import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageHardwareListComponent } from './manage-hardware-list.component';

describe('ManageHardwareListComponent', () => {
  let component: ManageHardwareListComponent;
  let fixture: ComponentFixture<ManageHardwareListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageHardwareListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageHardwareListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
