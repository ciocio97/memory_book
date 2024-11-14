import globals from 'globals';
import pluginVue from 'eslint-plugin-vue';

/** @type {import('eslint').Linter.Config[]} */
export default [
  { files: ['**/*.{js,mjs,cjs,vue}'] },
  { languageOptions: { globals: globals.browser } },
  {
    rules: {
      'no-console': 'warn',
      'no-unused-vars': 'warn',
      '@stylistic/js/indent': ['warn', 2],
      '@stylistic/js/quotes': ['warn', 'single'],
    },
  },
  ...pluginVue.configs['flat/essential'],
];
