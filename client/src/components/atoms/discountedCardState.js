import { atom } from 'recoil'

const discountedCardState = atom({
    key: 'discountedCardState',
    default: false
})

export default discountedCardState