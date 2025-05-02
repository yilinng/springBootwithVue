import axios from 'axios'
import { apiBaseUrl } from '../utils/constants'

let token = null

const setToken = (newToken) => {
  token = `Bearer ${newToken}`
}

const getAll = async () => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('check ', config)

  const { data } = await axios.get(`${apiBaseUrl}/reservations/`, config)

  return data
}

const getOne = async (id) => {
  const config = {
    headers: { Authorization: token },
  }
  const { data } = await axios.get(`${apiBaseUrl}/reservations/${id}`, config)

  return data
}

const create = async (newObject) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.post(
    `${apiBaseUrl}/reservations/`,
    newObject,
    config
  )
  return data
}

const deleteReservation = async (id) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.delete(
    `${apiBaseUrl}/reservations/${id}`,
    config
  )
  return data
}

const updateReservation = async (obj) => {
  const config = {
    headers: { Authorization: token },
  }

  console.log('config', config)

  const { data } = await axios.put(
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
