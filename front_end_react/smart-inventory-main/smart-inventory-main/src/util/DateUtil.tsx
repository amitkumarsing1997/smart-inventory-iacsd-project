export const DateUtil = {

    //2022-09-11T13:33:00Z
    getUTCDate(dt: Date): string {
        return `${dt.getUTCFullYear()}-${String(dt.getUTCMonth()).padStart(2, '0')}-${String(dt.getUTCDate()).padStart(2, '0')}T${String(dt.getUTCHours()).padStart(2,'0')}:${String(dt.getUTCMinutes()).padStart(2, '0')}:${String(dt.getUTCSeconds()).padStart(2,'0')}Z`
    }
}