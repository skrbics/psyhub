import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMMPIFeature } from '../mmpi-feature.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmpi-feature.test-samples';

import { MMPIFeatureService } from './mmpi-feature.service';

const requireRestSample: IMMPIFeature = {
  ...sampleWithRequiredData,
};

describe('MMPIFeature Service', () => {
  let service: MMPIFeatureService;
  let httpMock: HttpTestingController;
  let expectedResult: IMMPIFeature | IMMPIFeature[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MMPIFeatureService);
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

    it('should create a MMPIFeature', () => {
      const mMPIFeature = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mMPIFeature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MMPIFeature', () => {
      const mMPIFeature = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mMPIFeature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MMPIFeature', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MMPIFeature', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MMPIFeature', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMMPIFeatureToCollectionIfMissing', () => {
      it('should add a MMPIFeature to an empty array', () => {
        const mMPIFeature: IMMPIFeature = sampleWithRequiredData;
        expectedResult = service.addMMPIFeatureToCollectionIfMissing([], mMPIFeature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPIFeature);
      });

      it('should not add a MMPIFeature to an array that contains it', () => {
        const mMPIFeature: IMMPIFeature = sampleWithRequiredData;
        const mMPIFeatureCollection: IMMPIFeature[] = [
          {
            ...mMPIFeature,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMMPIFeatureToCollectionIfMissing(mMPIFeatureCollection, mMPIFeature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MMPIFeature to an array that doesn't contain it", () => {
        const mMPIFeature: IMMPIFeature = sampleWithRequiredData;
        const mMPIFeatureCollection: IMMPIFeature[] = [sampleWithPartialData];
        expectedResult = service.addMMPIFeatureToCollectionIfMissing(mMPIFeatureCollection, mMPIFeature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPIFeature);
      });

      it('should add only unique MMPIFeature to an array', () => {
        const mMPIFeatureArray: IMMPIFeature[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mMPIFeatureCollection: IMMPIFeature[] = [sampleWithRequiredData];
        expectedResult = service.addMMPIFeatureToCollectionIfMissing(mMPIFeatureCollection, ...mMPIFeatureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mMPIFeature: IMMPIFeature = sampleWithRequiredData;
        const mMPIFeature2: IMMPIFeature = sampleWithPartialData;
        expectedResult = service.addMMPIFeatureToCollectionIfMissing([], mMPIFeature, mMPIFeature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPIFeature);
        expect(expectedResult).toContain(mMPIFeature2);
      });

      it('should accept null and undefined values', () => {
        const mMPIFeature: IMMPIFeature = sampleWithRequiredData;
        expectedResult = service.addMMPIFeatureToCollectionIfMissing([], null, mMPIFeature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPIFeature);
      });

      it('should return initial array if no MMPIFeature is added', () => {
        const mMPIFeatureCollection: IMMPIFeature[] = [sampleWithRequiredData];
        expectedResult = service.addMMPIFeatureToCollectionIfMissing(mMPIFeatureCollection, undefined, null);
        expect(expectedResult).toEqual(mMPIFeatureCollection);
      });
    });

    describe('compareMMPIFeature', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMMPIFeature(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMMPIFeature(entity1, entity2);
        const compareResult2 = service.compareMMPIFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMMPIFeature(entity1, entity2);
        const compareResult2 = service.compareMMPIFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMMPIFeature(entity1, entity2);
        const compareResult2 = service.compareMMPIFeature(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
