package com.example.ferne_000.selectappconnectphp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView listado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listado = (ListView)findViewById(R.id.listView);
        ObtDato();

    }
    //METODO PARA CONECTAR AL SERVIDOR
    public void ObtDato(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url="http://systemxlr.96.lt/Server/log.php";

        /*RequestParams parametros = new RequestParams();
        parametros.put("clave", 18);*/

        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){ //NUMERO DE CODIGO DE HTTP A SI COMO 404 NO FOUND
                    //cargar el listView del los datos de json servidos en la nuve pasando por
                    //parametro los bytes convertidos a cadena del cuerpo de la respuesta
                    cargarLista(objDatosJSON(new String(responseBody)));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    //METODO PARA DIBUJAR EL JSON EN EL LISTview
    public void cargarLista(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        listado.setAdapter(adapter);
    }
    //OBTENER FORMATO DE JSON Y PASARLOS A UNA LISTA ENLAZADA
    public ArrayList<String> objDatosJSON(String response){
        ArrayList<String> lista = new ArrayList<String>(); //CREAR UNA LISTA ENLAZADA
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;
            //ITERAR HASTA QUE TENGA TODOS LOS CAMPOS DE JSON
            //PONER LOS CAMPOS DE FORMATO DE JSON
            for(int i=0;i<jsonArray.length();i++){
                texto=jsonArray.getJSONObject(i).getString("usuario")+ " "+
                      jsonArray.getJSONObject(i).getString("password")+ " ";
                lista.add(texto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
