package ir.rab.ali.mycustomview.View.Selector;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import ir.rab.ali.mycustomview.R;
import ir.rab.ali.mycustomview.View.AutoFiteView.AutofitTextView;


/**
 * Created by ali1 on 10/18/2017.
 */
public class ValueSelector extends RelativeLayout {
    View rootView;
    private int minValue = 1;
    private int maxValue = Integer.MAX_VALUE;
    private AutofitTextView txt_value;
    private Button btn_plus, btn_minus;
    private boolean enable = true;


    public ValueSelector(Context context) {
        super(context);
        init();
    }
    public ValueSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ValueSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void setEnable(boolean b) {
        if (b == enable)
            return;
        if(!b) {
            this.btn_minus.setVisibility(View.GONE);
            this.btn_plus.setVisibility(View.GONE);
            this.txt_value.setBackground(getResources().getDrawable(R.drawable.selector_single_txt));
        }else{
            this.btn_minus.setVisibility(View.VISIBLE);
            this.btn_plus.setVisibility(View.VISIBLE);
            this.txt_value.setBackground(getResources().getDrawable(R.drawable.selector_txt));
        }
        enable = b;
    }
    public boolean getEnable(){
        return this.enable;
    }

    public void init() {
        rootView = inflate(this.getContext(), R.layout.view_value_selector,this);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/samim.ttf");
        txt_value = (AutofitTextView)rootView.findViewById(R.id.txtNumber);
        txt_value.setTypeface(font);
        btn_plus = (Button)rootView.findViewById(R.id.btnPlus);
        btn_minus = (Button)rootView.findViewById(R.id.btnMinus);
        btn_plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enable) setValue(getValue() + 1);
            }
        });

        btn_plus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(enable && !btnPlusPressed) setOnBtnPlusLongClickListener(0);
                return false;
            }
        });

        btn_minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enable) setValue(getValue() - 1);
            }
        });

        btn_minus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(enable && !btnMinusPressed) setOnBtnMinusLongClickListener(0);
                return false;
            }
        });

    }

    private boolean btnPlusPressed = false;
    private void setOnBtnPlusLongClickListener(final int i){
        btnPlusPressed = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!btn_plus.isPressed()) {
                    btnPlusPressed = false;
                    return;
                }
                if(i == 0)
                    setValue(getValue() + 1);
                else if(i < 5)
                    setValue(getValue() + 10);
                else if(i < 10)
                    setValue(getValue() + 100);
                else if(i < 15)
                    setValue(getValue() + 1000);
                else if(i < 20)
                    setValue(getValue() + 10000);
                else
                    setValue(getValue() + 100000);

                setOnBtnPlusLongClickListener(i+1);
            }
        }, 500);
    }


    private boolean btnMinusPressed = false;
    private void setOnBtnMinusLongClickListener(final int i){
        btnMinusPressed = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!btn_minus.isPressed()) {
                    btnMinusPressed = false;
                    return;
                }
                if(i == 0)
                    setValue(getValue() - 1);
                else if(i < 5)
                    setValue(getValue() - 10);
                else if(i < 10)
                    setValue(getValue() - 100);
                else if(i < 15)
                    setValue(getValue() - 1000);
                else if(i < 20)
                    setValue(getValue() - 10000);
                else
                    setValue(getValue() - 100000);

                setOnBtnMinusLongClickListener(i+1);
            }
        }, 500);
    }

    public int getMinValue() {
        return minValue;
    }
    public int getMaxValue() {
        return maxValue;
    }
    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if(getValue() < minValue)
            txt_value.setText(String.valueOf(minValue));
    }
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if(getValue() >maxValue)
            txt_value.setText(String.valueOf(maxValue));
    }
    public int getValue() {
        return Integer.valueOf(txt_value.getText().toString());
    }
    public void setValue(int newValue) {
        int value = newValue;
        if(newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }
        txt_value.setText(String.valueOf(value));
        if (listener != null)
            listener.onValueChanged(value);
    }
    public void setValueWithNoAction(int newValue){
        int value = newValue;
        if(newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }
        txt_value.setText(String.valueOf(value));
    }

    public void setOnValueChangedListener(OnValueChangedListener l){
        this.listener = l;
    }
    public interface OnValueChangedListener{
        void onValueChanged(int newValue);
    }
    private OnValueChangedListener listener;
}
