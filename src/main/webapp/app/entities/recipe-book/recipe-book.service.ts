import axios from 'axios';

import { type IRecipeBook } from '@/shared/model/recipe-book.model';

const baseApiUrl = 'api/recipe-books';

export default class RecipeBookService {
  public find(id: number): Promise<IRecipeBook> {
    return new Promise<IRecipeBook>((resolve, reject) => {
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

  public retrieve(searchParam): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl, { params: searchParam })
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveIsRecipeInList(recipeId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `/find-in-recipe/${recipeId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveMyRecipeBooks(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `/my-recipe-books`)
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

  public create(entity: IRecipeBook): Promise<IRecipeBook> {
    return new Promise<IRecipeBook>((resolve, reject) => {
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

  public update(entity: IRecipeBook): Promise<IRecipeBook> {
    return new Promise<IRecipeBook>((resolve, reject) => {
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

  public partialUpdate(entity: IRecipeBook): Promise<IRecipeBook> {
    return new Promise<IRecipeBook>((resolve, reject) => {
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

  public addOrRemoveRecipe(recipeBookId: number, recipeId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/` + recipeBookId + '/' + recipeId)
        .then(res => {
          resolve(res);
        })
        .catch(error => {
          reject(error);
        });
    });
  }

  public toggleRecipeBookStatus(recipeBookId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.patch(`${baseApiUrl}/status/` + recipeBookId).then(res => {
        console.log('res', res);
      });
    });
  }

  public isOwnedByUser(): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      axios.get(`${baseApiUrl}/isOwnedByUser`).then(res => {
        console.log('res', res);
        resolve(res.data);
      });
    });
  }
  public getOwner(recipeBookId: number): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      axios.get(`${baseApiUrl}/getOwner/${recipeBookId}`).then(res => {
        console.log('res', res);
        resolve(res.data);
      });
    });
  }
}
