export const convertFirestoreTimestampToDate = (timestamp) => {
    const date = new Date(timestamp.seconds * 1000 + timestamp.nanoseconds / 1e6);
    return date.toISOString().slice(2, 19).replace("T", " ");
};