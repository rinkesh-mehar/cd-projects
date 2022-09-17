import { TestBed } from '@angular/core/testing';

import { BulkDataService } from './bulk-data.service';

describe('BulkDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BulkDataService = TestBed.get(BulkDataService);
    expect(service).toBeTruthy();
  });
});
