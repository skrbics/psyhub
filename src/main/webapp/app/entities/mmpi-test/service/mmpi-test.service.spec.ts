import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMMPITest } from '../mmpi-test.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmpi-test.test-samples';

import { MMPITestService, RestMMPITest } from './mmpi-test.service';

const requireRestSample: RestMMPITest = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('MMPITest Service', () => {
  let service: MMPITestService;
  let httpMock: HttpTestingController;
  let expectedResult: IMMPITest | IMMPITest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MMPITestService);
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

    it('should create a MMPITest', () => {
      const mMPITest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mMPITest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MMPITest', () => {
      const mMPITest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mMPITest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MMPITest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MMPITest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MMPITest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMMPITestToCollectionIfMissing', () => {
      it('should add a MMPITest to an empty array', () => {
        const mMPITest: IMMPITest = sampleWithRequiredData;
        expectedResult = service.addMMPITestToCollectionIfMissing([], mMPITest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITest);
      });

      it('should not add a MMPITest to an array that contains it', () => {
        const mMPITest: IMMPITest = sampleWithRequiredData;
        const mMPITestCollection: IMMPITest[] = [
          {
            ...mMPITest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMMPITestToCollectionIfMissing(mMPITestCollection, mMPITest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MMPITest to an array that doesn't contain it", () => {
        const mMPITest: IMMPITest = sampleWithRequiredData;
        const mMPITestCollection: IMMPITest[] = [sampleWithPartialData];
        expectedResult = service.addMMPITestToCollectionIfMissing(mMPITestCollection, mMPITest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITest);
      });

      it('should add only unique MMPITest to an array', () => {
        const mMPITestArray: IMMPITest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mMPITestCollection: IMMPITest[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestToCollectionIfMissing(mMPITestCollection, ...mMPITestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mMPITest: IMMPITest = sampleWithRequiredData;
        const mMPITest2: IMMPITest = sampleWithPartialData;
        expectedResult = service.addMMPITestToCollectionIfMissing([], mMPITest, mMPITest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITest);
        expect(expectedResult).toContain(mMPITest2);
      });

      it('should accept null and undefined values', () => {
        const mMPITest: IMMPITest = sampleWithRequiredData;
        expectedResult = service.addMMPITestToCollectionIfMissing([], null, mMPITest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITest);
      });

      it('should return initial array if no MMPITest is added', () => {
        const mMPITestCollection: IMMPITest[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestToCollectionIfMissing(mMPITestCollection, undefined, null);
        expect(expectedResult).toEqual(mMPITestCollection);
      });
    });

    describe('compareMMPITest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMMPITest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMMPITest(entity1, entity2);
        const compareResult2 = service.compareMMPITest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMMPITest(entity1, entity2);
        const compareResult2 = service.compareMMPITest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMMPITest(entity1, entity2);
        const compareResult2 = service.compareMMPITest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
