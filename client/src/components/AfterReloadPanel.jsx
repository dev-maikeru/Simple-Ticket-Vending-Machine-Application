import React from 'react'
import './AfterReloadPanel.css'
import { useRecoilValue } from 'recoil'
import reloadedCardDetailsState from './atoms/reloadedCardDetailsState'

function AfterReloadPanel() {
    const reloadedCardDetails = useRecoilValue(reloadedCardDetailsState);
    let currencyFormat = Intl.NumberFormat('en-US');

    const reloadWindow = () => {
        window.location.reload();
    }

  return (
    <div className='after-reload-container'>
        <div className='card-details-container'>
            <h1>Reloading successful!</h1>
            <h1>Change: ₱{currencyFormat.format(reloadedCardDetails.change) + '.00'}</h1>
            <h1>New Balance: ₱{currencyFormat.format(reloadedCardDetails.new_balance) + '.00'}</h1>
            <button onClick={reloadWindow}>OK</button>
        </div>
    </div>
  )
}

export default AfterReloadPanel