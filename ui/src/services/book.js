import axios from 'axios'
import { apiBaseUrl } from '../utils/constants'

let token = null

export const setToken = (newToken) => {
  token = `Bearer ${newToken}`
  console.log(token)
}

export const getAll = async () => {
  // console.log('apiBaseUrl ', apiBaseUrl)
  const { data } = await axios.get(`${apiBaseUrl}/books/`)

  console.log('data get', data)
  return data
}

export const getOne = async (id) => {
  const { data } = await axios.get(`${apiBaseUrl}/books/${id}`)
  console.log('data get', data)
  return data
}

export const addBook = async (newObject) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.post(`${apiBaseUrl}/books/`, newObject, config)
  return data
}

export const deleteBook = async (id) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)
  const { data } = await axios.delete(`${apiBaseUrl}/books/${id}`, config)
  return data
}

export const updateBook = async (obj) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.put(`${apiBaseUrl}/books/${obj.id}`, obj, config)
  return data
}

/*
export default {
  setToken,
  getAll,
  addBook,
  deleteBook,
  updateBook,
}
*/
