import { defineStore } from 'pinia'

import ReservationService from '../services/reservation'
import { getTokenFromLocal } from '../utils/localStorage'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export const useReservationStore = defineStore('reservation', () => {
  const reservations = ref([])
  const reservation = ref(null)
  const token = ref(null)
  const message = ref(null)
  const errorMsg = ref(null)
  const router = useRouter()

  async function getReservations() {
    try {
      const { content } = await ReservationService.getAll()
      reservations.value = content

      console.log('reservations', reservations.value)
    } catch (err) {
      handleError(err)
    }
  }

  async function getReservation(id) {
    try {
      const data = await ReservationService.getOne(id)
      reservation.value = data
    } catch (err) {
      handleError(err)
    }
  }

  async function createReservation(obj) {
    try {
      const { content } = await ReservationService.create(obj)
      reservations.value = books.value.push(content)

      console.log('reservations', reservations.value)
      message.value = 'create reservation success.'

      initMessage()
      initReservation()

      setTimeout(() => {
        router.push('/reservations')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function editReservation(obj) {
    try {
      const { content } = await ReservationService.updateReservation(obj)
      reservations.value = content

      console.log('reservations', reservations.value)

      message.value = 'update reservation success.'

      initMessage()
      initReservation()

      setTimeout(() => {
        router.push('/reservations')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  async function removeReservation(id) {
    try {
      const { content } = await ReservationService.deleteReservation(id)
      reservations.value = reservations.value.filter(
        (reservation) => reservation.id != id
      )

      console.log('reservations', content)

      message.value = 'delete reservation success.'

      initMessage()

      setTimeout(() => {
        router.push('/reservations')
      }, 5000)
    } catch (err) {
      handleError(err)
    }
  }

  function checkAuthorize() {
    getToken()
    if (token.value) {
      ReservationService.setToken(token.value)
    }
  }

  function getToken() {
    const data = getTokenFromLocal()
    token.value = data
  }

  function initReservation() {
    reservation.value = null
  }

  function initMessage() {
    setTimeout(() => (message.value = null), 10000)
  }

  function handleError(err) {
    console.log('ohhh nooo, error appear')
    console.log(err)
  }

  return {
    reservations,
    reservation,
    message,
    errorMsg,

    getReservations,
    getReservation,
    createReservation,
    editReservation,
    removeReservation,
    checkAuthorize,
  }
})
