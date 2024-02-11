import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import { MMPITestCardFeatureService } from '../service/mmpi-test-card-feature.service';

import mMPITestCardFeatureResolve from './mmpi-test-card-feature-routing-resolve.service';

describe('MMPITestCardFeature routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: MMPITestCardFeatureService;
  let resultMMPITestCardFeature: IMMPITestCardFeature | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(MMPITestCardFeatureService);
    resultMMPITestCardFeature = undefined;
  });

  describe('resolve', () => {
    it('should return IMMPITestCardFeature returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        mMPITestCardFeatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMMPITestCardFeature = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMMPITestCardFeature).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        mMPITestCardFeatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMMPITestCardFeature = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMMPITestCardFeature).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMMPITestCardFeature>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        mMPITestCardFeatureResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMMPITestCardFeature = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMMPITestCardFeature).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
