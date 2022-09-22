import React from 'react'

function ExpiredPanel() {

    const reloadWindow = () => {
        window.location.reload();
    }
    return (
        <div>
            <h1>Your transport card has been expired.</h1>
            <h1>Reload your card to re-activate.</h1>
            <button onClick={reloadWindow}>OK</button>
        </div>
    )
}

export default ExpiredPanel