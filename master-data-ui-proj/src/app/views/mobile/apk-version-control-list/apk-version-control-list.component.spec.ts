/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ApkVersionControlListComponent } from './apk-version-control-list.component';

describe('ApkVersionControlListComponent', () => {
  let component: ApkVersionControlListComponent;
  let fixture: ComponentFixture<ApkVersionControlListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApkVersionControlListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApkVersionControlListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
