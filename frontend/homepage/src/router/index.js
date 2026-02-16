import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/Homepage.vue'
import Rooms from '../components/Rooms.vue'

const routes = [
    { path: '/', redirect: '/home' },
    { path: '/home', component: HomePage },
    { path: '/rooms', component: Rooms }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router