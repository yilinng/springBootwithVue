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
import { Login, Signup, Role } from '../types/types'

import axios from 'axios'

//https://www.reddit.com/r/vuejs/comments/175bka4/pinia_data_is_lost_when_reloading_the_page/?rdt=57500
export const useUserStore = defineStore(
  'user',
  () => {
    const user = ref(null)
    const users = ref([])
    const user_id = ref(null)
    const token = ref(null)
    const haveAuthorize = ref(false)
    const haveAllAuthorize = ref(false)

    const message = ref(null)
    const errorMsg = ref(null)

    const router = useRouter()

    async function signup(obj: Signup) {
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

    async function login(obj: Login) {
      try {
        const data = await loginAction(obj)

        token.value = data.accessToken

        localStorage.setItem('accessToken', JSON.stringify(data.accessToken))
        localStorage.setItem('refreshToken', JSON.stringify(data.refreshToken))
        localStorage.setItem('userId', JSON.stringify(data.user.id))

        user.value = data.user
        message.value = data.message

        setToken(token.value)
        setAuthorize(data.user.roles)

        console.log('login data', data)

        initMessage()

        setTimeout(() => {
          router.push('/bookList')
        }, 5000)
      } catch (err) {
        handleError(err)
      }
    }

    function setAuthorize(roles: Role[]) {
      console.log('roles', roles)

      for (let i = 0; i < roles.length; i++) {
        if (roles[i].name === 'ADMIN' || roles[i].name === 'EMPLOYEE') {
          haveAuthorize.value = true
        }

        if (roles[i].name === 'ADMIN') {
          haveAllAuthorize.value = true
        }
      }
    }

    async function getCustomer(userid: string) {
      try {
        const { id, username, email, roles, books, reservations } =
          await getUser(parseInt(userid))
        user.value = { id, username, email, roles, books, reservations }

        setAuthorize(roles)

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
      setToken(token.value)

      if (user.value) {
        return
      }

      if (token.value && user_id.value) {
        await getCustomer(user_id.value)
      }
    }

    function initMessage() {
      setTimeout(() => ((message.value = null), (errorMsg.value = null)), 10000)
    }

    function initState() {
      cleanLocal()
      users.value = []
      user.value = null
      user_id.value = null
      token.value = null
      errorMsg.value = null
      message.value = null
      haveAuthorize.value = false
      haveAllAuthorize.value = false
      router.push('/login')
    }

    function handleError(err: any) {
      console.log('ohhh nooo, error appear')
      console.log(err)
      if (axios.isAxiosError(err)) {
        console.log(err.status)
        //console.error(err.response)
        console.log('errorString', err.response.data.message)

        if (
          err.response.data.message === '憑證錯誤' ||
          err.response.data.message === 'Invalid credentials'
        ) {
          errorMsg.value = 'password is incorrect.'
        } else {
          errorMsg.value = err.response.data.message
        }
        // Do something with this error...
      } else {
        console.error(err)
      }
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
  },
  {
    persist: true,
  }
)
