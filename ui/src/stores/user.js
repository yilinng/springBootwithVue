import { defineStore } from 'pinia'
import {
  loginAction,
  signupAction,
  getUser,
  getUsers,
  setToken,
} from '../services/user'

import {
  getTokenFromLocal,
  getUserIdFromLocal,
  cleanLocal,
} from '../utils/localStorage'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const users = ref([])
  const user_id = ref(null)
  const token = ref(null)
  const haveAuthorize = ref(false)
  const haveAllAuthorize = ref(false)

  const message = ref(null)
  const errorMsg = ref(null)

  const router = useRouter()

  async function signup(obj) {
    try {
      const data = await signupAction(obj)
      user.value = data
      console.log('user', user.value)
      message.value = 'sign up success. please login.'

      initMessage()

      setTimeout(() => {
        router.push('/login')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function login(obj) {
    try {
      const data = await loginAction(obj)

      token.value = data.accessToken

      localStorage.setItem('accessToken', JSON.stringify(data.accessToken))
      localStorage.setItem('refreshToken', JSON.stringify(data.refreshToken))
      localStorage.setItem('userId', JSON.stringify(data.userId))

      message.value = data.message

      setToken(token.value)
      //await getCustomer(data.userId)

      console.log('login data', data)

      initMessage()

      router.push('/bookList')
    } catch (err) {
      handleError(err)
    }
  }

  async function getCustomer(userid) {
    try {
      const { id, username, email, roles } = await getUser(parseInt(userid))
      user.value = { id, username, email }

      console.log('roles', roles)

      for (let i = 0; i < roles.length; i++) {
        if (roles[i].name === 'ADMIN' || roles[i].name === 'EMPLOYEE') {
          haveAuthorize.value = true
        }

        if (roles[i].name === 'ADMIN') {
          haveAllAuthorize.value = true
        }
      }

      //haveAuthorize.value
      console.log('getCustomer', user.value)
    } catch (err) {
      handleError(err)
    }
  }

  async function getAllUser() {
    try {
      const { content } = await getUsers()

      users.value = content
      console.log('getAllUser', users.value)
    } catch (err) {
      handleError(err)
    }
  }

  function getToken() {
    const data = getTokenFromLocal()
    token.value = data
    console.log('gettoken', token.value)
  }

  function getUserId() {
    const data = getUserIdFromLocal()
    user_id.value = data
  }

  async function checkAuthorize() {
    getToken()
    getUserId()
    if (token.value && user_id.value) {
      setToken(token.value)
      await getCustomer(user_id.value)
    }
  }

  function initMessage() {
    setTimeout(() => (message.value = null), 10000)
  }

  function initState() {
    cleanLocal()
    users.value = []
    user.value = null
    user_id.value = null
    token.value = null
    haveAuthorize.value = false
  }

  function handleError(err) {
    console.log('ohhh nooo, error appear')
    console.log(err)
  }

  return {
    signup,
    login,
    getAllUser,
    getCustomer,
    checkAuthorize,
    initState,
    haveAuthorize,
    haveAllAuthorize,
    user,
    users,
    message,
    errorMsg,
    token,
  }
})
