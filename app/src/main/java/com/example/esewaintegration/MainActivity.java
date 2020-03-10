package com.example.esewaintegration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_PAYMENT = 1001;
    private KhaltiButton khaltiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        khaltiButton = (KhaltiButton) findViewById(R.id.khalti_button);
        ESewaConfiguration eSewaConfiguration = new ESewaConfiguration()
                .clientId("<Client ID>")
                .secretKey("<Secret Key>")
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        Button buttonBuy = (Button) findViewById(R.id.btnPay);
        initKhalti();
        buttonBuy.setOnClickListener(view -> {
//                ESewaPayment eSewaPayment = new ESewaPayment(“ < Product Price >”,
//        “<Product Name>”, “<productId >”,”<call_back_url >”);
//
            Intent intent = new Intent(MainActivity.this, ESewaPaymentActivity.class);
//                intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration);

//                intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        });
    }

    private void initKhalti() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");

        Config.Builder builder = new Config.Builder(Constant.pub, "Product ID", "Main", 1100L, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                Toast.makeText(MainActivity.this, "" + errorMap.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                Toast.makeText(MainActivity.this, "" + data.toString(), Toast.LENGTH_LONG).show();
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);
                    add(PaymentPreference.EBANKING);
                    add(PaymentPreference.MOBILE_BANKING);
                    add(PaymentPreference.CONNECT_IPS);
                    add(PaymentPreference.SCT);
                }})
                .additionalData(map)
                .productUrl("http://example.com/product")
                .mobile("9800000000");

        Config config = builder.build();
        khaltiButton.setCheckOutConfig(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) return;
                String message = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i(TAG, "Proof of Payment " + message);
                Toast.makeText(this, "SUCCESSFUL PAYMENT", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled By User", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                if (data == null) return;
                String message = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i(TAG, "Proof of Payment " + message);
            }
        }
    }
}
