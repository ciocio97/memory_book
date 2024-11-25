<template>
  <TheLayout>
    <RouterView />
  </TheLayout>
</template>

<script setup>
import { useRouter } from 'vue-router';
import TheLayout from './components/TheLayout.vue';
import './styles/global.css'; // global css
import { isLogin } from '@/utils/user';

const router = useRouter();
const javascript_key = import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY;

Kakao.init(javascript_key);

// global navigation guard
router.beforeEach((to, from, next) => {
  if (!isLogin()) {
    if (to.path === '/') {
      next();
      return;
    }
    next('/');
    return;
  }

  next();
});
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
