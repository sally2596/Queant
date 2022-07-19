import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/home/HomeView.vue' 

import PortfolioView from '../views/portfolio/PortfolioView.vue'
import PortfolioEditView from '../views/portfolio/PortfolioEditView.vue'
import PortfolioAddView from '../views/portfolio/PortfolioAddView.vue'

import ProfileView from '../views/profile/ProfileView.vue'

import LoginView from '../views/auth/LoginView.vue'
import RegisterView from '../views/auth/RegisterView.vue'
import PwFindView from '../views/auth/PwFindView.vue'
import PwEditView from '../views/auth/PwEditView.vue'
import NaverView from '../views/auth/NaverView.vue'
import EmailAuthentication from '@/views/auth/EmailAuthentication.vue'

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
  // portfolio
  {
    path: '/portfolio',
    name: 'portfolio',
    component: PortfolioView
  },
  {
    path: '/portfolio/edit',
    name: 'portfolioEdit',
    component: PortfolioEditView
  },
  {
    path: '/portfolio/add',
    name: 'portfolioAdd',
    component: PortfolioAddView
  },
  // profile
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView
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
    path: '/pwfind',
    name: 'pwFind',
    component: PwFindView
  },
  {
    path: '/emailauthen',
    name: 'emailauthentication',
    component: EmailAuthentication
  },
  {
    path: '/pwedit',
    name: 'pwEdit',
    component: PwEditView
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