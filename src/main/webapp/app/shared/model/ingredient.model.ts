import { type IInstruction } from '@/shared/model/instruction.model';

import { type Unit } from '@/shared/model/enumerations/unit.model';
export interface IIngredient {
  id?: number;
  name?: string | null;
  amount?: number | null;
  unit?: keyof typeof Unit | null;
  instruction?: IInstruction | null;
}

export class Ingredient implements IIngredient {
  constructor(
    public id?: number,
    public name?: string | null,
    public amount?: number | null,
    public unit?: keyof typeof Unit | null,
    public instruction?: IInstruction | null,
  ) {}
}
