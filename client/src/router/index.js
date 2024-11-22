import TheHome from '@/views/TheHome.vue';
import TheReader from '@/views/TheReader.vue';
import TheWriter from '@/views/TheWriter.vue';
import TheSelection from '@/views/TheSelection.vue';
import { createWebHistory, createRouter } from 'vue-router';

const routes = [
  { path: '/', component: TheHome },
  { path: '/select', component: TheSelection },
  { path: '/write', component: TheWriter },
  { path: '/read', component: TheReader },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
