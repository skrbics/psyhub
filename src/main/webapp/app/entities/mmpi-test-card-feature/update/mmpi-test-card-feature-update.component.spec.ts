import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';
import { MMPITestCardService } from 'app/entities/mmpi-test-card/service/mmpi-test-card.service';
import { IMMPIFeature } from 'app/entities/mmpi-feature/mmpi-feature.model';
import { MMPIFeatureService } from 'app/entities/mmpi-feature/service/mmpi-feature.service';
import { IMMPITestCardFeature } from '../mmpi-test-card-feature.model';
import { MMPITestCardFeatureService } from '../service/mmpi-test-card-feature.service';
import { MMPITestCardFeatureFormService } from './mmpi-test-card-feature-form.service';

import { MMPITestCardFeatureUpdateComponent } from './mmpi-test-card-feature-update.component';

describe('MMPITestCardFeature Management Update Component', () => {
  let comp: MMPITestCardFeatureUpdateComponent;
  let fixture: ComponentFixture<MMPITestCardFeatureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mMPITestCardFeatureFormService: MMPITestCardFeatureFormService;
  let mMPITestCardFeatureService: MMPITestCardFeatureService;
  let mMPITestCardService: MMPITestCardService;
  let mMPIFeatureService: MMPIFeatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MMPITestCardFeatureUpdateComponent],
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
      .overrideTemplate(MMPITestCardFeatureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MMPITestCardFeatureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mMPITestCardFeatureFormService = TestBed.inject(MMPITestCardFeatureFormService);
    mMPITestCardFeatureService = TestBed.inject(MMPITestCardFeatureService);
    mMPITestCardService = TestBed.inject(MMPITestCardService);
    mMPIFeatureService = TestBed.inject(MMPIFeatureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MMPITestCard query and add missing value', () => {
      const mMPITestCardFeature: IMMPITestCardFeature = { id: 456 };
      const mMPITestCard: IMMPITestCard = { id: 28621 };
      mMPITestCardFeature.mMPITestCard = mMPITestCard;

      const mMPITestCardCollection: IMMPITestCard[] = [{ id: 27909 }];
      jest.spyOn(mMPITestCardService, 'query').mockReturnValue(of(new HttpResponse({ body: mMPITestCardCollection })));
      const additionalMMPITestCards = [mMPITestCard];
      const expectedCollection: IMMPITestCard[] = [...additionalMMPITestCards, ...mMPITestCardCollection];
      jest.spyOn(mMPITestCardService, 'addMMPITestCardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mMPITestCardFeature });
      comp.ngOnInit();

      expect(mMPITestCardService.query).toHaveBeenCalled();
      expect(mMPITestCardService.addMMPITestCardToCollectionIfMissing).toHaveBeenCalledWith(
        mMPITestCardCollection,
        ...additionalMMPITestCards.map(expect.objectContaining),
      );
      expect(comp.mMPITestCardsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MMPIFeature query and add missing value', () => {
      const mMPITestCardFeature: IMMPITestCardFeature = { id: 456 };
      const mMPIFeature: IMMPIFeature = { id: 79 };
      mMPITestCardFeature.mMPIFeature = mMPIFeature;

      const mMPIFeatureCollection: IMMPIFeature[] = [{ id: 26561 }];
      jest.spyOn(mMPIFeatureService, 'query').mockReturnValue(of(new HttpResponse({ body: mMPIFeatureCollection })));
      const additionalMMPIFeatures = [mMPIFeature];
      const expectedCollection: IMMPIFeature[] = [...additionalMMPIFeatures, ...mMPIFeatureCollection];
      jest.spyOn(mMPIFeatureService, 'addMMPIFeatureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mMPITestCardFeature });
      comp.ngOnInit();

      expect(mMPIFeatureService.query).toHaveBeenCalled();
      expect(mMPIFeatureService.addMMPIFeatureToCollectionIfMissing).toHaveBeenCalledWith(
        mMPIFeatureCollection,
        ...additionalMMPIFeatures.map(expect.objectContaining),
      );
      expect(comp.mMPIFeaturesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mMPITestCardFeature: IMMPITestCardFeature = { id: 456 };
      const mMPITestCard: IMMPITestCard = { id: 19500 };
      mMPITestCardFeature.mMPITestCard = mMPITestCard;
      const mMPIFeature: IMMPIFeature = { id: 18992 };
      mMPITestCardFeature.mMPIFeature = mMPIFeature;

      activatedRoute.data = of({ mMPITestCardFeature });
      comp.ngOnInit();

      expect(comp.mMPITestCardsSharedCollection).toContain(mMPITestCard);
      expect(comp.mMPIFeaturesSharedCollection).toContain(mMPIFeature);
      expect(comp.mMPITestCardFeature).toEqual(mMPITestCardFeature);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCardFeature>>();
      const mMPITestCardFeature = { id: 123 };
      jest.spyOn(mMPITestCardFeatureFormService, 'getMMPITestCardFeature').mockReturnValue(mMPITestCardFeature);
      jest.spyOn(mMPITestCardFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCardFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITestCardFeature }));
      saveSubject.complete();

      // THEN
      expect(mMPITestCardFeatureFormService.getMMPITestCardFeature).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mMPITestCardFeatureService.update).toHaveBeenCalledWith(expect.objectContaining(mMPITestCardFeature));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCardFeature>>();
      const mMPITestCardFeature = { id: 123 };
      jest.spyOn(mMPITestCardFeatureFormService, 'getMMPITestCardFeature').mockReturnValue({ id: null });
      jest.spyOn(mMPITestCardFeatureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCardFeature: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPITestCardFeature }));
      saveSubject.complete();

      // THEN
      expect(mMPITestCardFeatureFormService.getMMPITestCardFeature).toHaveBeenCalled();
      expect(mMPITestCardFeatureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPITestCardFeature>>();
      const mMPITestCardFeature = { id: 123 };
      jest.spyOn(mMPITestCardFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPITestCardFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mMPITestCardFeatureService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMMPITestCard', () => {
      it('Should forward to mMPITestCardService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mMPITestCardService, 'compareMMPITestCard');
        comp.compareMMPITestCard(entity, entity2);
        expect(mMPITestCardService.compareMMPITestCard).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMMPIFeature', () => {
      it('Should forward to mMPIFeatureService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mMPIFeatureService, 'compareMMPIFeature');
        comp.compareMMPIFeature(entity, entity2);
        expect(mMPIFeatureService.compareMMPIFeature).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
