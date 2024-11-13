import globals from "globals";
import pluginJs from "@eslint/js";
import pluginVue from "@eslint-plugin-vue";
import stylisticJs from "@stylistic/eslint-plugin-js";
import parser from "@babel/eslint-parser";
import eslintConfigPrettier from "eslint-config-prettier";

/** @type {import('eslint').Linter.Config[]} */
export default [
  {
    files: ["**/*.{js,mjs,cjs,vue}"],
    languageOptions: { 
      globals: globals.browser 
    },
    extends: [
      "prettier"
    ],
    parser: {
      "@babel/eslint-parser" : parser
    },
    plugins: { 
      '@stylistic/js': stylisticJs
    },
    rules: {
      "no-console": "warn",
      "no-unused-vars": "warn",
      "@stylistic/js/indent": ["error", 2],
      "@stylistic/js/quotes": ["error", "single"]
    }
  },
  pluginJs.configs.recommended,
  ...pluginVue.configs["flat/essential"],
  eslintConfigPrettier,
];

// "eslintConfig": {
//   "root": true,
//   "env": {
//     "node": true
//   },
//   "extends": [
//     "plugin:vue/vue3-essential",
//     "eslint:recommended"
//   ],
//   "parserOptions": {
//     "parser": "@babel/eslint-parser",
//     "requireConfigFile": false
//   },
//   "rules": {
//     "no-console": "warn",
//     "no-unused-vars": "warn"
//   }
// },