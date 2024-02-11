import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISessionBill } from '../session-bill.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../session-bill.test-samples';

import { SessionBillService } from './session-bill.service';

const requireRestSample: ISessionBill = {
  ...sampleWithRequiredData,
};

describe('SessionBill Service', () => {
  let service: SessionBillService;
  let httpMock: HttpTestingController;
  let expectedResult: ISessionBill | ISessionBill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SessionBillService);
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

    it('should create a SessionBill', () => {
      const sessionBill = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sessionBill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SessionBill', () => {
      const sessionBill = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sessionBill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SessionBill', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SessionBill', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SessionBill', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSessionBillToCollectionIfMissing', () => {
      it('should add a SessionBill to an empty array', () => {
        const sessionBill: ISessionBill = sampleWithRequiredData;
        expectedResult = service.addSessionBillToCollectionIfMissing([], sessionBill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sessionBill);
      });

      it('should not add a SessionBill to an array that contains it', () => {
        const sessionBill: ISessionBill = sampleWithRequiredData;
        const sessionBillCollection: ISessionBill[] = [
          {
            ...sessionBill,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSessionBillToCollectionIfMissing(sessionBillCollection, sessionBill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SessionBill to an array that doesn't contain it", () => {
        const sessionBill: ISessionBill = sampleWithRequiredData;
        const sessionBillCollection: ISessionBill[] = [sampleWithPartialData];
        expectedResult = service.addSessionBillToCollectionIfMissing(sessionBillCollection, sessionBill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sessionBill);
      });

      it('should add only unique SessionBill to an array', () => {
        const sessionBillArray: ISessionBill[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sessionBillCollection: ISessionBill[] = [sampleWithRequiredData];
        expectedResult = service.addSessionBillToCollectionIfMissing(sessionBillCollection, ...sessionBillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sessionBill: ISessionBill = sampleWithRequiredData;
        const sessionBill2: ISessionBill = sampleWithPartialData;
        expectedResult = service.addSessionBillToCollectionIfMissing([], sessionBill, sessionBill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sessionBill);
        expect(expectedResult).toContain(sessionBill2);
      });

      it('should accept null and undefined values', () => {
        const sessionBill: ISessionBill = sampleWithRequiredData;
        expectedResult = service.addSessionBillToCollectionIfMissing([], null, sessionBill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sessionBill);
      });

      it('should return initial array if no SessionBill is added', () => {
        const sessionBillCollection: ISessionBill[] = [sampleWithRequiredData];
        expectedResult = service.addSessionBillToCollectionIfMissing(sessionBillCollection, undefined, null);
        expect(expectedResult).toEqual(sessionBillCollection);
      });
    });

    describe('compareSessionBill', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSessionBill(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSessionBill(entity1, entity2);
        const compareResult2 = service.compareSessionBill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSessionBill(entity1, entity2);
        const compareResult2 = service.compareSessionBill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSessionBill(entity1, entity2);
        const compareResult2 = service.compareSessionBill(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
