import { type IUser } from '@/shared/model/user.model';

export interface IRecipeBook {
  id?: number;
  title?: string | null;
  theme?: string | null;
  description?: string | null;
  tags?: string | null;
  createdDate?: Date | null;
  user?: IUser | null;
  isPrivate: boolean;
}

export class RecipeBook implements IRecipeBook {
  constructor(
    public id?: number,
    public title?: string | null,
    public theme?: string | null,
    public description?: string | null,
    public tags?: string | null,
    public createdDate?: Date | null,
    public user?: IUser | null,
    public isPrivate: boolean,
  ) {}
}
