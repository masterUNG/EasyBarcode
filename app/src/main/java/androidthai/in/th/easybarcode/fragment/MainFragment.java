package androidthai.in.th.easybarcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidthai.in.th.easybarcode.R;

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


    }   // Main Method

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
