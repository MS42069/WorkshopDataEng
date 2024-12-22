function isArithmetic(value: unknown): value is string | number {
  return typeof value === "number" || typeof value === "string";
}

export const sortAsc = (a: unknown, b: unknown): number => {
  if (isArithmetic(a) && isArithmetic(b)) {
    if (a < b) {
      return 1;
    }

    if (a > b) {
      return -1;
    }
  }
  return 0;
};

export const sortDesc = (a: unknown, b: unknown): number => {
  if (isArithmetic(a) && isArithmetic(b)) {
    if (a > b) {
      return 1;
    }
    if (a < b) {
      return -1;
    }
  }
  return 0;
};
