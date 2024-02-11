import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMMPITest } from 'app/entities/mmpi-test/mmpi-test.model';
import { MMPITestService } from 'app/entities/mmpi-test/service/mmpi-test.service';
import { IMMPITestCard } from 'app/entities/mmpi-test-card/mmpi-test-card.model';
import { MMPITestCardService } from 'app/entities/mmpi-test-card/service/mmpi-test-card.service';
import { IMMPIAnswer } from '../mmpi-answer.model';
import { MMPIAnswerService } from '../service/mmpi-answer.service';
import { MMPIAnswerFormService } from './mmpi-answer-form.service';

import { MMPIAnswerUpdateComponent } from './mmpi-answer-update.component';

describe('MMPIAnswer Management Update Component', () => {
  let comp: MMPIAnswerUpdateComponent;
  let fixture: ComponentFixture<MMPIAnswerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mMPIAnswerFormService: MMPIAnswerFormService;
  let mMPIAnswerService: MMPIAnswerService;
  let mMPITestService: MMPITestService;
  let mMPITestCardService: MMPITestCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MMPIAnswerUpdateComponent],
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
      .overrideTemplate(MMPIAnswerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MMPIAnswerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mMPIAnswerFormService = TestBed.inject(MMPIAnswerFormService);
    mMPIAnswerService = TestBed.inject(MMPIAnswerService);
    mMPITestService = TestBed.inject(MMPITestService);
    mMPITestCardService = TestBed.inject(MMPITestCardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MMPITest query and add missing value', () => {
      const mMPIAnswer: IMMPIAnswer = { id: 456 };
      const mMPITest: IMMPITest = { id: 31725 };
      mMPIAnswer.mMPITest = mMPITest;

      const mMPITestCollection: IMMPITest[] = [{ id: 22662 }];
      jest.spyOn(mMPITestService, 'query').mockReturnValue(of(new HttpResponse({ body: mMPITestCollection })));
      const additionalMMPITests = [mMPITest];
      const expectedCollection: IMMPITest[] = [...additionalMMPITests, ...mMPITestCollection];
      jest.spyOn(mMPITestService, 'addMMPITestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mMPIAnswer });
      comp.ngOnInit();

      expect(mMPITestService.query).toHaveBeenCalled();
      expect(mMPITestService.addMMPITestToCollectionIfMissing).toHaveBeenCalledWith(
        mMPITestCollection,
        ...additionalMMPITests.map(expect.objectContaining),
      );
      expect(comp.mMPITestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MMPITestCard query and add missing value', () => {
      const mMPIAnswer: IMMPIAnswer = { id: 456 };
      const mMPITestCard: IMMPITestCard = { id: 9917 };
      mMPIAnswer.mMPITestCard = mMPITestCard;

      const mMPITestCardCollection: IMMPITestCard[] = [{ id: 29119 }];
      jest.spyOn(mMPITestCardService, 'query').mockReturnValue(of(new HttpResponse({ body: mMPITestCardCollection })));
      const additionalMMPITestCards = [mMPITestCard];
      const expectedCollection: IMMPITestCard[] = [...additionalMMPITestCards, ...mMPITestCardCollection];
      jest.spyOn(mMPITestCardService, 'addMMPITestCardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mMPIAnswer });
      comp.ngOnInit();

      expect(mMPITestCardService.query).toHaveBeenCalled();
      expect(mMPITestCardService.addMMPITestCardToCollectionIfMissing).toHaveBeenCalledWith(
        mMPITestCardCollection,
        ...additionalMMPITestCards.map(expect.objectContaining),
      );
      expect(comp.mMPITestCardsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mMPIAnswer: IMMPIAnswer = { id: 456 };
      const mMPITest: IMMPITest = { id: 25604 };
      mMPIAnswer.mMPITest = mMPITest;
      const mMPITestCard: IMMPITestCard = { id: 17769 };
      mMPIAnswer.mMPITestCard = mMPITestCard;

      activatedRoute.data = of({ mMPIAnswer });
      comp.ngOnInit();

      expect(comp.mMPITestsSharedCollection).toContain(mMPITest);
      expect(comp.mMPITestCardsSharedCollection).toContain(mMPITestCard);
      expect(comp.mMPIAnswer).toEqual(mMPIAnswer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIAnswer>>();
      const mMPIAnswer = { id: 123 };
      jest.spyOn(mMPIAnswerFormService, 'getMMPIAnswer').mockReturnValue(mMPIAnswer);
      jest.spyOn(mMPIAnswerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIAnswer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPIAnswer }));
      saveSubject.complete();

      // THEN
      expect(mMPIAnswerFormService.getMMPIAnswer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mMPIAnswerService.update).toHaveBeenCalledWith(expect.objectContaining(mMPIAnswer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIAnswer>>();
      const mMPIAnswer = { id: 123 };
      jest.spyOn(mMPIAnswerFormService, 'getMMPIAnswer').mockReturnValue({ id: null });
      jest.spyOn(mMPIAnswerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIAnswer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPIAnswer }));
      saveSubject.complete();

      // THEN
      expect(mMPIAnswerFormService.getMMPIAnswer).toHaveBeenCalled();
      expect(mMPIAnswerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIAnswer>>();
      const mMPIAnswer = { id: 123 };
      jest.spyOn(mMPIAnswerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIAnswer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mMPIAnswerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMMPITest', () => {
      it('Should forward to mMPITestService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mMPITestService, 'compareMMPITest');
        comp.compareMMPITest(entity, entity2);
        expect(mMPITestService.compareMMPITest).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMMPITestCard', () => {
      it('Should forward to mMPITestCardService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mMPITestCardService, 'compareMMPITestCard');
        comp.compareMMPITestCard(entity, entity2);
        expect(mMPITestCardService.compareMMPITestCard).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
