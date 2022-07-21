const HOST = 'http://localhost/'

const MEMBER = 'member/'

export default {
  member: {
    register: () => HOST + MEMBER + 'register',
    emailcheck: () => HOST + MEMBER + 'emailcheck',
    emailverify: () => HOST + MEMBER + 'emailverify'
  }
}