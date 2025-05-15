import { defineStore } from 'pinia'

import ReservationService from '../services/reservation'
import { getTokenFromLocal } from '../utils/localStorage'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Reservation, ReservationDefault } from '../types/types'
import { Book } from '../types/types'
import axios from 'axios'

export const useReservationStore = defineStore(
  'reservation',
  () => {
    const reservations = ref([])
    const reservation = ref(null)
    const cartItems = ref([])
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

    async function getReservation(id: number) {
      try {
        const data = await ReservationService.getOne(id)
        reservation.value = data
      } catch (err) {
        handleError(err)
      }
    }

    async function createReservation(obj: ReservationDefault) {
      try {
        const data = await ReservationService.create(obj)
        reservations.value.push(data)

        console.log('reservations data', data)

        console.log('reservations value', reservations.value)
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

    async function editReservation(obj: Reservation) {
      try {
        const data = await ReservationService.updateReservation(obj)
        // reservations.value = content

        console.log('reservations data', data)
        console.log('reservations values', reservations.value)

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

    async function removeReservation(id: number) {
      try {
        const data = await ReservationService.deleteReservation(id)
        reservations.value = reservations.value.filter(
          (reservation) => reservation.id != id
        )

        console.log('reservations', data)

        message.value = 'delete reservation success.'

        initMessage()

        setTimeout(() => {
          router.push('/reservations')
        }, 5000)
      } catch (err) {
        handleError(err)
      }
    }

    //https://stackoverflow.com/questions/237104/how-do-i-check-if-an-array-includes-a-value-in-javascript
    function addToCart(obj: Book) {
      console.log('init addTocart', obj)

      if (cartItems.value.length === 0) {
        cartItems.value.push(obj)
      }

      if (
        cartItems.value.length &&
        cartItems.value.some((item) => item.id !== obj.id)
      ) {
        cartItems.value.push(obj)
        console.log('addTocart no include', obj)
      }

      console.log('addTocart ', cartItems.value)
    }

    function removeFromCart(obj: Book) {
      cartItems.value = cartItems.value.filter((item) => item.id !== obj.id)
      console.log('removefromcart ', cartItems.value)
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
      cartItems.value = []
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
      reservations,
      reservation,
      message,
      errorMsg,
      cartItems,
      getReservations,
      getReservation,
      createReservation,
      editReservation,
      removeReservation,
      checkAuthorize,
      addToCart,
      removeFromCart,
    }
  },
  {
    persist: true,
  }
)
