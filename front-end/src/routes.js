

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import JoinComplete from './views/user/JoinComplete.vue'
import FindPassword from './views/user/FindPassword.vue'
export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/user/findPassword',
        name : 'FindPassword',
        component : FindPassword
    },
    {
        path : '/user/joincomplete',
        name : 'JoinComplete',
        component : JoinComplete
    }
]
