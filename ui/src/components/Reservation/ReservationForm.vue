<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { useReservationStore } from '../../stores/reservation'

const userStore = useUserStore()

const reservationStore = useReservationStore()
const router = useRouter()
const route = useRoute()

const reservation_Id = route.params.id

reservationStore.checkAuthorize()

if (reservation_Id) {
  reservationStore.getReservation(reservation_Id)
}

console.log('update reservation', reservationStore.reservation, route.params.id)

const title = ref('')
const loading = ref(false)

watch(
  reservationStore,
  (state) => {
    console.log('watch state', state)
    if (state.cartItems) {
      loading.value = true
    }
    if (state.reservation) {
      title.value = reservationStore.reservation.title
    }
  },
  { deep: true }
)

//https://stackoverflow.com/questions/11686724/adding-numbers-in-for-loop-javascript
function total() {
  return reservationStore.cartItems.reduce(function (accum, val) {
    return accum + Number(val.price)
  }, 0)
}

function removeItem(obj) {
  reservationStore.removeFromCart(obj)
}

//https://router.vuejs.org/guide/advanced/navigation-guards.html

function createReservation(e) {
  e.preventDefault()
  if (!title.value) {
    return
  }
  reservationStore.createReservation({
    title: title.value,
    books: reservationStore.cartItems,
  })
}

function updateReservation(e) {
  e.preventDefault()

  if (!title.value) {
    return
  }
  reservationStore.editReservation({
    id: reservationStore.reservation.id,
    title: title.value,
  })
}

function substringTxt(txt) {
  if (txt.length > 40) {
    return txt.subString(0, 40) + '...'
  }

  return txt
}

function initRef() {
  title.value = null
}

function backAction() {
  router.push('/reservations')
}
</script>

<template>
  <div
    class="bg-gray-50 p-4 table md:block m-auto"
    v-if="reservationStore.cartItems.length"
  >
    <h2 class="flex justify-center text-3xl my-4 md:m-auto">
      {{ reservation_Id ? 'Edit Reservation' : 'Create Reservation' }}
    </h2>
    <form class="w-full md:w-1/2 my-4 mx-auto border">
      <div class="my-4">
        <label for="title">title: </label>
        <input
          type="text"
          v-model="title"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div
        v-for="item in reservationStore.cartItems"
        class="border rounded-lg p-2 bg-white flex flex-row justify-around space-x-2 w-3/4 md:w-full mx-auto"
      >
        <h3 class="p-1 w-full md:w-1/2">
          {{
            item.title.length > 60
              ? item.title.toString().substring(0, 60) + '...'
              : item.title
          }}
        </h3>

        <h4 class="p-1 self-center">${{ item.price }}</h4>

        <button
          type="button"
          class="border-2 border-fuchsia-600 p-1"
          @click="removeItem(item)"
        >
          X
        </button>
      </div>

      <hr />
      <div class="w-1/2 md:w-full my-4 flex justify-center">
        <div>total: ${{ total() }}</div>
      </div>

      <div class="flex justify-center space-x-4">
        <div class="submit_btn">
          <button
            class="bg-pink-400 font-bold hover:bg-pink-600 text-white focus:text-pink-300 focus:outline-none focus:ring-2 focus:ring-pink-500 focus:ring-opacity-50 p-2"
            @click="createReservation"
            v-if="!reservation_Id && userStore.token"
          >
            add
          </button>

          <button
            class="bg-pink-400 font-bold hover:bg-pink-600 text-white focus:text-pink-300 focus:outline-none focus:ring-2 focus:ring-pink-500 focus:ring-opacity-50 p-2"
            @click="updateReservation"
            v-if="reservation_Id && userStore.token"
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
        <p v-if="reservationStore.message" class="text-green-300">
          reservationStore.message: {{ reservationStore.message }}
        </p>
        <p v-if="reservationStore.errorMsg" class="text-red-500">
          reservationStore.errorMsg: {{ reservationStore.errorMsg }}
        </p>
      </div>
    </form>
  </div>

  <div
    class="text-center flex flex-col items-center"
    v-if="!reservationStore.cartItems.length"
  >
    <h2 class="text-xl my-4">no item in cart</h2>
    <RouterLink
      class="navbar-brand text-2xl bg-lime-500 text-white p-2"
      to="/bookList"
      >bookList</RouterLink
    >
  </div>
</template>

<style></style>
