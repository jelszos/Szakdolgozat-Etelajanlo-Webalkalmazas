import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Ingredient = () => import('@/entities/ingredient/ingredient.vue');
const IngredientUpdate = () => import('@/entities/ingredient/ingredient-update.vue');
const IngredientDetails = () => import('@/entities/ingredient/ingredient-details.vue');

const Instruction = () => import('@/entities/instruction/instruction.vue');
const InstructionUpdate = () => import('@/entities/instruction/instruction-update.vue');
const InstructionDetails = () => import('@/entities/instruction/instruction-details.vue');

const Recipe = () => import('@/entities/recipe/recipe.vue');
const RecipeUpdate = () => import('@/entities/recipe/recipe-update.vue');
const RecipeDetails = () => import('@/entities/recipe/recipe-details.vue');
const RecipeDetailsRatings = () => import('@/entities/recipe/recipe-details-ratings.vue');

const Rating = () => import('@/entities/rating/rating.vue');
const RatingUpdate = () => import('@/entities/rating/rating-update.vue');
const RatingDetails = () => import('@/entities/rating/rating-details.vue');

const RecipeBook = () => import('@/entities/recipe-book/recipe-book.vue');
const RecipeBookUpdate = () => import('@/entities/recipe-book/recipe-book-update.vue');
const RecipeBookDetails = () => import('@/entities/recipe-book/recipe-book-details.vue');
const RecipeBookPage = () => import('@/entities/recipe-book/recipe-book-page.vue');
const RecipeBookMyPage = () => import('@/entities/recipe-book/recipe-book-mypage.vue');

const RecipeBookRelation = () => import('@/entities/recipe-book-relation/recipe-book-relation.vue');
const RecipeBookRelationUpdate = () => import('@/entities/recipe-book-relation/recipe-book-relation-update.vue');
const RecipeBookRelationDetails = () => import('@/entities/recipe-book-relation/recipe-book-relation-details.vue');

const FavoriteRelation = () => import('@/entities/favorite-relation/favorite-relation.vue');
const FavoriteRelationUpdate = () => import('@/entities/favorite-relation/favorite-relation-update.vue');
const FavoriteRelationDetails = () => import('@/entities/favorite-relation/favorite-relation-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'ingredient',
      name: 'Ingredient',
      component: Ingredient,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ingredient/new',
      name: 'IngredientCreate',
      component: IngredientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ingredient/:ingredientId/edit',
      name: 'IngredientEdit',
      component: IngredientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ingredient/:ingredientId/view',
      name: 'IngredientView',
      component: IngredientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'instruction',
      name: 'Instruction',
      component: Instruction,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'instruction/new',
      name: 'InstructionCreate',
      component: InstructionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'instruction/:instructionId/edit',
      name: 'InstructionEdit',
      component: InstructionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'instruction/:instructionId/view',
      name: 'InstructionView',
      component: InstructionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe',
      name: 'Recipe',
      component: Recipe,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe/new',
      name: 'RecipeCreate',
      component: RecipeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe/:recipeId/edit',
      name: 'RecipeEdit',
      component: RecipeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe/:recipeId/view',
      name: 'RecipeView',
      component: RecipeDetails,
      meta: { authorities: '' },
    },
    {
      path: 'recipe/:recipeId/ratings',
      name: 'RecipeDetailsRatings',
      component: RecipeDetailsRatings,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rating',
      name: 'Rating',
      component: Rating,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rating/new',
      name: 'RatingCreate',
      component: RatingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rating/:ratingId/edit',
      name: 'RatingEdit',
      component: RatingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rating/:ratingId/view',
      name: 'RatingView',
      component: RatingDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book',
      name: 'RecipeBook',
      component: RecipeBook,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book-page',
      name: 'RecipeBookPage',
      component: RecipeBookPage,
    },
    {
      path: 'recipe-book-my-page',
      name: 'RecipeBookMyPage',
      component: RecipeBookMyPage,
    },
    {
      path: 'recipe-book/new',
      name: 'RecipeBookCreate',
      component: RecipeBookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book/:recipeBookId/edit',
      name: 'RecipeBookEdit',
      component: RecipeBookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book/:recipeBookId/view',
      name: 'RecipeBookView',
      component: RecipeBookDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book-relation',
      name: 'RecipeBookRelation',
      component: RecipeBookRelation,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book-relation/new',
      name: 'RecipeBookRelationCreate',
      component: RecipeBookRelationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book-relation/:recipeBookRelationId/edit',
      name: 'RecipeBookRelationEdit',
      component: RecipeBookRelationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recipe-book-relation/:recipeBookRelationId/view',
      name: 'RecipeBookRelationView',
      component: RecipeBookRelationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'favorite-relation',
      name: 'FavoriteRelation',
      component: FavoriteRelation,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'favorite-relation/new',
      name: 'FavoriteRelationCreate',
      component: FavoriteRelationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'favorite-relation/:favoriteRelationId/edit',
      name: 'FavoriteRelationEdit',
      component: FavoriteRelationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'favorite-relation/:favoriteRelationId/view',
      name: 'FavoriteRelationView',
      component: FavoriteRelationDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
