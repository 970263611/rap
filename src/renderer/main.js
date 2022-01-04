import {createApp} from 'vue'
import App from './App.vue'
import {createRouter, createWebHistory} from 'vue-router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const routes = [
    {path: '/', component: () => import('./components/Test.vue')},
    {path: '/hello', component: () => import('./components/Hello.vue')},
    {path: '/test', component: () => import('./components/Test.vue')}
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

createApp(App).use(router).use(ElementPlus).mount('#app')