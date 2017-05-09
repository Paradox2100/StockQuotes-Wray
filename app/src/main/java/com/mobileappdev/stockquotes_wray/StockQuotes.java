package com.mobileappdev.stockquotes_wray;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StockQuotes extends AppCompatActivity {

    private String enteredSymbol;
    private String symbol;
    private String name;
    private String lastTradePrice;
    private String lastTradeTime;
    private String change;
    private String weekRange;

    private Stock stockQuote;

    private EditText entrSymbol;
    private TextView symboltv;
    private TextView nametv;
    private TextView tradePricetv;
    private TextView tradeTimetv;
    private TextView changetv;
    private TextView rangetv;

    private Button getStockQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quotes);

        entrSymbol = (EditText) findViewById(R.id.enterSymbol);
        symboltv = (TextView) findViewById(R.id.Symbol);
        nametv = (TextView) findViewById(R.id.Name);
        tradePricetv = (TextView) findViewById(R.id.LastTradePrice);
        tradeTimetv = (TextView) findViewById(R.id.LastTradeTime);
        changetv = (TextView) findViewById(R.id.Change);
        rangetv = (TextView) findViewById(R.id.Range);
        getStockQuote = (Button) findViewById(R.id.GetQuote);

        //Button activation code
        getStockQuote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                enteredSymbol = entrSymbol.getText().toString();

                if(enteredSymbol.isEmpty() || enteredSymbol.contains(" ")){
                    Log.i("", "Tried to calculate a blank statement");
                    Toast.makeText(StockQuotes.this, "Please enter a value for symbol", Toast.LENGTH_LONG).show();
                }
                else{
                    new LoadStock().execute();
                }


            }
        });
    }

    private class LoadStock extends AsyncTask<String, Void, Stock>{

        @Override
        protected Stock doInBackground(String...params){

            stockQuote = new Stock(enteredSymbol);
            try{
                stockQuote.load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return stockQuote;
        }

        @Override
        protected void onPostExecute(Stock stock){
            super.onPostExecute(stock);

            if(stock == null){
                Log.i("", "Could not Recover stock");
                Toast.makeText(StockQuotes.this, "Could not load Stock symbol", Toast.LENGTH_LONG).show();
            }
            else{
                symbol = stock.getSymbol();
                name = stock.getName();
                lastTradePrice = stock.getLastTradePrice();
                lastTradeTime = stock.getLastTradeTime();
                change = stock.getChange();
                weekRange = stock.getRange();

                entrSymbol.setText(enteredSymbol);
                symboltv.setText(symbol);
                nametv.setText(name);
                tradePricetv.setText(lastTradePrice);
                tradeTimetv.setText(lastTradeTime);
                changetv.setText(change);
                rangetv.setText(weekRange);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("enteredSymbol", enteredSymbol);
        outState.putString("symbol", symbol);
        outState.putString("name", name);
        outState.putString("tradePrice", lastTradePrice);
        outState.putString("tradeTime", lastTradeTime);
        outState.putString("change", change);
        outState.putString("range", weekRange);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        entrSymbol.setText(savedInstanceState.getString("enteredSymbol"));
        symboltv.setText(savedInstanceState.getString("symbol"));
        nametv.setText(savedInstanceState.getString("name"));
        tradePricetv.setText(savedInstanceState.getString("tradePrice"));
        tradeTimetv.setText(savedInstanceState.getString("tradeTime"));
        changetv.setText(savedInstanceState.getString("change"));
        rangetv.setText(savedInstanceState.getString("range"));

    }
}
