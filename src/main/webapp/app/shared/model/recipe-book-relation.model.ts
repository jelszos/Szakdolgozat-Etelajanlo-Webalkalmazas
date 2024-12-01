import { type IRecipe } from '@/shared/model/recipe.model';
import { type IRecipeBook } from '@/shared/model/recipe-book.model';

export interface IRecipeBookRelation {
  id?: number;
  recipe?: IRecipe | null;
  recipeBook?: IRecipeBook | null;
}

export class RecipeBookRelation implements IRecipeBookRelation {
  constructor(
    public id?: number,
    public recipe?: IRecipe | null,
    public recipeBook?: IRecipeBook | null,
  ) {}
}
