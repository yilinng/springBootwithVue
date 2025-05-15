import axios from 'axios'

import { apiBaseUrl } from '../utils/constants'
import {
  JWTAuthResponse,
  User,
  Login,
  Signup,
  UserResponse,
} from '../types/types'

let token: string | null = null

//https://gist.github.com/JaysonChiang/fa704307bacffe0f17d51acf6b1292fc
//https://stackoverflow.com/questions/69766132/type-axiosresponseany-is-missing-the-following-properties-from-type-countri
export const setToken = (newToken: String) => {
  token = `Bearer ${newToken}`
}
export const getUsers = async () => {
  const config = {
    headers: { Authorization: token },
  }

  const { data } = await axios.get<UserResponse>(`${apiBaseUrl}/users`, config)

  return data
}

export const getUser = async (id: number) => {
  const config = {
    headers: { Authorization: token },
  }

  const { data } = await axios.get<User>(`${apiBaseUrl}/users/${id}`, config)

  return data
}

export const loginAction = async (object: Login) => {
  const { data } = await axios.post<JWTAuthResponse>(
    `${apiBaseUrl}/login`,
    object
  )

  return data
}

export const signupAction = async (object: Signup) => {
  const { data } = await axios.post<User>(`${apiBaseUrl}/signup`, object)

  return data
}

/*
export default {
  setToken,
  getUser,
  loginAction,
  signupAction,
}
*/
