package com.example.testmidtransapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testmidtransapp.BuildConfig.MERCHANT_BASE_URL
import com.example.testmidtransapp.BuildConfig.MERCHANT_CLIENT_KEY
import com.google.android.material.button.MaterialButton
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.PaymentMethod
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.*
import com.midtrans.sdk.corekit.models.snap.CreditCard
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import java.util.*

class MidtransActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_midtrans)
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.READ_PHONE_STATE), 101);
//        }
        SdkUIFlowBuilder.init()
                .setClientKey(MERCHANT_CLIENT_KEY)
                .setContext(this)
                .setTransactionFinishedCallback(TransactionFinishedCallback {
                    it.
                })
                .setMerchantBaseUrl(MERCHANT_BASE_URL)
                .enableLog(true)
                .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
                .setLanguage("id")
                .buildSDK()

        val etQuantity = findViewById<EditText>(R.id.et_quantity)
        val etPrice = findViewById<EditText>(R.id.et_price)
        val btnPay = findViewById<MaterialButton>(R.id.btn_pay)
        btnPay.setOnClickListener {
            val quantity = etQuantity.text.toString().toInt()
            val price = etPrice.text.toString().toDouble()
            val totalAmount = quantity * price
            val transactionRequest = TransactionRequest("Andarta-Store-"+System.currentTimeMillis().toShort()+"", totalAmount)
            val randomID = UUID.randomUUID().toString()
            val itemDetail = ItemDetails(randomID, price, quantity, "Motor")
            val listItem = ArrayList<ItemDetails>()
            listItem.add(itemDetail)

//            val creditCard = CreditCard()
//            creditCard.isSaveCard = false
//            creditCard.bank = BankType.MANDIRI
//            transactionRequest.creditCard = creditCard
            uiKitDetail(transactionRequest)
            transactionRequest.itemDetails = listItem

            MidtransSDK.getInstance().transactionRequest = transactionRequest
//            MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER_MANDIRI)
            MidtransSDK.getInstance().startPaymentUiFlow(this)
        }
    }

    private fun uiKitDetail(transactionRequest: TransactionRequest) {
        val customerDetail = CustomerDetails()
        customerDetail.customerIdentifier = "Alif Andarta"
        customerDetail.phone = "081217915595"
        customerDetail.firstName = "Alif"
        customerDetail.lastName = "Andarta"
        customerDetail.email = "aliefazuka123@gmail.com"
        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Perumdin PTKL F-2 Leces"
        shippingAddress.city = "Probolinggo"
        shippingAddress.postalCode = "67273"
        customerDetail.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.address = "Perumdin PTKL F-2 Leces"
        billingAddress.city = "Probolinggo"
        billingAddress.postalCode = "67273"
        customerDetail.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetail
    }
}