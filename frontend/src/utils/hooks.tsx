import {useEffect, useRef} from "react"

//https://overreacted.io/making-setinterval-declarative-with-react-hooks/
export function useInterval(callback: Function, delay: number) {
    const savedCallbackRef = useRef<Function>()

    // Remember the latest callback.
    useEffect(() => {
        savedCallbackRef.current = callback
    }, [callback])

    // Set up the interval.
    useEffect(() => {
        function tick() {
            const curr = savedCallbackRef.current;
            if (curr != null) {
                curr()
            }
        }

        if (delay !== null) {
            let id = setInterval(tick, delay)
            return () => clearInterval(id)
        }
        return;
    }, [delay])
}