/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ApkVersionService } from './apk-version.service';

describe('Service: ApkVersion', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApkVersionService]
    });
  });

  it('should ...', inject([ApkVersionService], (service: ApkVersionService) => {
    expect(service).toBeTruthy();
  }));
});
