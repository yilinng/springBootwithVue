export const useDebounce = (func: any, delay: number) => {
  let timeout = null

  return (...args: any) => {
    if (timeout) clearTimeout(timeout)

    timeout = setTimeout(() => {
      func(...args)
    }, delay)
  }
}
