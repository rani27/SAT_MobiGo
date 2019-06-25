package com.shop.mobigo.listeners


interface OnApiResponseListener {
    fun onCompleted(response: Any)
    fun onError(errorMessage: String)

}