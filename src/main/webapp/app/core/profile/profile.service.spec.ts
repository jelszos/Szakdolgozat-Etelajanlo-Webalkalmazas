import axios from 'axios';
import sinon from 'sinon';
import ProfileService from './profile.service';
import type { IUser } from '@/shared/model/user.model';

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
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Profile Service', () => {
    let service: ProfileService;
    let elemDefault: IUser;
    let currentDate: Date;

    beforeEach(() => {
      service = new ProfileService();
      currentDate = new Date();
      elemDefault = { id: 123, login: 'user123', email: 'user@example.com' }; // Sample user object
    });

    describe('Service methods', () => {
      it('should get a User Profile', async () => {
        const returnedFromService = { ...elemDefault };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.get('123').then(res => {
          expect(res.data).toMatchObject(elemDefault);
        });
      });

      it('should not get a User Profile', async () => {
        axiosStub.get.rejects(error);

        return service
          .get('123')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a User Profile', async () => {
        const returnedFromService = { id: 123, ...elemDefault };
        const expected = { ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });

        return service.create(elemDefault).then(res => {
          expect(res.data).toMatchObject(expected);
        });
      });

      it('should not create a User Profile', async () => {
        axiosStub.post.rejects(error);

        return service
          .create(elemDefault)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a User Profile', async () => {
        const returnedFromService = { ...elemDefault, login: 'updatedUser' };
        const expected = { ...returnedFromService };

        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(elemDefault).then(res => {
          expect(res.data).toMatchObject(expected);
        });
      });

      it('should not update a User Profile', async () => {
        axiosStub.put.rejects(error);

        return service
          .update(elemDefault)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a User Profile', async () => {
        axiosStub.delete.resolves({ ok: true });

        return service.remove(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a User Profile', async () => {
        axiosStub.delete.rejects(error);

        return service
          .remove(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should retrieve a list of users', async () => {
        const returnedFromService = { ...elemDefault };
        axiosStub.get.resolves([returnedFromService]);

        return service.retrieve().then(res => {
          expect(res).toContainEqual(returnedFromService);
        });
      });

      it('should not retrieve a list of users', async () => {
        axiosStub.get.rejects(error);
        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should retrieve authorities', async () => {
        const authorities = [{ name: 'ADMIN' }, { name: 'USER' }];
        axiosStub.get.resolves({ data: authorities });

        return service.retrieveAuthorities().then(res => {
          expect(res.data).toEqual(['ADMIN', 'USER']);
        });
      });

      it('should not retrieve authorities', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieveAuthorities()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
