import { type IRecipe } from '@/shared/model/recipe.model';
import type { IIngredient, Ingredient } from '@/shared/model/ingredient.model';

export interface IInstruction {
  id?: number;
  title?: string | null;
  requiredTime?: number | null;
  description?: string | null;
  recipe?: IRecipe | null;
  ingredients?: IIngredient[];
}

export class Instruction implements IInstruction {
  constructor(
    public id?: number,
    public title?: string | null,
    public requiredTime?: number | null,
    public description?: string | null,
    public ingredients?: IIngredient[],
    public recipe?: IRecipe | null,
  ) {}
}
