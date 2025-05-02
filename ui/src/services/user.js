import axios from 'axios'
import { apiBaseUrl } from '../utils/constants'
//import { Customer, Login, AuthResponse, Signup } from '../types/types'

let token = null

//https://gist.github.com/JaysonChiang/fa704307bacffe0f17d51acf6b1292fc
//https://stackoverflow.com/questions/69766132/type-axiosresponseany-is-missing-the-following-properties-from-type-countri
export const setToken = (newToken) => {
  token = `Bearer ${newToken}`
}
export const getUsers = async () => {
  const config = {
    headers: { Authorization: token },
  }

  const { data } = await axios.get(`${apiBaseUrl}/users`, config)

  return data
}

export const getUser = async (id) => {
  const config = {
    headers: { Authorization: token },
  }

  const { data } = await axios.get(`${apiBaseUrl}/users/${id}`, config)

  return data
}

export const loginAction = async (object) => {
  const { data } = await axios.post(`${apiBaseUrl}/login`, object)

  return data
}

export const signupAction = async (object) => {
  const { data } = await axios.post(`${apiBaseUrl}/signup`, object)

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
