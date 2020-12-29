export function removeElement<T>(arr: Array<T>, idx: number): Array<T> {
  return arr.filter((_, i) => i !== idx)
}

export function getRandomElement<T>(arr: Array<T>): T {
  return arr[generateRandomNumber(0, arr.length - 1)]
}

export function generateRandomNumber(
  inclusiveStart: number,
  inclusiveEnd: number
) {
  return (
    Math.floor(Math.random() * (inclusiveEnd - inclusiveStart + 1)) +
    inclusiveStart
  )
}

type KeyExtractor<T, U> = (arg: T) => U

export function arrayToMap<T, U>(
  arr: Array<T>,
  keyExtractor: KeyExtractor<T, U>
): Map<U, T> {
  const out = new Map<U, T>()
  arr.forEach((value) => out.set(keyExtractor(value), value))
  return out
}
