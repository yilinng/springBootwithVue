import axios from 'axios'
import { Book } from '../types/types'
import { apiBaseUrl } from '../utils/constants'
import {
  BookDefault,
  BookDto,
  BooksResponse,
  DeleteResponse,
} from '../types/types'

let token: string | null = null

export const setToken = (newToken: String) => {
  token = `Bearer ${newToken}`
  console.log(token)
}

export const getAll = async () => {
  // console.log('apiBaseUrl ', apiBaseUrl)
  const { data } = await axios.get<BooksResponse>(`${apiBaseUrl}/books/`)

  console.log('data get', data)
  return data
}

export const getOne = async (id: number) => {
  const { data } = await axios.get<BookDto>(`${apiBaseUrl}/books/${id}`)
  console.log('data get', data)
  return data
}

export const addBook = async (newObject: BookDefault) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)
  console.log('addBook', newObject)

  const { data } = await axios.post<BookDto>(
    `${apiBaseUrl}/books/`,
    newObject,
    config
  )
  return data
}

export const deleteBook = async (id: number) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)
  const { data } = await axios.delete<DeleteResponse>(
    `${apiBaseUrl}/books/${id}`,
    config
  )
  return data
}

export const updateBook = async (obj: Book) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.put<BookDto>(
    `${apiBaseUrl}/books/${obj.id}`,
    obj,
    config
  )
  return data
}

export const searchBook = async (text: String) => {
  console.log('searchBook', text)

  const { data } = await axios.get<BooksResponse>(
    `${apiBaseUrl}/books/search/?txt=${text}`
  )
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
