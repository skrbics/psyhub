import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MMPIFeatureService } from '../service/mmpi-feature.service';
import { IMMPIFeature } from '../mmpi-feature.model';
import { MMPIFeatureFormService } from './mmpi-feature-form.service';

import { MMPIFeatureUpdateComponent } from './mmpi-feature-update.component';

describe('MMPIFeature Management Update Component', () => {
  let comp: MMPIFeatureUpdateComponent;
  let fixture: ComponentFixture<MMPIFeatureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mMPIFeatureFormService: MMPIFeatureFormService;
  let mMPIFeatureService: MMPIFeatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MMPIFeatureUpdateComponent],
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
      .overrideTemplate(MMPIFeatureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MMPIFeatureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mMPIFeatureFormService = TestBed.inject(MMPIFeatureFormService);
    mMPIFeatureService = TestBed.inject(MMPIFeatureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mMPIFeature: IMMPIFeature = { id: 456 };

      activatedRoute.data = of({ mMPIFeature });
      comp.ngOnInit();

      expect(comp.mMPIFeature).toEqual(mMPIFeature);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIFeature>>();
      const mMPIFeature = { id: 123 };
      jest.spyOn(mMPIFeatureFormService, 'getMMPIFeature').mockReturnValue(mMPIFeature);
      jest.spyOn(mMPIFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPIFeature }));
      saveSubject.complete();

      // THEN
      expect(mMPIFeatureFormService.getMMPIFeature).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mMPIFeatureService.update).toHaveBeenCalledWith(expect.objectContaining(mMPIFeature));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIFeature>>();
      const mMPIFeature = { id: 123 };
      jest.spyOn(mMPIFeatureFormService, 'getMMPIFeature').mockReturnValue({ id: null });
      jest.spyOn(mMPIFeatureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIFeature: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mMPIFeature }));
      saveSubject.complete();

      // THEN
      expect(mMPIFeatureFormService.getMMPIFeature).toHaveBeenCalled();
      expect(mMPIFeatureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMMPIFeature>>();
      const mMPIFeature = { id: 123 };
      jest.spyOn(mMPIFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mMPIFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mMPIFeatureService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
