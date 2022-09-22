import React, { useEffect, useState } from 'react'
import './CardTypeButtonContainer.css'
import { useRecoilValue, useRecoilState, atom } from 'recoil'
import axios from 'axios'
import noDiscountCardState from './atoms/noDiscountCardState'
import discountedCardState from './atoms/discountedCardState'
import cardDetailsState from './atoms/cardDetailsState'
import readCardState from './atoms/readCardState'
import ExpiredPanel from './ExpiredPanel'

//POST http://localhost:8080/api/transport-card/{type}
const BUY_CARD_URI = 'http://localhost:8080/api/transport-card';
//GET http://localhost:8080/api/transport-card/{id}
const READ_CARD_URI = 'http://localhost:8080/api/transport-card/'
const COMMUTER_TYPES = ['REGULAR', 'SENIOR', 'PWD'];

function CardTypeButtonContainer() {
  const [isNoDiscountBought, setIsNoDiscountBought] = useRecoilState(noDiscountCardState);
  const [isDiscountedCardClicked, setIsDiscountedCardClicked] = useRecoilState(discountedCardState)
  const [cardDetails, setCardDetails] = useRecoilState(cardDetailsState);
  const [cardId, setCardId] = useState('')
  const [isReadCard, setIsReadCard] = useRecoilState(readCardState)
  const [isExpired, setIsExpired] = useState(false)

  useEffect(() => {
    if (cardDetails) {
      console.log(cardDetails);
    }
    
  }, [cardDetails])

  const readTransportCard = async () => {
    try {
      const response = await axios.get(READ_CARD_URI.concat(cardId));
      console.log(response.data);
      setIsReadCard(!isReadCard);
      setCardDetails(response.data);
    } catch (error) {
      if (error.response.status === 417) {
        setIsExpired(!isExpired);
      } else {
        alert("Transport card data not found.")
      }
    }
  }

  const handleOnChange = (e) => {
    setCardId(e.target.value)
  }
  

  const buyNoDiscountCard = async () => {
    const response = await axios.post(BUY_CARD_URI.concat(`/${COMMUTER_TYPES[0]}`), {
       value: 100,
       status: 'Open for entry',
       commuter_type: COMMUTER_TYPES[0]
    })
    setCardDetails(response.data);
    console.log(response.data);
    setIsNoDiscountBought(!isNoDiscountBought)
  }

  return (
    <div className={`${isDiscountedCardClicked || isDiscountedCardClicked || isReadCard ? 'hidden':'card-type-container'}`}>
      {isExpired ? <ExpiredPanel/> 
      : 
      <>
        <div className='header-container'>
            <h1>Q-LESS Transport</h1>
        </div>
        <div className='card-id-input'>
          <input onChange={handleOnChange} value={cardId} type="number" placeholder='Enter your Card ID to read your card'/>
          <button onClick={readTransportCard}>OK</button>
        </div>
        <div>OR</div>
        <div className='btn-container'>
            <button onClick={buyNoDiscountCard}>Buy Q-LESS Transport Card</button>
            <button onClick={() => setIsDiscountedCardClicked(!isDiscountedCardClicked)}>Buy Q-LESS Discounted Transport Card</button>
        </div>
      </>
      }
    </div>
  )
}

export default CardTypeButtonContainer