// Assuming AVATAR_ONE, AVATAR_TWO, etc., are constants that you have already defined
import {
  AVATAR_ONE,
  AVATAR_TWO,
  AVATAR_THREE,
  AVATAR_FOUR,
  AVATAR_FIVE,
  AVATAR_SIX,
  FOOD_TYPE_APPETIZER,
  FOOD_TYPE_SOUP,
  FOOD_TYPE_SALAD,
  FOOD_TYPE_SAUCE,
  FOOD_TYPE_STEW,
  FOOD_TYPE_DESSERT,
  FOOD_TYPE_NOODLE,
} from '@/constants';
import { useI18n } from 'vue-i18n';

export const functions = {
  // AVATAR
  getAvatarImg: number => {
    const avatars = [AVATAR_ONE, AVATAR_TWO, AVATAR_THREE, AVATAR_FOUR, AVATAR_FIVE, AVATAR_SIX];
    return avatars[number - 1] || avatars[0];
  },
  getAllAvatarImgs() {
    const avatars = [AVATAR_ONE, AVATAR_TWO, AVATAR_THREE, AVATAR_FOUR, AVATAR_FIVE, AVATAR_SIX];
    return avatars;
  },

  // FOOD CATEGORY
  formatFoodCategory(category) {
    if (!category) return '';
    return category.replace(/_/g, ' ').replace(/\b\w/g, char => char.toUpperCase());
  },

  formatFoodCategoryToLabel: category => {
    if (!category) return '';
    return category
      .replace(/_/g, ' ')
      .replace(/\b\w/g, char => char.toUpperCase())
      .replace(/\B\w/g, char => char.toLowerCase());
  },

  // FOOD TYPE
  getFoodType: input => {
    const foodTypes = {
      APPETIZER: FOOD_TYPE_APPETIZER,
      SOUP: FOOD_TYPE_SOUP,
      SALAD: FOOD_TYPE_SALAD,
      SAUCE: FOOD_TYPE_SAUCE,
      STEW: FOOD_TYPE_STEW,
      DESSERT: FOOD_TYPE_DESSERT,
      NOODLE: FOOD_TYPE_NOODLE,
    };

    if (typeof input === 'number') {
      const foodTypeArray = Object.values(foodTypes);
      return foodTypeArray[input - 1] || foodTypeArray[0];
    } else if (typeof input === 'string') {
      const normalizedInput = input.toUpperCase();
      return foodTypes[normalizedInput] || null;
    }

    return null;
  },

  getFoodCategory: input => {
    const foodCategories = {
      CHINESE: 'CHINESE',
      ITALIAN: 'ITALIAN',
      HUNGARIAN: 'HUNGARIAN',
      MEXICAN: 'MEXICAN',
      INDIAN: 'INDIAN',
      JAPANESE: 'JAPANESE',
      THAI: 'THAI',
      AMERICAN: 'AMERICAN',
      EASTERN_EUROPEAN: 'EASTERN_EUROPEAN',
      VIETNAMESE: 'VIETNAMESE',
      RUSSIAN: 'RUSSIAN',
      SPANISH: 'SPANISH',
      FRENCH: 'FRENCH',
      GREEK: 'GREEK',
      MIDDLE_EASTERN: 'MIDDLE_EASTERN',
      LATIN_AMERICAN: 'LATIN_AMERICAN',
      BRITISH: 'BRITISH',
      AUSTRALIAN: 'AUSTRALIAN',
      UKRAINIAN: 'UKRAINIAN',
      GERMAN: 'GERMAN',
    };

    if (typeof input === 'number') {
      const foodCategoryArray = Object.values(foodCategories);
      return foodCategoryArray[input - 1] || foodCategoryArray[0];
    } else if (typeof input === 'string') {
      const normalizedInput = input.toUpperCase();
      return foodCategories[normalizedInput] || null;
    }

    return null;
  },

  getAllFoodCategories() {
    const { t } = useI18n();
    return [
      { label: t('szakdolgozatApp.FoodCategory.CHINESE'), value: 'CHINESE' },
      { label: t('szakdolgozatApp.FoodCategory.ITALIAN'), value: 'ITALIAN' },
      { label: t('szakdolgozatApp.FoodCategory.HUNGARIAN'), value: 'HUNGARIAN' },
      { label: t('szakdolgozatApp.FoodCategory.MEXICAN'), value: 'MEXICAN' },
      { label: t('szakdolgozatApp.FoodCategory.INDIAN'), value: 'INDIAN' },
      { label: t('szakdolgozatApp.FoodCategory.JAPANESE'), value: 'JAPANESE' },
      { label: t('szakdolgozatApp.FoodCategory.THAI'), value: 'THAI' },
      { label: t('szakdolgozatApp.FoodCategory.AMERICAN'), value: 'AMERICAN' },
      { label: t('szakdolgozatApp.FoodCategory.EASTERN_EUROPEAN'), value: 'EASTERN_EUROPEAN' },
      { label: t('szakdolgozatApp.FoodCategory.VIETNAMESE'), value: 'VIETNAMESE' },
      { label: t('szakdolgozatApp.FoodCategory.RUSSIAN'), value: 'RUSSIAN' },
      { label: t('szakdolgozatApp.FoodCategory.SPANISH'), value: 'SPANISH' },
      { label: t('szakdolgozatApp.FoodCategory.FRENCH'), value: 'FRENCH' },
      { label: t('szakdolgozatApp.FoodCategory.GREEK'), value: 'GREEK' },
      { label: t('szakdolgozatApp.FoodCategory.MIDDLE_EASTERN'), value: 'MIDDLE_EASTERN' },
      { label: t('szakdolgozatApp.FoodCategory.LATIN_AMERICAN'), value: 'LATIN_AMERICAN' },
      { label: t('szakdolgozatApp.FoodCategory.BRITISH'), value: 'BRITISH' },
      { label: t('szakdolgozatApp.FoodCategory.AUSTRALIAN'), value: 'AUSTRALIAN' },
      { label: t('szakdolgozatApp.FoodCategory.UKRAINIAN'), value: 'UKRAINIAN' },
      { label: t('szakdolgozatApp.FoodCategory.GERMAN'), value: 'GERMAN' },
    ];
  },

  getAllFoodTypes() {
    const { t } = useI18n();
    const foodTypes = [
      FOOD_TYPE_APPETIZER,
      FOOD_TYPE_SOUP,
      FOOD_TYPE_SALAD,
      FOOD_TYPE_SAUCE,
      FOOD_TYPE_STEW,
      FOOD_TYPE_DESSERT,
      FOOD_TYPE_NOODLE,
    ];
    const foodTypesWithLabels = [
      { label: t('szakdolgozatApp.FoodType.APPETIZER'), image: FOOD_TYPE_APPETIZER, value: 'APPETIZER' },
      { label: t('szakdolgozatApp.FoodType.SOUP'), image: FOOD_TYPE_SOUP, value: 'SOUP' },
      { label: t('szakdolgozatApp.FoodType.SALAD'), image: FOOD_TYPE_SALAD, value: 'SALAD' },
      { label: t('szakdolgozatApp.FoodType.SAUCE'), image: FOOD_TYPE_SAUCE, value: 'SAUCE' },
      { label: t('szakdolgozatApp.FoodType.STEW'), image: FOOD_TYPE_STEW, value: 'STEW' },
      { label: t('szakdolgozatApp.FoodType.DESSERT'), image: FOOD_TYPE_DESSERT, value: 'DESSERT' },
      { label: t('szakdolgozatApp.FoodType.NOODLE'), image: FOOD_TYPE_NOODLE, value: 'NOODLE' },
    ];

    return foodTypesWithLabels;
  },
};
