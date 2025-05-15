import { defineStore } from 'pinia'
import {
  getAll,
  getOne,
  addBook,
  updateBook,
  deleteBook,
  searchBook,
  setToken,
} from '../services/book'
import { getTokenFromLocal } from '../utils/localStorage'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import { Book, BookDefault } from '../types/types'
import axios from 'axios'

export const useBookStore = defineStore('book', () => {
  const books = ref([])
  const book = ref(null)
  const searchBooks = ref([])
  const filteredBooks = ref([])
  const token = ref(null)
  const message = ref(null)
  const errorMsg = ref(null)
  const router = useRouter()

  async function getBooks() {
    try {
      const { content } = await getAll()
      books.value = content

      console.log('books', books.value)
    } catch (err) {
      handleError(err)
    }
  }

  async function getBook(id: number) {
    try {
      const data = await getOne(id)
      book.value = data
    } catch (err) {
      handleError(err)
    }
  }

  async function createBook(obj: BookDefault) {
    try {
      console.log('create book', obj)
      const data = await addBook(obj)
      books.value.push(data)

      console.log('check createbook data', data)

      console.log('books', books.value)
      message.value = 'create book success.'

      initMessage()
      initBook()

      setTimeout(() => {
        router.push('/bookList')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function editBook(obj: Book) {
    try {
      const data = await updateBook(obj)

      console.log('check editbooks', data)

      message.value = 'update book success.'

      initMessage()
      initBook()

      setTimeout(() => {
        router.push('/bookList')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function removeBook(id: number) {
    try {
      const data = await deleteBook(id)
      books.value = books.value.filter((book) => book.id != id)

      console.log('remove books', data)

      message.value = 'delete book success.'

      initMessage()

      setTimeout(() => {
        router.push('/bookList')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function searchByTitle(text: String) {
    if (!text) {
      initBook()
    }

    try {
      const { content } = await searchBook(text)
      searchBooks.value = content
    } catch (err) {
      handleError(err)
    }
  }

  function filteredById(books_id: number[]) {
    filteredBooks.value = books.value.filter((book) =>
      books_id.includes(book.id)
    )
  }

  function checkAuthorize() {
    getToken()
    setToken(token.value)
  }

  function getToken() {
    const data = getTokenFromLocal()
    token.value = data
  }

  function initBook() {
    book.value = null
    searchBooks.value = []
    filteredBooks.value = []
  }

  function initMessage() {
    setTimeout(() => ((message.value = null), (errorMsg.value = null)), 10000)
  }

  function handleError(err: any) {
    console.log('ohhh nooo, error appear')
    console.log(err)
    if (axios.isAxiosError(err)) {
      console.log(err.status)
      //console.error(err.response)
      console.log('errorString', err.response.data.message)

      errorMsg.value = err.response.data.message

      // Do something with this error...
    } else {
      console.error(err)
    }
  }

  return {
    books,
    book,
    searchBooks,
    filteredBooks,
    message,
    errorMsg,
    getBooks,
    getBook,
    createBook,
    editBook,
    removeBook,
    searchByTitle,
    filteredById,
    checkAuthorize,
  }
})
