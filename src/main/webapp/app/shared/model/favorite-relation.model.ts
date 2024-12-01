import { type IUser } from '@/shared/model/user.model';
import { type IRecipe } from '@/shared/model/recipe.model';

export interface IFavoriteRelation {
  id?: number;
  user?: IUser | null;
  recipe?: IRecipe | null;
}

export class FavoriteRelation implements IFavoriteRelation {
  constructor(
    public id?: number,
    public user?: IUser | null,
    public recipe?: IRecipe | null,
  ) {}
}
