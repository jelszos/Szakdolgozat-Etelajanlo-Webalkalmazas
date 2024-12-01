// @ts-check

import globals from 'globals';
import prettier from 'eslint-plugin-prettier/recommended';
import tseslint from 'typescript-eslint';
import js from '@eslint/js';
import vue from 'eslint-plugin-vue';

export default tseslint.config(
  {
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
  },
  { ignores: ['src/main/docker/'] },
  { ignores: ['target/classes/static/', 'target/'] },
  js.configs.recommended,
  ...tseslint.configs.recommended.map(config =>
    config.name === 'typescript-eslint/base' ? config : { ...config, files: ['**/*.ts', '**/*.tsx', '**/*.mts', '**/*.cts'] },
  ),
  {
    files: ['**/*.{js,cjs,mjs}'],
    rules: {
      'no-unused-vars': ['error', { argsIgnorePattern: '^_' }],
    },
  },
  ...vue.configs['flat/recommended'],
  {
    files: ['**/*.vue'],
    languageOptions: {
      parserOptions: { parser: '@typescript-eslint/parser' },
      globals: { ...globals.browser },
    },
  },
  {
    files: ['src/main/webapp/**/*.vue', 'src/main/webapp/**/*.ts'],
    languageOptions: {
      globals: { ...globals.browser },
    },
    rules: {
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'vue/multi-word-component-names': 'off',
      '@typescript-eslint/no-explicit-any': 'off',
      '@typescript-eslint/no-unused-vars': 'off',
      '@typescript-eslint/explicit-module-boundary-types': 'off',
      '@typescript-eslint/no-empty-function': 'off',
      '@typescript-eslint/ban-ts-comment': 'off',
      '@typescript-eslint/no-var-requires': 'off',
      '@typescript-eslint/consistent-type-imports': 'error',
      'vue/no-v-text-v-html-on-component': ['error', { allow: ['router-link', 'b-alert', 'b-badge', 'b-button', 'b-link'] }],
      'vue/no-reserved-component-names': 'off',
      'vue/attributes-order': 'off',
      'vue/component-definition-name-casing': 'off',
      'vue/require-default-prop': 'off',
      'vue/order-in-components': [
        'error',
        {
          order: [
            'el', // Root DOM element selector
            'name', // Component name
            'parent', // Parent instance reference
            'functional', // Functional component flag
            ['delimiters', 'comments'], // Options
            ['components', 'directives', 'filters'], // Custom Vue items
            'extends', // Base component
            'mixins', // Mixins
            'inheritAttrs', // Attribute inheritance flag
            'props', // Props declaration
            'propsData', // Props default values (when using `new Vue`)
            'emits', // Event declaration
            'setup', // Composition API setup function
            'data', // Component's reactive state
            'computed', // Computed properties
            'watch', // Watchers
            'LIFECYCLE_HOOKS', // Lifecycle hooks (created, mounted, etc.)
            'methods', // Component methods
            ['template', 'render'], // Template or render function
            'renderError', // Error rendering function
          ],
        },
      ],
      'no-empty': [
        'error',
        {
          allowEmptyCatch: true,
        },
      ],
    },
  },
  {
    files: ['src/main/webapp/**/*.spec.ts'],
    rules: {
      '@typescript-eslint/no-empty-function': 'off',
    },
  },
  prettier,
);
