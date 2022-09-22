import React from 'react'

function CardDetails({cardDetailsValue, getTransactionDetails, setReloadMode}) {
  let currencyFormat = Intl.NumberFormat('en-US');
  return (
    <>
        <h1>Card Details</h1>
        {/* {cardDetailsValue.map(details => { */}
        <div>
              <h2>Card ID: {cardDetailsValue.card_id}</h2>
              <h2>Status: {cardDetailsValue.status}</h2>
              <h2>Card Value: ₱{currencyFormat.format(cardDetailsValue.value) + '.00'}</h2>
              <h2>Expiry date: {cardDetailsValue.expiry_date.replace('T', ' ')}</h2>
          </div>
        {/* })} */}
        <button onClick={getTransactionDetails}>TAP TO PAY</button> 
        <button onClick={setReloadMode}>RELOAD YOUR CARD</button>
    </>
  )
}

export default CardDetails