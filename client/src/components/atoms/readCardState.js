import { atom } from 'recoil'

const readCardState = atom({
    key: 'readCardState',
    default: false
})

export default readCardState