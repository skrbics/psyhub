import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITherapist } from '../therapist.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../therapist.test-samples';

import { TherapistService } from './therapist.service';

const requireRestSample: ITherapist = {
  ...sampleWithRequiredData,
};

describe('Therapist Service', () => {
  let service: TherapistService;
  let httpMock: HttpTestingController;
  let expectedResult: ITherapist | ITherapist[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TherapistService);
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

    it('should create a Therapist', () => {
      const therapist = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(therapist).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Therapist', () => {
      const therapist = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(therapist).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Therapist', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Therapist', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Therapist', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTherapistToCollectionIfMissing', () => {
      it('should add a Therapist to an empty array', () => {
        const therapist: ITherapist = sampleWithRequiredData;
        expectedResult = service.addTherapistToCollectionIfMissing([], therapist);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(therapist);
      });

      it('should not add a Therapist to an array that contains it', () => {
        const therapist: ITherapist = sampleWithRequiredData;
        const therapistCollection: ITherapist[] = [
          {
            ...therapist,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTherapistToCollectionIfMissing(therapistCollection, therapist);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Therapist to an array that doesn't contain it", () => {
        const therapist: ITherapist = sampleWithRequiredData;
        const therapistCollection: ITherapist[] = [sampleWithPartialData];
        expectedResult = service.addTherapistToCollectionIfMissing(therapistCollection, therapist);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(therapist);
      });

      it('should add only unique Therapist to an array', () => {
        const therapistArray: ITherapist[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const therapistCollection: ITherapist[] = [sampleWithRequiredData];
        expectedResult = service.addTherapistToCollectionIfMissing(therapistCollection, ...therapistArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const therapist: ITherapist = sampleWithRequiredData;
        const therapist2: ITherapist = sampleWithPartialData;
        expectedResult = service.addTherapistToCollectionIfMissing([], therapist, therapist2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(therapist);
        expect(expectedResult).toContain(therapist2);
      });

      it('should accept null and undefined values', () => {
        const therapist: ITherapist = sampleWithRequiredData;
        expectedResult = service.addTherapistToCollectionIfMissing([], null, therapist, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(therapist);
      });

      it('should return initial array if no Therapist is added', () => {
        const therapistCollection: ITherapist[] = [sampleWithRequiredData];
        expectedResult = service.addTherapistToCollectionIfMissing(therapistCollection, undefined, null);
        expect(expectedResult).toEqual(therapistCollection);
      });
    });

    describe('compareTherapist', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTherapist(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTherapist(entity1, entity2);
        const compareResult2 = service.compareTherapist(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTherapist(entity1, entity2);
        const compareResult2 = service.compareTherapist(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTherapist(entity1, entity2);
        const compareResult2 = service.compareTherapist(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
