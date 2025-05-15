export const getTokenFromLocal = () => {
  const token = localStorage.getItem('accessToken')
  if (token) {
    console.log('getTokenFromLocal', token)
    return JSON.parse(token)
  }
  return null
}

export const getUserIdFromLocal = () => {
  const userId = localStorage.getItem('userId')
  if (userId) {
    return JSON.parse(userId)
  }
  return null
}

export const cleanLocal = () => {
  localStorage.clear()
}
