import { vitest } from 'vitest';
import { shallowMount } from '@vue/test-utils';
import Home from './home.vue';
import { ref, computed } from 'vue';

// Mock the food categories JSON to avoid issues during testing
const foodCategoriesJson = {
  szakdolgozatApp: {
    FoodCategory: ['Category 1', 'Category 2', 'Category 3'], // Mock categories for the test
  },
};

const mockLatestRecipes = [{ id: 1, name: 'Recipe 1' }];
const mockHighestFavoriteCountRecipes = [{ id: 2, name: 'Recipe 2' }];
const mockSameFoodCategoryRecipes = [{ id: 3, name: 'Recipe 3' }];
const mockRecipeBooks = [{ id: 4, name: 'Recipe Book 1' }];

describe('Home.vue', () => {
  let home;
  let authenticated;
  let currentUsername;
  const loginService = { openLogin: vitest.fn(), logout: vitest.fn() };
  const recipeService = {
    retrieveLatestRecipes: vitest.fn().mockResolvedValue({ data: mockLatestRecipes }),
    retrieveHighestFavoriteCountRecipes: vitest.fn().mockResolvedValue({ data: mockHighestFavoriteCountRecipes }),
    retrieveSameFoodCategoryRecipes: vitest.fn().mockResolvedValue({ data: mockSameFoodCategoryRecipes }),
  };
  const recipeBookService = { retrieve: vitest.fn().mockResolvedValue({ data: mockRecipeBooks }) };

  beforeEach(() => {
    authenticated = ref(false);
    currentUsername = ref('');
    const wrapper = shallowMount(Home, {
      global: {
        stubs: {
          'router-link': true,
          'font-awesome-icon': true,
          'b-carousel': true,
          'b-pagination': true,
        },
        provide: {
          loginService,
          authenticated,
          currentUsername,
          recipeService,
          recipeBookService,
        },
        mocks: {
          foodCategoriesJson, // Mock the imported foodCategoriesJson
        },
      },
    });
    home = wrapper.vm;
  });

  it('should not have user data set initially', () => {
    expect(home.authenticated).toBeFalsy();
    expect(home.username).toBe('');
  });

  it('should display user data after authentication', () => {
    authenticated.value = true;
    currentUsername.value = 'testUser';

    expect(home.authenticated).toBeTruthy();
    expect(home.username).toBe('testUser');
  });

  it('should call openLogin from loginService', () => {
    home.openLogin();
    expect(loginService.openLogin).toHaveBeenCalled();
  });

  it('should handle fetching state when retrieving recipes', async () => {
    const mockLatestRecipes = [{ id: 1, name: 'Recipe 1' }];

    recipeService.retrieveLatestRecipes.mockResolvedValue({ data: mockLatestRecipes });

    home.isFetching = true;
    await home.retrieveRecipes();

    expect(home.isFetching).toBeFalsy();
  });

  it('should return a label for food category', () => {
    const category = 'vegan';
    const label = home.formatFoodCategoryToLabel(category);
    expect(label).toBeTruthy();
  });

  it('should return avatar image correctly', () => {
    const avatar = home.getAvatarImg(1);
    expect(avatar).toBeTruthy();
  });

  it('should retrieve correct food type', () => {
    const foodType = home.getFoodType(1);
    expect(foodType).toBeTruthy();
  });
});
