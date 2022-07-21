const HOST = 'http://localhost/'

const MEMBER = 'member/'

export default {
  member: {
    emailcheck: () => HOST + MEMBER + 'emailcheck',
    emailverify: () => HOST + MEMBER + 'emailverify'
  }
}