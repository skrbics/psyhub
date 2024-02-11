import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../mmpi-test-card-feature.test-samples';

import { MMPITestCardFeatureService } from './mmpi-test-card-feature.service';

const requireRestSample: IMMPITestCardFeature = {
  ...sampleWithRequiredData,
};

describe('MMPITestCardFeature Service', () => {
  let service: MMPITestCardFeatureService;
  let httpMock: HttpTestingController;
  let expectedResult: IMMPITestCardFeature | IMMPITestCardFeature[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MMPITestCardFeatureService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MMPITestCardFeature', () => {
      const mMPITestCardFeature = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mMPITestCardFeature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MMPITestCardFeature', () => {
      const mMPITestCardFeature = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mMPITestCardFeature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MMPITestCardFeature', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MMPITestCardFeature', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MMPITestCardFeature', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMMPITestCardFeatureToCollectionIfMissing', () => {
      it('should add a MMPITestCardFeature to an empty array', () => {
        const mMPITestCardFeature: IMMPITestCardFeature = sampleWithRequiredData;
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing([], mMPITestCardFeature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITestCardFeature);
      });

      it('should not add a MMPITestCardFeature to an array that contains it', () => {
        const mMPITestCardFeature: IMMPITestCardFeature = sampleWithRequiredData;
        const mMPITestCardFeatureCollection: IMMPITestCardFeature[] = [
          {
            ...mMPITestCardFeature,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing(mMPITestCardFeatureCollection, mMPITestCardFeature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MMPITestCardFeature to an array that doesn't contain it", () => {
        const mMPITestCardFeature: IMMPITestCardFeature = sampleWithRequiredData;
        const mMPITestCardFeatureCollection: IMMPITestCardFeature[] = [sampleWithPartialData];
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing(mMPITestCardFeatureCollection, mMPITestCardFeature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITestCardFeature);
      });

      it('should add only unique MMPITestCardFeature to an array', () => {
        const mMPITestCardFeatureArray: IMMPITestCardFeature[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mMPITestCardFeatureCollection: IMMPITestCardFeature[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing(mMPITestCardFeatureCollection, ...mMPITestCardFeatureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mMPITestCardFeature: IMMPITestCardFeature = sampleWithRequiredData;
        const mMPITestCardFeature2: IMMPITestCardFeature = sampleWithPartialData;
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing([], mMPITestCardFeature, mMPITestCardFeature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITestCardFeature);
        expect(expectedResult).toContain(mMPITestCardFeature2);
      });

      it('should accept null and undefined values', () => {
        const mMPITestCardFeature: IMMPITestCardFeature = sampleWithRequiredData;
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing([], null, mMPITestCardFeature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITestCardFeature);
      });

      it('should return initial array if no MMPITestCardFeature is added', () => {
        const mMPITestCardFeatureCollection: IMMPITestCardFeature[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestCardFeatureToCollectionIfMissing(mMPITestCardFeatureCollection, undefined, null);
        expect(expectedResult).toEqual(mMPITestCardFeatureCollection);
      });
    });

    describe('compareMMPITestCardFeature', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMMPITestCardFeature(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMMPITestCardFeature(entity1, entity2);
        const compareResult2 = service.compareMMPITestCardFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMMPITestCardFeature(entity1, entity2);
        const compareResult2 = service.compareMMPITestCardFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMMPITestCardFeature(entity1, entity2);
        const compareResult2 = service.compareMMPITestCardFeature(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
