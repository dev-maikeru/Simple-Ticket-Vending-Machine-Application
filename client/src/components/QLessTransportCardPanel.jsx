import React, { useState } from 'react'
import './QLessTransportCardPanel.css'
import { useRecoilValue } from 'recoil'
import cardDetailsState from './atoms/cardDetailsState'
import axios from 'axios';
import CardDetails from './CardDetails';
import AfterTransactionDetails from './AfterTransactionDetails';
import ReloadPanel from './ReloadPanel';

//GET http://localhost:8080/api/transport-card/transaction/{id}
const TRANSACTION_URI = 'http://localhost:8080/api/transport-card/transaction/';

function QLessTransportCardPanel() {
  const cardDetailsValue = useRecoilValue(cardDetailsState)
  const [isTap, setIsTap] = useState(false);
  const [transactionDetails, setTransactionDetails] = useState()
  const [isReload, setIsReload] = useState(false)

  const getTransactionDetails = async () => {
    try {
      const response = await axios.get(TRANSACTION_URI.concat(cardDetailsValue.card_id))
      console.log(response.data)
      setTransactionDetails(response.data);
      setIsTap(!isTap);
    } catch (error) {
      console.error(error);
      alert(error.response.data.message)
    }
      
  }

  const setReloadMode = () => {
    setIsReload(!isReload);
  }

  return (
    <div className='container-transport-card'>
       <div className='card-details-container'>
       {isReload ? <ReloadPanel/> : 
          !isTap ? 
            <CardDetails cardDetailsValue={cardDetailsValue} 
            getTransactionDetails={getTransactionDetails}
            setReloadMode={setReloadMode}/>
          : <AfterTransactionDetails details={transactionDetails}/>}
       </div>
    </div>
  )
}

export default QLessTransportCardPanel