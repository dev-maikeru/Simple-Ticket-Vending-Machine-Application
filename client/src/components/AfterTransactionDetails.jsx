import React from 'react'

function AfterTransactionDetails({details}) {
  let currencyFormat = Intl.NumberFormat('en-US');

  const reloadWindow = () => {
    window.location.reload();
  }
  
  return (
    <>
        <div>
            <h1>Transaction Successful!</h1>
            <h2>Remaining value: ₱{currencyFormat.format(details.value) + '.00'}</h2>
            <h2>Expiry date: {details.expiry_date.replace('T', ' ')}</h2>
        </div>
        <button onClick={reloadWindow}>OK</button>
    </>
  )
}

export default AfterTransactionDetails