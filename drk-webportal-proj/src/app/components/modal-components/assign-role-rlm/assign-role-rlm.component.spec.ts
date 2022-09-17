import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignRoleRlmComponent } from './assign-role-rlm.component';

describe('AssignRoleRlmComponent', () => {
  let component: AssignRoleRlmComponent;
  let fixture: ComponentFixture<AssignRoleRlmComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignRoleRlmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignRoleRlmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
