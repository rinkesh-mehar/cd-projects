import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignVillagesToPrsComponent } from './assign-villages-to-prs.component';

describe('AssignVillagesToPrsComponent', () => {
  let component: AssignVillagesToPrsComponent;
  let fixture: ComponentFixture<AssignVillagesToPrsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignVillagesToPrsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignVillagesToPrsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
