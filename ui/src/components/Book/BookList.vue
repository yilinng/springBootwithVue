<script setup>
import { watch, ref } from 'vue'
import { useBookStore } from '../../stores/book'
import { useUserStore } from '../../stores/user'
import { useReservationStore } from '../../stores/reservation'

const bookStore = useBookStore()
const userStore = useUserStore()
const reservationStore = useReservationStore()

bookStore.getBooks()
bookStore.checkAuthorize()

userStore.checkAuthorize()

const loading = ref(false)

watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
    if (state.books) {
      loading.value = true
    }
  },
  { deep: true }
)

console.log('check cart', reservationStore.cartItems)

function addToCart(obj) {
  reservationStore.addToCart(obj)
}
</script>

<template>
  <div class="flex flex-col" v-if="loading">
    <div
      class="create_book_url bg-blue-500 text-white m-2 p-2 w-36"
      v-if="userStore.haveAuthorize"
    >
      <RouterLink class="navbar-brand text-2xl" to="/bookList/create"
        >create book</RouterLink
      >
    </div>
    <div
      class="book my-4 p-2 border-solid border-4 border-light-blue-500 space-y-3"
      v-for="book of bookStore.books"
    >
      <RouterLink
        class="navbar-brand"
        :to="{ name: 'Book', params: { id: book.id } }"
      >
        <div class="flex flex-col">
          <h2 class="text-2xl">
            {{
              book.title.length > 60
                ? book.title.toString().substring(0, 60) + '...'
                : book.title
            }}
          </h2>
          <p class="font-xl">price: ${{ book.price }}</p>
        </div>
      </RouterLink>

      <button
        class="bg-blue-300 p-2 text-white border-blue-500"
        @click="addToCart(book)"
      >
        Add to cart
      </button>
    </div>
  </div>
</template>

<style></style>
