import React, {useState} from 'react'
import './ReloadPanel.css'
import axios from 'axios'
import cardDetailsState from './atoms/cardDetailsState'
import { useRecoilValue, useRecoilState } from 'recoil'
import reloadedCardDetailsState from './atoms/reloadedCardDetailsState'
import AfterReloadPanel from './AfterReloadPanel'

//POST http://localhost:8080/api/transport-card/reload/{id}
const RELOAD_URI = 'http://localhost:8080/api/transport-card/reload/'

function ReloadPanel() {
  const [reloadFields, setReloadFields] = useState({load: null, money: null});
  const cardDetailsValue = useRecoilValue(cardDetailsState);
  const [, setReloadedCardDetails] = useRecoilState(reloadedCardDetailsState);
  const [isReloadDetailsSent, setIsReloadDetailsSent] = useState(false);

  const handleOnChange = (e) => {
      const {name, value} = e.target;
      setReloadFields({...reloadFields, [name]: value})
  }

  const sendReloadDetails = async () => {
    setReloadFields({...reloadFields, load: parseFloat(reloadFields.load).toFixed(2), 
      money: parseFloat(reloadFields.money).toFixed(2)})
    try {
      console.log(JSON.stringify(reloadFields))
      const response = await axios.post(RELOAD_URI.concat(cardDetailsValue.card_id), reloadFields, {
        headers: {
          'content-type': 'application/json'
        }
      });
      setReloadedCardDetails(response.data);
      setIsReloadDetailsSent(!isReloadDetailsSent);
      console.log(response.data);
    } catch (error) {
      console.log(error);
      alert(error.response.data.message);
    }
  }

  return (
    <div className='reload-panel-container'>
        {isReloadDetailsSent ? <AfterReloadPanel/> : 
          <> 
            <h1>Reload your card</h1>
            <input onChange={handleOnChange} value={reloadFields.load} name="load" type="number" placeholder='Amount to load'/>
            <input onChange={handleOnChange} value={reloadFields.money} name="money" type="number" placeholder='Cash value' />
            <button onClick={sendReloadDetails}>OK</button>
          </>
        }
    </div>
  )
}

export default ReloadPanel