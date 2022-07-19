import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/home/HomeView.vue' 

import ProfileView from '../views/profile/ProfileView.vue'
import ProfileCheckView from '../views/profile/ProfileCheckView.vue'
import ProfileEditView from '../views/profile/ProfileEditView.vue'
import PortfolioView from '../views/profile/PortfolioView.vue'

import LoginView from '../views/auth/LoginView.vue'
import RegisterView from '../views/auth/RegisterView.vue'
import CredentialsFindView from '../views/auth/CredentialsFindView.vue'
import NaverView from '../views/auth/NaverView.vue'

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
    path: '/profile/check',
    name: 'profileCheck',
    component: ProfileCheckView
  },
  {
    path: '/profile/edit',
    name: 'profileEdit',
    component: ProfileEditView
  },
  {
    path: '/profile/portfolio',
    name: 'portfolio',
    component: PortfolioView
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
    path: '/find',
    name: 'credentialsFind',
    component: CredentialsFindView
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