import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IOffice } from 'app/entities/office/office.model';
import { OfficeService } from 'app/entities/office/service/office.service';
import { TherapistService } from '../service/therapist.service';
import { ITherapist } from '../therapist.model';
import { TherapistFormService } from './therapist-form.service';

import { TherapistUpdateComponent } from './therapist-update.component';

describe('Therapist Management Update Component', () => {
  let comp: TherapistUpdateComponent;
  let fixture: ComponentFixture<TherapistUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let therapistFormService: TherapistFormService;
  let therapistService: TherapistService;
  let officeService: OfficeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TherapistUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TherapistUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TherapistUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    therapistFormService = TestBed.inject(TherapistFormService);
    therapistService = TestBed.inject(TherapistService);
    officeService = TestBed.inject(OfficeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Office query and add missing value', () => {
      const therapist: ITherapist = { id: 456 };
      const office: IOffice = { id: 26033 };
      therapist.office = office;

      const officeCollection: IOffice[] = [{ id: 9477 }];
      jest.spyOn(officeService, 'query').mockReturnValue(of(new HttpResponse({ body: officeCollection })));
      const additionalOffices = [office];
      const expectedCollection: IOffice[] = [...additionalOffices, ...officeCollection];
      jest.spyOn(officeService, 'addOfficeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ therapist });
      comp.ngOnInit();

      expect(officeService.query).toHaveBeenCalled();
      expect(officeService.addOfficeToCollectionIfMissing).toHaveBeenCalledWith(
        officeCollection,
        ...additionalOffices.map(expect.objectContaining),
      );
      expect(comp.officesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const therapist: ITherapist = { id: 456 };
      const office: IOffice = { id: 11862 };
      therapist.office = office;

      activatedRoute.data = of({ therapist });
      comp.ngOnInit();

      expect(comp.officesSharedCollection).toContain(office);
      expect(comp.therapist).toEqual(therapist);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITherapist>>();
      const therapist = { id: 123 };
      jest.spyOn(therapistFormService, 'getTherapist').mockReturnValue(therapist);
      jest.spyOn(therapistService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ therapist });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: therapist }));
      saveSubject.complete();

      // THEN
      expect(therapistFormService.getTherapist).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(therapistService.update).toHaveBeenCalledWith(expect.objectContaining(therapist));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITherapist>>();
      const therapist = { id: 123 };
      jest.spyOn(therapistFormService, 'getTherapist').mockReturnValue({ id: null });
      jest.spyOn(therapistService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ therapist: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: therapist }));
      saveSubject.complete();

      // THEN
      expect(therapistFormService.getTherapist).toHaveBeenCalled();
      expect(therapistService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITherapist>>();
      const therapist = { id: 123 };
      jest.spyOn(therapistService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ therapist });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(therapistService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOffice', () => {
      it('Should forward to officeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(officeService, 'compareOffice');
        comp.compareOffice(entity, entity2);
        expect(officeService.compareOffice).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
