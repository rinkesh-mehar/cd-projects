import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestrictRoleComponent } from './restrict-role.component';

describe('RestrictRoleComponent', () => {
  let component: RestrictRoleComponent;
  let fixture: ComponentFixture<RestrictRoleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestrictRoleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RestrictRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
