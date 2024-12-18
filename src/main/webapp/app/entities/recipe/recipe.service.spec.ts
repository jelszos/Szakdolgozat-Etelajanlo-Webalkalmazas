/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import RecipeService from './recipe.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Recipe } from '@/shared/model/recipe.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Recipe Service', () => {
    let service: RecipeService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new RecipeService();
      currentDate = new Date();
      elemDefault = new Recipe(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'CHINESE', 'APPETIZER', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { createdDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Recipe', async () => {
        const returnedFromService = { id: 123, createdDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        const expected = { createdDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Recipe', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Recipe', async () => {
        const returnedFromService = {
          title: 'BBBBBB',
          description: 'BBBBBB',
          ingredientNames: 'BBBBBB',
          foodCategory: 'BBBBBB',
          foodType: 'BBBBBB',
          imageUrl: 'BBBBBB',
          createdDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { createdDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Recipe', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Recipe', async () => {
        const patchObject = {
          foodCategory: 'BBBBBB',
          foodType: 'BBBBBB',
          createdDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...new Recipe(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { createdDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Recipe', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Recipe', async () => {
        const returnedFromService = {
          title: 'BBBBBB',
          description: 'BBBBBB',
          ingredientNames: 'BBBBBB',
          foodCategory: 'BBBBBB',
          foodType: 'BBBBBB',
          imageUrl: 'BBBBBB',
          createdDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { createdDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Recipe', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Recipe', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Recipe', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
