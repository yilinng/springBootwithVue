<script setup>
import { watch } from 'vue'

import { useRoute, useRouter } from 'vue-router'
import { useReservationStore } from '../../stores/reservation'

import { useUserStore } from '../../stores/user'

const route = useRoute()
const router = useRouter()
const reservationStore = useReservationStore()
const userStore = useUserStore()

userStore.checkAuthorize()

reservationStore.checkAuthorize()

reservationStore.getReservation(route.params.id)

function deleteReservation() {
  if (window.confirm('Do you want to remove this reservation?')) {
    reservationStore.removeReservation(route.params.id)
  }
}

function ItemOwner(user_id) {
  if (userStore.user) {
    return user_id === userStore.user.id
  }
  return false
}

function backAction() {
  router.push('/reservations')
}

/*
watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
  },
  { deep: true }
)
*/
</script>
<template>
  <div
    class="reservation_details space-y-6"
    v-if="reservationStore.reservation"
  >
    <div>reservation id: {{ $route.params.id }}</div>

    <h1 class="text-3xl my-4">
      title: {{ reservationStore.reservation.title }}
    </h1>

    <div class="date flex flex-row space-x-4">
      <p>create date: {{ reservationStore.reservation.createDate }}</p>
      <p>update_date: {{ reservationStore.reservation.updateDate }}</p>
    </div>

    <div
      v-for="item in reservationStore.reservation.books"
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
    </div>

    <hr />

    <div class="text-center">
      <p>total: ${{ reservationStore.reservation.price }}</p>
    </div>

    <div class="back_btn my-4 flex flex-row space-x-4">
      <button
        class="bg-green-600 font-bold hover:bg-green-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-green-600 focus:ring-opacity-50 p-2"
        @click="backAction"
      >
        back
      </button>

      <button
        class="bg-blue-600 font-bold hover:bg-blue-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50 p-2"
        v-if="
          userStore.haveAllAuthorize ||
          ItemOwner(reservationStore.reservation.user_id)
        "
      >
        <RouterLink
          class="navbar-brand"
          :to="{
            name: 'EditReservation',
            params: { id: reservationStore.reservation.id },
          }"
        >
          edit
        </RouterLink>
      </button>

      <button
        class="bg-red-600 font-bold hover:bg-red-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-red-600 focus:ring-opacity-50 p-2"
        v-if="
          userStore.haveAllAuthorize ||
          ItemOwner(reservationStore.reservation.user_id)
        "
        @click="deleteReservation"
      >
        delete
      </button>
    </div>
  </div>
</template>

<style></style>
