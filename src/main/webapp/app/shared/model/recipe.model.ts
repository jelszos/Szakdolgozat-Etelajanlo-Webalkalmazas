import { type IUser } from '@/shared/model/user.model';

import { type FoodCategory } from '@/shared/model/enumerations/food-category.model';
import { type FoodType } from '@/shared/model/enumerations/food-type.model';
import type { Rating } from '@/shared/model/rating.model';
import type { Instruction } from '@/shared/model/instruction.model';
export interface IRecipe {
  id?: number;
  title?: string | null;
  description?: string | null;
  ingredientNames?: string | null;
  foodCategory?: keyof typeof FoodCategory | null;
  foodType?: keyof typeof FoodType | null;
  imageUrl?: string | null;
  createdDate?: Date | null;
  user?: IUser | null;
  instructions?: Instruction[] | null;
  ratings?: Rating[] | null;
  recipeBookRelations?: [] | null;
  isFavorite?: false;
}

export class Recipe implements IRecipe {
  constructor(
    public id?: number,
    public title?: string | null,
    public description?: string | null,
    public ingredientNames?: string | null,
    public foodCategory?: keyof typeof FoodCategory | null,
    public foodType?: keyof typeof FoodType | null,
    public imageUrl?: string | null,
    public createdDate?: Date | null,
    public user?: IUser | null,
    public instructions?: Instruction[] | null,
    public ratings?: Rating[] | null,
    public isFavorite?: false,
  ) {}
}
