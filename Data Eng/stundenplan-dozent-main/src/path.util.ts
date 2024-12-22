export const createTimetablePath = (path: string) => {
  const currentPath = window.location.pathname;
  const sub = currentPath.indexOf("timetable/") + "timetable/".length;
  return `/timetable/${currentPath.substring(
    sub,
    currentPath.indexOf(`/`, sub)
  )}/${path}`;
};

export const getTimetableId = () => {
  const currentPath = window.location.pathname;
  const sub = currentPath.indexOf("timetable/") + "timetable/".length;
  return `${currentPath.substring(sub, currentPath.indexOf(`/`, sub))}`;
};
