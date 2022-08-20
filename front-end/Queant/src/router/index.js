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

// profile
import ProfileView from '@/views/profile/ProfileView.vue'

// auth
import LoginView from '@/views/auth/LoginView.vue'
import RegisterView from '@/views/auth/RegisterView.vue'
import PasswordFindView from '@/views/auth/PasswordFindView.vue'

// admin
import AdminUserListView from '@/views/admin/AdminUserListView.vue'
import AdminSpecialView from '@/views/admin/AdminSpecialView.vue'

// products
import ProductRecommendationView from '@/views/product/ProductRecommendationView.vue'
import ProductCartView from '@/views/product/ProductCartView.vue'
import ProductSpecialPlusView from '@/views/product/ProductSpecialPlusView.vue'
import ProductComparisonView from '@/views/product/ProductComparisonView.vue'
import ProductDepositResultView from '@/views/product/ProductDepositResultView.vue'
import ProductSavingResultView from '@/views/product/ProductSavingResultView.vue'
import ProductSearchResultView from '@/views/product/ProductSearchResultView.vue'
import ProductDetailView from '@/views/product/ProductDetailView.vue'

// content
import ContentListView from '@/views/content/ContentListView.vue'
import ContentCreateView from '@/views/content/ContentCreateView.vue'
import ContentDetailView from '@/views/content/ContentDetailView.vue'
import ContentEditView from '@/views/content/ContentEditView.vue'

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
  // product
  {
    path: '/product',
    name: 'productRecommend',
    component: ProductRecommendationView,
  },
  {
    path: '/product/deposit',
    name: 'productDepositResult',
    component: ProductDepositResultView
  },
  {
    path: '/product/saving',
    name: 'productSavingResult',
    component: ProductSavingResultView
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
    component: ProductComparisonView,
    meta: { isLoggedIn: true }
  },
  {
    path: '/product/search/:text',
    name: 'productSearchResult',
    component: ProductSearchResultView
  },
  {
    path: '/product/:productId',
    name: 'productDetail',
    component: ProductDetailView
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
  {
    path: '/content/:contentId/edit',
    name : 'contentEdit',
    component: ContentEditView,
    meta: { isAdmin: true }
  },
  // profile
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    meta: { isLoggedIn: true }
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
    path: '/admin/special',
    name: 'adminSpecial',
    component: AdminSpecialView,
    meta: { isAdmin: true }
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(){
    return { top: 0 }
  },
})

router.beforeEach((to, from, next) => {
  let isLoggedIn = store.getters.isLoggedIn
  let isAdmin = store.getters.isAdmin
	// $route.matched 배열에 저장된 라우터 중 meta 필드에 'isLoggedIn'가 있는지 찾는다.
	if (to.matched.some(record => record.meta.isLoggedIn)) {
    if (!isLoggedIn) { // 로그인 되어있지 않으면 로그인 페이지로 이동
      if (confirm('로그인이 필요합니다. 로그인 하시겠어요?') === true) {
        next({ name: 'login' })
      } else {
        return
      }
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
