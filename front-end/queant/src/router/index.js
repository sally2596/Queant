import { createRouter, createWebHistory } from 'vue-router'
// home
import HomeView from '../views/home/HomeView.vue' 

// bankinfo
import BankInfoView from '../views/bankinfo/BankInfoView.vue'

// portfolio
import PortfolioView from '../views/portfolio/PortfolioView.vue'
import PortfolioEditView from '../views/portfolio/PortfolioEditView.vue'
import PortfolioAddView from '../views/portfolio/PortfolioAddView.vue'

// profile
import ProfileView from '../views/profile/ProfileView.vue'

// auth
import LoginView from '../views/auth/LoginView.vue'
import RegisterView from '../views/auth/RegisterView.vue'
import PasswordFindView from '../views/auth/PasswordFindView.vue'
import NaverView from '../views/auth/NaverView.vue'

// admin
import AdminView from '../views/admin/AdminView.vue'
import AdminManageView from '../views/admin/AdminManageView.vue'
import AdminSpecialView from '../views/admin/AdminSpecialView.vue'

// products
import ProductRecommendationView from '@/views/product/ProductRecommendationView.vue'
import ProductCartView from '@/views/product/ProductCartView.vue'
import ProductSpecialPlusView from '@/views/product/ProductSpecialPlusView.vue'
import ComparisonView from '@/views/product/ComparisonView.vue'

// content
import ContentsView from '@/views/content/ContentsView.vue'

const routes = [
  // home
  { 
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/bankinfo',
    name: 'bankInfo',
    component: BankInfoView
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
  // product
  {
    path : '/product',
    name : 'productRecommend',
    component: ProductRecommendationView
  },
  {
    path : '/product/cart',
    name : 'productCart',
    component: ProductCartView
  },
  {
    path : '/product/special',
    name : 'productSpecialPlus',
    component: ProductSpecialPlusView
  },
  {
    path : '/product/comparison',
    name : 'productComparison',
    component : ComparisonView
  },
  // contents
  {
    path : '/contents',
    name : 'contents',
    component : ContentsView
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
    path: '/passwordfind',
    name: 'passwordFind',
    component: PasswordFindView
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