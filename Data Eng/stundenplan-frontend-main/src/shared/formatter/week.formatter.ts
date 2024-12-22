/**
 * Formats the given array as intervals where possible.
 * e.g. `1..12` `1..10, 12`
 */
export function formatWeeks(weeks: number[]) {
  let out = "";

  let start = weeks[0];
  let end = weeks[0];
  for (const week of weeks.slice(1)) {
    // Continue range
    if (week === end + 1) {
      end = week;
      // End single range
    } else if (start === end) {
      out += `${start}, `;
      start = week;
      end = week;
      // End multiple range
    } else {
      out += `${start}..${end}, `;
      start = week;
      end = week;
    }
  }

  if (start !== weeks.at(-1)) {
    out += `${start}..${weeks.at(-1)}, `;
  } else {
    out += `${weeks.at(-1)}, `;
  }

  return out.slice(0, -2);
}
