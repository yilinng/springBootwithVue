import axios from 'axios'
import { apiBaseUrl } from '../utils/constants'
import {
  DeleteResponse,
  Reservation,
  ReservationDefault,
  ReservationDto,
  ReservationsResponse,
} from '../types/types'

let token: string | null = null

const setToken = (newToken: String) => {
  token = `Bearer ${newToken}`
}

const getAll = async () => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('check ', config)

  const { data } = await axios.get<ReservationsResponse>(
    `${apiBaseUrl}/reservations/`,
    config
  )

  return data
}

const getOne = async (id: number) => {
  const config = {
    headers: { Authorization: token },
  }
  const { data } = await axios.get<ReservationDto>(
    `${apiBaseUrl}/reservations/${id}`,
    config
  )

  return data
}

const create = async (newObject: ReservationDefault) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.post<ReservationDto>(
    `${apiBaseUrl}/reservations/`,
    newObject,
    config
  )
  return data
}

const deleteReservation = async (id: number) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.delete<DeleteResponse>(
    `${apiBaseUrl}/reservations/${id}`,
    config
  )
  return data
}

const updateReservation = async (obj: Reservation) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.put<ReservationDto>(
    `${apiBaseUrl}/reservations/${obj.id}`,
    obj,
    config
  )
  return data
}

export default {
  setToken,
  getAll,
  getOne,
  create,
  deleteReservation,
  updateReservation,
}
