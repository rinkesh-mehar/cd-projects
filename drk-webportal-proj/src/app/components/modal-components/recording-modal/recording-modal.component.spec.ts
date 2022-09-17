import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordingModalComponent } from './recording-modal.component';

describe('RecordingModalComponent', () => {
  let component: RecordingModalComponent;
  let fixture: ComponentFixture<RecordingModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordingModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
