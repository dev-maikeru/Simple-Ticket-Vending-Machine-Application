import React, { useState } from 'react'
import './QLessTransportPage.css'
import CardTypeButtonContainer from '../components/CardTypeButtonContainer'
import QLessTransportCardPanel from '../components/QLessTransportCardPanel'
import QLessDiscountedCardPanel from '../components/QLessDiscountedCardPanel'
import ReadCardPanel from '../components/ReadCardPanel'
import noDiscountCardState from '../components/atoms/noDiscountCardState'
import discountedCardState from '../components/atoms/discountedCardState'
import readCardState from '../components/atoms/readCardState'
import { useRecoilValue } from 'recoil'

function QLessTransportPage() {
  const noDiscountCardStateValue = useRecoilValue(noDiscountCardState)
  const discountedCardStateValue = useRecoilValue(discountedCardState)
  const isReadCard = useRecoilValue(readCardState)

  return (
    <div className='container'>
        <main>
          {!noDiscountCardStateValue && !discountedCardStateValue ? <CardTypeButtonContainer/> : null}
          {!noDiscountCardStateValue ? null : <QLessTransportCardPanel />}
          {!discountedCardStateValue ? null : <QLessDiscountedCardPanel />}
          {!isReadCard ? null : <ReadCardPanel />}
        </main>
    </div>
  )
}

export default QLessTransportPage