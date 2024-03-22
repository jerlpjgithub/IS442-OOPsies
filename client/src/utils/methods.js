/*
  @params: date string in the format of "YYYY-MM-DDTHH:MM:SSZ"
  return: string in the format of "Day Month Year" eg. "25 Mar 2024"
*/
export const parseToReadableDate = (dateString) => {
  const date = new Date(dateString);

  return date
          .toLocaleDateString('en-US', { day: 'numeric', month: 'long', year: 'numeric' })
          .replace(/(\w+) (\d+), (\d+)/, '$2 $1 $3');
};

/*
  @params: date string in the format of "YYYY-MM-DDTHH:MM:SSZ"
  return: string in the 12H format. eg. "6:30PM"
*/
export const parseToReadableTime = (dateString) => {
  const date = new Date(dateString);

  return date
          .toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
}