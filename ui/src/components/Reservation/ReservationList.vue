<script setup>
import { watch } from 'vue'

import { useReservationStore } from '../../stores/reservation'

import { useUserStore } from '../../stores/user'

const reservationStore = useReservationStore()
const userStore = useUserStore()

userStore.checkAuthorize()

reservationStore.checkAuthorize()

reservationStore.getReservations()

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
  <div class="flex flex-col items-center">
    <div class="create_reservation_url bg-blue-500 text-white m-2 p-2">
      <RouterLink class="navbar-brand text-2xl" to="/reservations/create"
        >create Reservation</RouterLink
      >
    </div>
    <div
      class="book my-4 p-2 border-solid border-4 border-light-blue-500"
      v-for="reservation of reservationStore.reservations"
    >
      <RouterLink
        class="navbar-brand"
        :to="{ name: 'Reservation', params: { id: reservation.id } }"
      >
        <div class="flex flex-col">
          <h2 class="text-2xl">
            {{
              reservation.title > 60
                ? reservation.title.toString().substring(0, 60) + '...'
                : reservation.title
            }}
          </h2>
          <p class="font-xl">create date: {{ reservation.createDate }}</p>
        </div>
      </RouterLink>
    </div>
  </div>
</template>

<style></style>
