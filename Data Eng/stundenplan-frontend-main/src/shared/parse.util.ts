/**
 * @brief parses int and checks for NaN.
 *
 * @param value new value as string
 * @param prevValue previous value defaulting to 0, used when value is NaN
 *
 * @return parsed number or previous value
 */
export const checkedParseInt = (value: string, prevValue = 0): number => {
  const valuesAsInt = +value;
  if (Number.isNaN(valuesAsInt)) {
    return prevValue;
  }
  return valuesAsInt;
};
