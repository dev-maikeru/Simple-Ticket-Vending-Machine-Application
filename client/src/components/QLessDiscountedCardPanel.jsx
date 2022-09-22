import React, { useState, useEffect, useRef } from 'react'
import './QLessDiscountedCardPanel.css'
import axios from 'axios';
import cardDetailsState from './atoms/cardDetailsState'
import CardDetails from './CardDetails';
import { useRecoilState } from 'recoil';
import AfterTransactionDetails from './AfterTransactionDetails';
import ReloadPanel from './ReloadPanel';

//POST http://localhost:8080/api/transport-card/{type}
const BUY_CARD_URI = 'http://localhost:8080/api/transport-card';
//GET http://localhost:8080/api/transport-card/transaction/{id}
const TRANSACTION_URI = 'http://localhost:8080/api/transport-card/transaction/';
const COMMUTER_TYPES = ['REGULAR', 'SENIOR', 'PWD'];

function QLessDiscountedCardPanel() {
  const [isSenior, setIsSenior] = useState(false);
  const [isPwd, setIsPwd] = useState(false);
  const [multipleInputs, setMultipleInputs] = useState({first: '', second: '', third: ''});
  const seniorSecondInputRef = useRef(null);
  const seniorThirdInputRef = useRef(null);
  const pwdSecondInputRef = useRef(null);
  const pwdThirdInputRef = useRef(null);
  const [cardDetails, setCardDetails] = useRecoilState(cardDetailsState);
  const [isTap, setIsTap] = useState(false);
  const [transactionDetails, setTransactionDetails] = useState()
  const [isReload, setIsReload] = useState(false)

  const getTransactionDetails = async () => {
    console.log(cardDetails);
    try {
        const response = await axios.get(TRANSACTION_URI.concat(cardDetails.card_id))
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

  useEffect(() => {
    if (multipleInputs.first.length === 2 && isSenior) {
        seniorSecondInputRef.current.focus();
    }
    if (multipleInputs.first.length === 4 && isPwd) {
        pwdSecondInputRef.current.focus();
    }
  }, [multipleInputs.first])

  useEffect(() => {
    if (multipleInputs.second.length === 4 && isSenior) {
        seniorThirdInputRef.current.focus();
    }
    if (multipleInputs.second.length === 4 && isPwd) {
        pwdThirdInputRef.current.focus();
    }
  }, [multipleInputs.second])

  useEffect(() => {
    if (multipleInputs.third.length === 4 && isSenior) {
        const buySeniorCard = async () => {
            const response = await axios.post(BUY_CARD_URI.concat(`/${COMMUTER_TYPES[1]}`), {
                value: 500,
                status: 'Open for entry',
                commuter_type: COMMUTER_TYPES[1]
             })
            setCardDetails(response.data);
            console.log(response.data);
        }
        buySeniorCard();
    }
    if (multipleInputs.third.length === 4 && isPwd) {
        const buyPwdCard = async () => {
            const response = await axios.post(BUY_CARD_URI.concat(`/${COMMUTER_TYPES[2]}`), {
                value: 500,
                status: 'Open for entry',
                commuter_type: COMMUTER_TYPES[2]
             })
            setCardDetails(response.data);
        }
        buyPwdCard();
    }
  }, [multipleInputs.third])
  
  
  

  const setSeniorActive = () => {
     setIsSenior(true);
     setIsPwd(false);
  }

  const setPwdActive = () => {
    setIsSenior(false);
    setIsPwd(true);
 }

 const handleOnChange = (e) => {
    const {name, value} = e.target;
    setMultipleInputs({...multipleInputs, [name]: value})
 }

  return (
    <div className='discounted-type-container'>
        <div className={`${isSenior || isPwd ? 'hidden' : 'button-choices'}`}>
            <h1>I am a</h1>
            <button onClick={setSeniorActive}>Senior Citizen</button>
            <button onClick={setPwdActive}>PWD</button>
        </div>
        {isSenior && Object.keys(cardDetails).length === 0 ? 
            <div className={isSenior && !isPwd ? 'senior-input-container' : 'hidden'}>
                <h1>Enter your Senior Citizen Control Number</h1>
                <div className='senior-inputs'>
                    <div className='first-two-char'><input onChange={handleOnChange} name='first' value={multipleInputs.first} type="text" maxLength={2} minLength={2}/></div>
                    <span>-</span>
                    <div className='second-four-char'><input ref={seniorSecondInputRef} onChange={handleOnChange} name='second' value={multipleInputs.second} type="text" maxLength={4} minLength={4}/></div>
                    <span>-</span>
                    <div className='third-four-char'><input ref={seniorThirdInputRef} onChange={handleOnChange} name='third' value={multipleInputs.third} type="text" maxLength={4} minLength={4}/></div>
                </div>
            </div> : null}

        {isPwd && Object.keys(cardDetails).length === 0 ? 
            <div className={isPwd && !isSenior ? 'pwd-input-container' : 'hidden'}>
                <h1>Enter your PWD ID Number</h1>
                <div className='pwd-inputs'>
                    <div className='first-four-char'><input onChange={handleOnChange} name='first' value={multipleInputs.first} type="text" maxLength={4} minLength={4}/></div>
                    <span>-</span>
                    <div className='second-four-char'><input ref={pwdSecondInputRef} onChange={handleOnChange} name='second' value={multipleInputs.second} type="text" maxLength={4} minLength={4}/></div>
                    <span>-</span>
                    <div className='third-four-char'><input ref={pwdThirdInputRef} onChange={handleOnChange} name='third' value={multipleInputs.third} type="text" maxLength={4} minLength={4}/></div>
                </div>
            </div> : null}
        {isReload ? <ReloadPanel/> : 
         Object.keys(cardDetails).length > 0 && !isTap ? 
        <CardDetails cardDetailsValue={cardDetails} getTransactionDetails={getTransactionDetails}
        setReloadMode={setReloadMode}/> : null}

        {isTap ? <AfterTransactionDetails details={transactionDetails}/> : null}
    </div>
  )
}

export default QLessDiscountedCardPanel