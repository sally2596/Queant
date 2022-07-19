const HOST = 'http://localhost:8080/'

const MEMBER = 'member/'

export default {
  member: {
    emailcheck: () => HOST + MEMBER + 'emailcheck',
    emailverify: () => HOST + MEMBER + 'emailverify'
  }
}