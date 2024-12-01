import { type IUser } from '@/shared/model/user.model';
import { type IRecipe } from '@/shared/model/recipe.model';

export interface IRating {
  id?: number;
  rate?: number | null;
  title?: string | null;
  description?: string | null;
  imageUrl?: string | null;
  createdDate?: Date | null;
  user?: IUser | null;
  recipe?: IRecipe | null;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public rate?: number | null,
    public title?: string | null,
    public description?: string | null,
    public imageUrl?: string | null,
    public createdDate?: Date | null,
    public user?: IUser | null,
    public recipe?: IRecipe | null,
  ) {}
}
