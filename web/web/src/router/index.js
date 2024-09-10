import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import StudentView from '../views/student/View.vue'
import StudentCreateView from '../views/student/Create.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/student',
      name: 'student',
      component: StudentView
    },
    {
      path: '/student/create',
      name: 'student-create',
      component: StudentCreateView
    },
    {
      path: '/student/create/:id',
      name: 'student-create-update',
      component: StudentCreateView
    }
  ]
})

export default router
