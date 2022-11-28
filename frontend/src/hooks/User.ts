import { Api } from '../services/Api'
import useSWR, { KeyedMutator } from 'swr'
import { ErrorDetails } from '../services/Http'
import { Session } from '../Session'

export interface User {
  session?: Session
  mutate: KeyedMutator<Session>
  isLoading: boolean
  error?: ErrorDetails
}

export function useUser(): User {
  const { data, error, mutate } = useSWR<Session, ErrorDetails>(
    '/logcheck',
    (key) => Api.logcheck()
  )
  return {
    mutate: mutate,
    session: data,
    isLoading: !data && !error,
    error: error,
  }
}
