import axios from 'axios';

import { type IRecipeBookRelation } from '@/shared/model/recipe-book-relation.model';

const baseApiUrl = 'api/recipe-book-relations';

export default class RecipeBookRelationService {
  public find(id: number): Promise<IRecipeBookRelation> {
    return new Promise<IRecipeBookRelation>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IRecipeBookRelation): Promise<IRecipeBookRelation> {
    return new Promise<IRecipeBookRelation>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IRecipeBookRelation): Promise<IRecipeBookRelation> {
    return new Promise<IRecipeBookRelation>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IRecipeBookRelation): Promise<IRecipeBookRelation> {
    return new Promise<IRecipeBookRelation>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getAllByRecipeId(recipeBookId: number, recipeId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/all-by-recipe-book-id/${recipeBookId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => reject(err));
    });
  }

  public deleteByRecipeNBookId(recipeId, recipeBookId): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/delete/${recipeId}/${recipeBookId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => reject(err));
    });
  }
}
