
export function removeElement<T>(arr: Array<T>, idx: number): Array<T> {
    return arr.filter((_, i) => i !== idx);
}