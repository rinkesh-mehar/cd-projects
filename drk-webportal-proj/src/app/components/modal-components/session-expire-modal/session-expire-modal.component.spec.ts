import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionExpireModalComponent } from './session-expire-modal.component';

describe('SessionExpireModalComponent', () => {
  let component: SessionExpireModalComponent;
  let fixture: ComponentFixture<SessionExpireModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SessionExpireModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SessionExpireModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
