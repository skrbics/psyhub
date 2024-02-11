import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMMPIAnswer } from '../mmpi-answer.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmpi-answer.test-samples';

import { MMPIAnswerService } from './mmpi-answer.service';

const requireRestSample: IMMPIAnswer = {
  ...sampleWithRequiredData,
};

describe('MMPIAnswer Service', () => {
  let service: MMPIAnswerService;
  let httpMock: HttpTestingController;
  let expectedResult: IMMPIAnswer | IMMPIAnswer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MMPIAnswerService);
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

    it('should create a MMPIAnswer', () => {
      const mMPIAnswer = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mMPIAnswer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MMPIAnswer', () => {
      const mMPIAnswer = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mMPIAnswer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MMPIAnswer', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MMPIAnswer', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MMPIAnswer', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMMPIAnswerToCollectionIfMissing', () => {
      it('should add a MMPIAnswer to an empty array', () => {
        const mMPIAnswer: IMMPIAnswer = sampleWithRequiredData;
        expectedResult = service.addMMPIAnswerToCollectionIfMissing([], mMPIAnswer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPIAnswer);
      });

      it('should not add a MMPIAnswer to an array that contains it', () => {
        const mMPIAnswer: IMMPIAnswer = sampleWithRequiredData;
        const mMPIAnswerCollection: IMMPIAnswer[] = [
          {
            ...mMPIAnswer,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMMPIAnswerToCollectionIfMissing(mMPIAnswerCollection, mMPIAnswer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MMPIAnswer to an array that doesn't contain it", () => {
        const mMPIAnswer: IMMPIAnswer = sampleWithRequiredData;
        const mMPIAnswerCollection: IMMPIAnswer[] = [sampleWithPartialData];
        expectedResult = service.addMMPIAnswerToCollectionIfMissing(mMPIAnswerCollection, mMPIAnswer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPIAnswer);
      });

      it('should add only unique MMPIAnswer to an array', () => {
        const mMPIAnswerArray: IMMPIAnswer[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mMPIAnswerCollection: IMMPIAnswer[] = [sampleWithRequiredData];
        expectedResult = service.addMMPIAnswerToCollectionIfMissing(mMPIAnswerCollection, ...mMPIAnswerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mMPIAnswer: IMMPIAnswer = sampleWithRequiredData;
        const mMPIAnswer2: IMMPIAnswer = sampleWithPartialData;
        expectedResult = service.addMMPIAnswerToCollectionIfMissing([], mMPIAnswer, mMPIAnswer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPIAnswer);
        expect(expectedResult).toContain(mMPIAnswer2);
      });

      it('should accept null and undefined values', () => {
        const mMPIAnswer: IMMPIAnswer = sampleWithRequiredData;
        expectedResult = service.addMMPIAnswerToCollectionIfMissing([], null, mMPIAnswer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPIAnswer);
      });

      it('should return initial array if no MMPIAnswer is added', () => {
        const mMPIAnswerCollection: IMMPIAnswer[] = [sampleWithRequiredData];
        expectedResult = service.addMMPIAnswerToCollectionIfMissing(mMPIAnswerCollection, undefined, null);
        expect(expectedResult).toEqual(mMPIAnswerCollection);
      });
    });

    describe('compareMMPIAnswer', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMMPIAnswer(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMMPIAnswer(entity1, entity2);
        const compareResult2 = service.compareMMPIAnswer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMMPIAnswer(entity1, entity2);
        const compareResult2 = service.compareMMPIAnswer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMMPIAnswer(entity1, entity2);
        const compareResult2 = service.compareMMPIAnswer(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
