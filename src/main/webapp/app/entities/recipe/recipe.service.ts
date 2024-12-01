import axios from 'axios';

import { type IRecipe } from '@/shared/model/recipe.model';

const baseApiUrl = 'api/recipes';

export default class RecipeService {
  public find(id: number): Promise<IRecipe> {
    return new Promise<IRecipe>((resolve, reject) => {
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

  public create(entity: IRecipe): Promise<IRecipe> {
    return new Promise<IRecipe>((resolve, reject) => {
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

  public uploadImage(formData: FormData): Promise<string> {
    const config = {
      withCredentials: true,
      headers: {
        Accept: 'application/json',
        'Content-Type': 'multipart/form-data',
      },
    };
    return new Promise<string>((resolve, reject) => {
      axios
        .post('api/images/upload', formData, config)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          console.error('Error while uploading an image', err);
          reject(err);
        });
    });
  }

  public update(entity: IRecipe): Promise<IRecipe> {
    return new Promise<IRecipe>((resolve, reject) => {
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

  public partialUpdate(entity: IRecipe): Promise<IRecipe> {
    return new Promise<IRecipe>((resolve, reject) => {
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

  public retrieveLatestRecipes(): Promise<IRecipe[]> {
    return new Promise<IRecipe[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/queries/latest`).then(res => {
        resolve(res);
      });
    });
  }

  public retrieveHighestFavoriteCountRecipes(): Promise<IRecipe[]> {
    return new Promise<IRecipe[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/queries/highest_favorite_count`).then(res => {
        resolve(res);
      });
    });
  }

  public retrieveSameFoodCategoryRecipes(food_category): Promise<IRecipe[]> {
    return new Promise<IRecipe[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/queries/same_food_category/${food_category}`).then(res => {
        resolve(res);
      });
    });
  }

  public retrieveRecipeRatings(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/ratings`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public searchRecipes(params): Promise<IRecipe[]> {
    return new Promise<IRecipe[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/search`, { params: params }).then(res => {
        resolve(res);
      });
    });
  }
}
