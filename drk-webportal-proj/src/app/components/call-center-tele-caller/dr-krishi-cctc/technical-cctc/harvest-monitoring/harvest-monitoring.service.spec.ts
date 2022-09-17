import { TestBed } from '@angular/core/testing';

import { HarvestMonitoringService } from './harvest-monitoring.service';

describe('HarvestMonitoringService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HarvestMonitoringService = TestBed.get(HarvestMonitoringService);
    expect(service).toBeTruthy();
  });
});
