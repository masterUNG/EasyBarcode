package androidthai.in.th.easybarcode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import androidthai.in.th.easybarcode.R;
import androidthai.in.th.easybarcode.utility.GetAllData;

/**
 * Created by masterung on 18/12/2017 AD.
 */

public class MainFragment extends Fragment{

    private TextView textView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Initial TextView
        initialTextView();

//        For Special
        forSpecial();

//        Scan Controller
        scanController();

//        CheckBarCode Controller
        checkBarcode();


    }   // Main Method

    private void checkBarcode() {

        Button button = getView().findViewById(R.id.btnCheck);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String barcodeString = (String) textView.getText();

//                Check Have Barcode
                if (barcodeString.equals(getString(R.string.barcode))) {
//                    No Barcode
                    Toast.makeText(getActivity(), "Please Add Barcode",
                            Toast.LENGTH_SHORT).show();
                } else {
//                    Have Barcode
                    haveBarcode(barcodeString);

                }


            }
        });

    }

    private void haveBarcode(String barcodeString) {

        try {

            String tag = "18DecV1";
            String urlJSON = "http://androidthai.in.th/pi/getAllProductMaster.php";
            boolean statusAboolean = true; // true ==> barcode false
            String nameString = null;
            String detailString = null;

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(urlJSON);

            String jsonString = getAllData.get();
            Log.d(tag, "JSON ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (barcodeString.equals(jsonObject.getString("Barcode"))) {

                    nameString = jsonObject.getString("NamaProduce");
                    detailString = jsonObject.getString("Detail");

                    Log.d(tag, "Name ==> " + nameString);
                    Log.d(tag, "Detail ==> " + detailString);

                    statusAboolean = false;

                }

            }   // for

            if (statusAboolean) {

                myAlert("No This Barcode in my Database");

            } else {

                myAlert(nameString + "\n" + detailString);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void myAlert(String messageString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("My Alert");
        builder.setMessage(messageString);
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            String resultString = data.getStringExtra("SCAN_RESULT");
            textView.setText(resultString);

        } else {
            Toast.makeText(getActivity(), "Un Success",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void scanController() {

        Button button = getView().findViewById(R.id.btnScan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myString = "com.google.zxing.client.android.SCAN";
                String modeString = "BAR_CODE_MODE";

                try {

                    Intent intent = new Intent(myString);
                    intent.putExtra("SCAN_MODE", modeString);
                    startActivityForResult(intent, 1);


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Please Install QR Barcode",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void initialTextView() {
        textView = getView().findViewById(R.id.txtDisplay);
    }

    private void forSpecial() {

        EditText editText = getView().findViewById(R.id.edtSpecial);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textView.setText(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }
}   // Main Class
