import { defineStore } from 'pinia'
import {
  getAll,
  getOne,
  addBook,
  updateBook,
  deleteBook,
  setToken,
} from '../services/book'
import { getTokenFromLocal } from '../utils/localStorage'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export const useBookStore = defineStore('book', () => {
  const books = ref([])
  const book = ref(null)
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

  async function getBook(id) {
    try {
      const data = await getOne(id)
      book.value = data
    } catch (err) {
      handleError(err)
    }
  }

  async function createBook(obj) {
    try {
      const { content } = await addBook(obj)
      books.value = books.value.push(content)

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

  async function editBook(obj) {
    try {
      const { content } = await updateBook(obj)
      books.value = content

      console.log('books', books.value)

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

  async function removeBook(id) {
    try {
      const { content } = await deleteBook(id)
      books.value = books.value.filter((book) => book.id != id)

      console.log('books', content)

      message.value = 'delete book success.'

      initMessage()

      setTimeout(() => {
        router.push('/bookList')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  function checkAuthorize() {
    getToken()
    if (token.value) {
      setToken(token.value)
    }
  }

  function getToken() {
    const data = getTokenFromLocal()
    token.value = data
  }

  function initBook() {
    book.value = null
  }

  function initMessage() {
    setTimeout(() => (message.value = null), 10000)
  }

  function handleError(err) {
    console.log('ohhh nooo, error appear')
    console.log(err)
  }

  return {
    books,
    book,
    message,
    errorMsg,
    getBooks,
    getBook,
    createBook,
    editBook,
    removeBook,
    checkAuthorize,
  }
})
