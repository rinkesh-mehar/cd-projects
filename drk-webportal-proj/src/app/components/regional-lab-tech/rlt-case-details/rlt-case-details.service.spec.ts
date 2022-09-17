import { TestBed } from '@angular/core/testing';

import { RltCaseDetailsService } from './rlt-case-details.service';

describe('RltCaseDetailsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RltCaseDetailsService = TestBed.get(RltCaseDetailsService);
    expect(service).toBeTruthy();
  });
});
