<script setup>
import { watch, ref } from 'vue'

import { useUserStore } from '../../stores/user'
import { useBookStore } from '../../stores/book'

const userStore = useUserStore()
const bookStore = useBookStore()
const showBooks = ref(false)
const showReservations = ref(false)

bookStore.getBooks()

watch(
  userStore,
  (state) => {
    console.log('watch userstate', state)
  },
  { deep: true }
)

watch(
  bookStore,
  (state) => {
    console.log('watch bookstate', state)
  },
  { deep: true }
)

function clickShowBooks() {
  showBooks.value = !showBooks.value
}

function clickShowReservation() {
  showReservations.value = !showReservations.value
}
</script>
<template>
  <div
    class="user_info flex flex-col my-4 text-xl space-y-2"
    v-if="userStore.user"
  >
    <div>username: {{ userStore.user.username }}</div>
    <div>email: {{ userStore.user.email }}</div>

    <div
      class="bookList"
      v-if="userStore.user.books && userStore.user.books.length"
    >
      <h2>Book List:</h2>
      <button
        type="button"
        @click="clickShowBooks"
        class="border border-blue-700 p-1 bg-blue-200 text-blue-700"
      >
        {{ showBooks ? 'hide' : 'show' }}
      </button>
      <Transition name="slide-fade">
        <div class="bookList" v-if="showBooks">
          <div
            class="book my-2 p-2 border-solid border-4 border-light-blue-500 space-y-3"
            v-for="book of userStore.user.books"
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
          </div>
        </div>
      </Transition>
    </div>

    <div
      class="reservationList"
      v-if="userStore.user.reservations && userStore.user.reservations.length"
    >
      <h2>reservation List:</h2>

      <button
        type="button"
        @click="clickShowReservation"
        class="border border-pink-700 p-1 bg-pink-200 text-pink-700"
      >
        {{ showReservations ? 'hide' : 'show' }}
      </button>

      <Transition name="slide-fade">
        <div class="reservationList" v-if="showReservations">
          <div
            v-for="reservation of userStore.user.reservations"
            class="flex flex-col space-y-2 border p-2"
          >
            <span>{{ reservation.created_date }}</span>

            {{ bookStore.filteredById(reservation.books) }}

            <div
              class="book my-4 p-2 border-solid border-4 border-light-blue-500 space-y-3"
              v-for="book of bookStore.filteredBooks"
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
            </div>

            <span> ${{ reservation.price }}</span>
          </div>
        </div>
      </Transition>
    </div>
  </div>
</template>

<style></style>
