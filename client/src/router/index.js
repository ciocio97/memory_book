import TheHome from '@/views/TheHome.vue';
import TheReader from '@/views/TheReader.vue';
import TheWriter from '@/views/TheWriter.vue';
import { createMemoryHistory, createRouter } from 'vue-router';

const routes = [
  { path: '/', component: TheHome },
  { path: '/write', component: TheWriter },
  { path: '/read', component: TheReader },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

export default router;
