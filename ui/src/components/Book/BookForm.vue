<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { useBookStore } from '../../stores/book'

const userStore = useUserStore()
const bookStore = useBookStore()
const router = useRouter()
const route = useRoute()

const book_Id = route.params.id

bookStore.checkAuthorize()

if (book_Id) {
  bookStore.getBook(book_Id)
}

console.log('update book', bookStore.book, route.params.id)

const title = ref('')
const author = ref('')
const price = ref(0)

watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
    if (state.book) {
      title.value = bookStore.book.title
      author.value = bookStore.book.author
      price.value = bookStore.book.price
    }
  },
  { deep: true }
)

//https://router.vuejs.org/guide/advanced/navigation-guards.html

function createBook(e) {
  e.preventDefault()
  if (!title.value || !author.value || price.value === 0) {
    return
  }
  bookStore.createBook({
    title: title.value,
    author: author.value,
    price: price.value,
  })
}

function updateBook(e) {
  e.preventDefault()

  if (!title.value || !author.value || price.value === 0) {
    return
  }
  bookStore.editBook({
    id: bookStore.book.id,
    title: title.value,
    author: author.value,
    price: price.value,
  })
}

function initRef() {
  title.value = null
  author.value = null
  price.value = 0
}

function backAction() {
  router.push('/bookList')
}
</script>

<template>
  <div class="bg-gray-50 p-4">
    <h2 class="flex justify-center text-3xl my-4">
      {{ book_Id ? 'Edit Book' : 'Create Book' }}
    </h2>
    <form>
      <div class="w-1/2 md:w-full my-4 flex justify-center">
        <label for="title">title: </label>
        <input
          type="text"
          v-model="title"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="w-1/2 md:w-full flex justify-center">
        <label for="author">author: </label>
        <input
          type="text"
          v-model="author"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="w-1/2 md:w-full my-4 flex justify-center">
        <label for="price">price: </label>
        <input
          type="number"
          v-model="price"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="flex justify-center space-x-4">
        <div class="submit_btn">
          <button
            class="bg-pink-400 font-bold hover:bg-pink-600 text-white focus:text-pink-300 focus:outline-none focus:ring-2 focus:ring-pink-500 focus:ring-opacity-50 p-2"
            @click="createBook"
            v-if="!book_Id && userStore.haveAuthorize"
          >
            add
          </button>

          <button
            class="bg-pink-400 font-bold hover:bg-pink-600 text-white focus:text-pink-300 focus:outline-none focus:ring-2 focus:ring-pink-500 focus:ring-opacity-50 p-2"
            @click="updateBook"
            v-if="book_Id && userStore.haveAuthorize"
          >
            update
          </button>
        </div>
        <div class="back_btn">
          <button
            class="bg-green-600 font-bold hover:bg-green-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-green-600 focus:ring-opacity-50 p-2"
            @click="backAction"
          >
            back
          </button>
        </div>
      </div>

      <div class="flex justify-center">
        bookStore.message: {{ bookStore.message }}
      </div>
    </form>
  </div>
</template>

<style></style>
