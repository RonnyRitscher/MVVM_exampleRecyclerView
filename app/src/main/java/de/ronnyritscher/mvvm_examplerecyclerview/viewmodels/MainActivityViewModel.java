package de.ronnyritscher.mvvm_examplerecyclerview.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import de.ronnyritscher.mvvm_examplerecyclerview.models.Umfrage;
import de.ronnyritscher.mvvm_examplerecyclerview.repositorys.UmfrageRepository;

/**
 * VIEW-MODEL einer Liste von (simplen) Umfragen
 * <p>
 * get() -Methode zum erhalt der Liste
 * <p>
 * MutableLiveData um später auch weitere Elemente einfügen zu können
 * (Immutable) LiveData würde das nicht zulassen
 */

//0. extends ViewModel
public class MainActivityViewModel extends ViewModel {

    //1. MutableLiveData erstellen  - enthält die Liste der Umfragen
    private MutableLiveData<List<Umfrage>> mListUmfragen;

    //5. UmfrageRepositoryObjekt erstellen
    private UmfrageRepository mUmfrageRepository;

    //8. Boolean für die Anzeige der ProgressBar wenn Updating läuft
    private MutableLiveData<Boolean> mIsUpdateing = new MutableLiveData<>();

    //3. init()-Methode erzeugen
    public void init(){
        //Hier können elemente/Objekte hinzugefügt werden?

        //4. Abfrage ob Daten vorhanden sind
        if(mListUmfragen != null){ return; }

        //6. instance der mUmfrageRepository
        mUmfrageRepository = UmfrageRepository.getInstance();

        //7. MutableLiveDataObject * Obserable
        mListUmfragen = mUmfrageRepository.getListUmfragen();

    }


    //2. get()-Methode erzeugen um die Liste außerhalb abrufen zu können
    public LiveData<List<Umfrage>> getUmfrageListe(){
        return mListUmfragen;
    }

    //9. addNewUmfrage
    public void addNewUmfrage(final Umfrage umfrage){
        mIsUpdateing.setValue(true);

        //10. DUMMY/EXAMPLE um das Laden zu visualisieren
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                //11. hole die aktuelle Liste
                List<Umfrage> currentListUmfragen = mListUmfragen.getValue();
                //12. füge die Umfrage der Liste hinzu
                currentListUmfragen.add(umfrage);
                //13. submit neue Umfrage  * neue Updatebare Version der Liste
                mListUmfragen.postValue(currentListUmfragen);
                //14. Ausschalten der Progressbar über mIsUpdateing
                mIsUpdateing.postValue(false);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                //15. DUMMY nur zur Visualisierung, dass etwas geUpdated wird (2sec pause) TODO - löschen wenn fertig
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    //16. get-Methode um LadeStatus über die MainActivity abzufragen
    public LiveData<Boolean> getIsLoading(){
        return mIsUpdateing;
    }
}
