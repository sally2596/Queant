import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'


// oauth
import KakaoView from '@/views/oauth/KakaoView.vue'
import NaverView from '@/views/oauth/NaverView.vue'
import GoogleView from '@/views/oauth/GoogleView.vue'

// home
import HomeView from '@/views/home/HomeView.vue' 

// bankinfo
import BankInfoListView from '@/views/bankinfo/BankInfoListView.vue'
import BankInfoDetailView from '@/views/bankinfo/BankInfoDetailView.vue'

// portfolio
import PortfolioView from '@/views/portfolio/PortfolioView.vue'
import PortfolioEditView from '@/views/portfolio/PortfolioEditView.vue'
import PortfolioAddView from '@/views/portfolio/PortfolioAddView.vue'

// profile
import ProfileView from '@/views/profile/ProfileView.vue'
import PasswordEditView from '@/views/profile/PasswordEditView.vue'

// auth
import LoginView from '@/views/auth/LoginView.vue'
import RegisterView from '@/views/auth/RegisterView.vue'
import PasswordFindView from '@/views/auth/PasswordFindView.vue'

// admin
import AdminUserListView from '@/views/admin/AdminUserListView.vue'
import AdminContentView from '@/views/admin/AdminContentView.vue'
import AdminSpecialView from '@/views/admin/AdminSpecialView.vue'

// products
import ProductRecommendationView from '@/views/product/ProductRecommendationView.vue'
import ProductCartView from '@/views/product/ProductCartView.vue'
import ProductSpecialPlusView from '@/views/product/ProductSpecialPlusView.vue'
import ComparisonView from '@/views/product/ComparisonView.vue'
import ProductResults from '@/components/ProductResults.vue'
import ProductSearchView from '@/views/product/ProductSearchView.vue'
import ProductDetailView from '@/views/product/ProductDetailView.vue'

// content
import ContentListView from '@/views/content/ContentListView.vue'
import ContentCreateView from '@/views/content/ContentCreateView.vue'
import ContentDetailView from '@/views/content/ContentDetailView.vue'

const routes = [
  // oauth
  {
    path: '/kakao',
    name: 'kakao',
    component: KakaoView
  },
  {
    path: '/naver',
    name: 'naver',
    component: NaverView
  },
  {
    path: '/google',
    name: 'google',
    component: GoogleView
  },
  // home
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  // bankinfo
  {
    path: '/bankinfo',
    name: 'bankInfoList',
    component: BankInfoListView
  },
  {
    path: '/bankinfo/:bankId',
    name: 'bankInfoDetail',
    component: BankInfoDetailView
  },
  // portfolio
  {
    path: '/portfolio',
    name: 'portfolio',
    component: PortfolioView,
    meta: { isLoggedIn: true }
  },
  {
    path: '/portfolio/edit',
    name: 'portfolioEdit',
    component: PortfolioEditView,
    meta: { isLoggedIn: true }
  },
  {
    path: '/portfolio/add',
    name: 'portfolioAdd',
    component: PortfolioAddView,
    meta: { isLoggedIn: true }
  },
  // product
  {
    path: '/product',
    name: 'productRecommend',
    component: ProductRecommendationView,
  },
  {
    path: '/product/results',
    name: 'productResults',
    component: ProductResults
  },
  {
    path: '/product/cart',
    name: 'productCart',
    component: ProductCartView
  },
  {
    path: '/product/special',
    name: 'productSpecialPlus',
    component: ProductSpecialPlusView
  },
  {
    path: '/product/comparison',
    name: 'productComparison',
    component: ComparisonView
  },
  {
    path: '/product/:productId',
    name: 'productDetail',
    component: ProductDetailView
  },
  {
    path: '/product/search/:text',
    name: 'productSearch',
    component: ProductSearchView
  },
  // contents
  {
    path: '/contents',
    name: 'contents',
    component: ContentListView
  },
  {
    path: '/contents/create',
    name: 'contentCreate',
    component: ContentCreateView,
    meta: { isAdmin: true }
  },
  {
    path: '/content/:contentId',
    name: 'contentDetail',
    component: ContentDetailView
  },
  // profile
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    meta: { isLoggedIn: true }
  },
  {
    path: '/passwordEditByProfile',
    name: 'profilePasswordEdit',
    component: PasswordEditView
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
  // admin
  {
    path: '/admin',
    name: 'admin',
    component: AdminUserListView,
    meta: { isAdmin: true }
  },
  {
    path: '/admin/content',
    name: 'adminContent',
    component: AdminContentView
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

router.beforeEach((to, from, next) => {
  let isLoggedIn = store.getters.isLoggedIn
  let isAdmin = store.getters.isAdmin
	// $route.matched 배열에 저장된 라우터 중 meta 필드에 'isLoggedIn'가 있는지 찾는다.
	if (to.matched.some(record => record.meta.isLoggedIn)) {
    if (!isLoggedIn) { // 로그인 되어있지 않으면 로그인 페이지로 이동
      alert('로그인이 필요합니다.')
      next({ name: 'login' })
    } else { // 로그인 되어 있다면 그대로 라우터 이동
      next()
    }
  } else if (to.matched.some(record => record.meta.isAdmin)) {
    if (isLoggedIn && isAdmin) {
      next()
    } else {
      alert('접근 권한이 없습니다.')
      history.back()
    }
  } else {
    next()
  }
})

export default router
