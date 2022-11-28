import { useEffect, useRef } from "react"

//https://overreacted.io/making-setinterval-declarative-with-react-hooks/
export function useInterval(callback: Function, delayMs: number) {
  const savedCallbackRef = useRef<Function>();

  // Remember the latest callback.
  useEffect(() => {
    savedCallbackRef.current = callback;
  }, [callback]);

  // Set up the interval.
  useEffect(() => {
    function tick() {
      const curr = savedCallbackRef.current;
      if (curr != null) {
        curr();
      }
    }

    if (delayMs !== null) {
      let id = setInterval(tick, delayMs);
      return () => clearInterval(id);
    }
    return;
  }, [delayMs]);
}
