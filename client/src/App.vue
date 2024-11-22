<template>
  <TheLayout>
    <RouterLink to="/read">Read</RouterLink>
    <RouterLink to="/write">Write</RouterLink>
    <RouterView />
  </TheLayout>
</template>

<script setup>
import { useRouter } from 'vue-router';
import TheLayout from './components/TheLayout.vue';
import './styles/global.css'; // global css
import { isLogin } from '@/utils/user';

const router = useRouter();

// global navigation guard
router.beforeEach((to, from, next) => {
  if (!isLogin()) {
    console.log('로그인 하지 않은 사용자입니다');

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
