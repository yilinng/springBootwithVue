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
const price = ref(0)

watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
    if (state.reservation) {
      title.value = reservationStore.reservation.title
    }
  },
  { deep: true }
)

//https://router.vuejs.org/guide/advanced/navigation-guards.html

function createReservation(e) {
  e.preventDefault()
  if (!title.value) {
    return
  }
  reservationStore.createReservation({
    title: title.value,
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

function initRef() {
  title.value = null
}

function backAction() {
  router.push('/reservations')
}
</script>

<template>
  <div class="bg-gray-50 p-4">
    <h2 class="flex justify-center text-3xl my-4">
      {{ reservation_Id ? 'Edit Reservation' : 'Create Reservation' }}
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
        reservationStore.message: {{ reservationStore.message }}
      </div>
    </form>
  </div>
</template>

<style></style>
