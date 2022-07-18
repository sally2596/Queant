import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/home/HomeView.vue' 

import ProfileView from '../views/profile/ProfileView.vue'
import ProfileFindView from '../views/profile/ProfileFindView.vue'
import ProfileCheckView from '../views/profile/ProfileCheckView.vue'
import ProfileEditView from '../views/profile/ProfileEditView.vue'

import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import NaverView from '../views/NaverView.vue'

import AdminView from '../views/admin/AdminView.vue'
import AdminManageView from '../views/admin/AdminManageView.vue'
import AdminSpecialView from '../views/admin/AdminSpecialView.vue'


const routes = [
  // home
  { 
    path: '/',
    name: 'home',
    component: HomeView
  },
  // profile
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView
  },
  {
    path: '/profile/find',
    name: 'profileFind',
    component: ProfileFindView
  },
  {
    path: '/profile/check',
    name: 'profileCheck',
    component: ProfileCheckView
  },
  {
    path: '/profile/edit',
    name: 'profileEdit',
    component: ProfileEditView
  },
  // auth
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/naver',
    name: 'naver',
    component: NaverView
  },
  // admin
  {
    path: '/admin',
    name: 'admin',
    component: AdminView
  },
  {
    path: '/admin/manage',
    name: 'adminManage',
    component: AdminManageView
  },
  {
    path: '/admin/special',
    name: 'adminSpecial',
    component: AdminSpecialView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router