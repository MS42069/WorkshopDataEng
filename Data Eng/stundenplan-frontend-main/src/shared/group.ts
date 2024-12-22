export function groupBy<T, U extends PropertyKey>(
  items: T[],
  grouper: (item: T) => U
): Record<U, T[]> {
  const res = {} as Record<U, T[]>;
  for (const item of items) {
    const key = grouper(item);
    (res[key] ??= []).push(item);
  }
  return res;
}
