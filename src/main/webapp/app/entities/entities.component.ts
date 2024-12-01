import { defineComponent, provide } from 'vue';

import IngredientService from './ingredient/ingredient.service';
import InstructionService from './instruction/instruction.service';
import RecipeService from './recipe/recipe.service';
import RatingService from './rating/rating.service';
import RecipeBookService from './recipe-book/recipe-book.service';
import RecipeBookRelationService from './recipe-book-relation/recipe-book-relation.service';
import FavoriteRelationService from './favorite-relation/favorite-relation.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('ingredientService', () => new IngredientService());
    provide('instructionService', () => new InstructionService());
    provide('recipeService', () => new RecipeService());
    provide('ratingService', () => new RatingService());
    provide('recipeBookService', () => new RecipeBookService());
    provide('recipeBookRelationService', () => new RecipeBookRelationService());
    provide('favoriteRelationService', () => new FavoriteRelationService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
