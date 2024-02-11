import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMMPITestCard } from '../mmpi-test-card.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mmpi-test-card.test-samples';

import { MMPITestCardService } from './mmpi-test-card.service';

const requireRestSample: IMMPITestCard = {
  ...sampleWithRequiredData,
};

describe('MMPITestCard Service', () => {
  let service: MMPITestCardService;
  let httpMock: HttpTestingController;
  let expectedResult: IMMPITestCard | IMMPITestCard[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MMPITestCardService);
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

    it('should create a MMPITestCard', () => {
      const mMPITestCard = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mMPITestCard).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MMPITestCard', () => {
      const mMPITestCard = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mMPITestCard).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MMPITestCard', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MMPITestCard', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MMPITestCard', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMMPITestCardToCollectionIfMissing', () => {
      it('should add a MMPITestCard to an empty array', () => {
        const mMPITestCard: IMMPITestCard = sampleWithRequiredData;
        expectedResult = service.addMMPITestCardToCollectionIfMissing([], mMPITestCard);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITestCard);
      });

      it('should not add a MMPITestCard to an array that contains it', () => {
        const mMPITestCard: IMMPITestCard = sampleWithRequiredData;
        const mMPITestCardCollection: IMMPITestCard[] = [
          {
            ...mMPITestCard,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMMPITestCardToCollectionIfMissing(mMPITestCardCollection, mMPITestCard);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MMPITestCard to an array that doesn't contain it", () => {
        const mMPITestCard: IMMPITestCard = sampleWithRequiredData;
        const mMPITestCardCollection: IMMPITestCard[] = [sampleWithPartialData];
        expectedResult = service.addMMPITestCardToCollectionIfMissing(mMPITestCardCollection, mMPITestCard);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITestCard);
      });

      it('should add only unique MMPITestCard to an array', () => {
        const mMPITestCardArray: IMMPITestCard[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mMPITestCardCollection: IMMPITestCard[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestCardToCollectionIfMissing(mMPITestCardCollection, ...mMPITestCardArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mMPITestCard: IMMPITestCard = sampleWithRequiredData;
        const mMPITestCard2: IMMPITestCard = sampleWithPartialData;
        expectedResult = service.addMMPITestCardToCollectionIfMissing([], mMPITestCard, mMPITestCard2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mMPITestCard);
        expect(expectedResult).toContain(mMPITestCard2);
      });

      it('should accept null and undefined values', () => {
        const mMPITestCard: IMMPITestCard = sampleWithRequiredData;
        expectedResult = service.addMMPITestCardToCollectionIfMissing([], null, mMPITestCard, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mMPITestCard);
      });

      it('should return initial array if no MMPITestCard is added', () => {
        const mMPITestCardCollection: IMMPITestCard[] = [sampleWithRequiredData];
        expectedResult = service.addMMPITestCardToCollectionIfMissing(mMPITestCardCollection, undefined, null);
        expect(expectedResult).toEqual(mMPITestCardCollection);
      });
    });

    describe('compareMMPITestCard', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMMPITestCard(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMMPITestCard(entity1, entity2);
        const compareResult2 = service.compareMMPITestCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMMPITestCard(entity1, entity2);
        const compareResult2 = service.compareMMPITestCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMMPITestCard(entity1, entity2);
        const compareResult2 = service.compareMMPITestCard(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
