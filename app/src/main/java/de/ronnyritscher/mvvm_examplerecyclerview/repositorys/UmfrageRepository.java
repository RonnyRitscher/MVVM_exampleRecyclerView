package de.ronnyritscher.mvvm_examplerecyclerview.repositorys;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import de.ronnyritscher.mvvm_examplerecyclerview.models.Umfrage;

/**
 *
 * Schnittstelle Datenbank und ViewModel
 * Zugriff auf die Datenbanken (egal ob über webservice oder Model)
 * definieren der Query-Statements
 */

/**
 * Singleton - pattern
 *
 * Access to Database -> Hier die Querys definieren - OpenConnection to webservice, API, etc...
 */
public class UmfrageRepository {


    //Als Beispiel bnur HardCoded ...

    //1. instance
    private static UmfrageRepository instance;

    //2. ArrayList (dataSetDB)
    private ArrayList<Umfrage> dataSetDB = new ArrayList<>();




    //3. return-method
    public static UmfrageRepository getInstance(){
        if (instance == null){
            instance = new UmfrageRepository();
        }

        return instance;
    }

    //4. Rückgabe des MutableLiveData (inkl. observer-möglichkeit)
    // tue als ob die Daten von deinem WebServer oder OnlineCode kommen
    public MutableLiveData<List<Umfrage>> getListUmfragen(){

        //7. setListUmfrage ausführen - Daten über die DB`s/API`s erhalten
        setListUmfrage();

        //8. MutbleLiveDataObjekt erstellen und  ListDaten von den DB`s hinzufügen
        MutableLiveData<List<Umfrage>> dataReturn = new MutableLiveData<>();
        dataReturn.setValue(dataSetDB);

        //9. return MutbleLiveDataObjekt
        return dataReturn;
    }

    //5. Methode zum einfügen einer Umfrage in die ArrayList (dataSetDB)
    public void setListUmfrage(){


        //TODO - Hier werden die Daten aus der Datenbank geladen - implementieren der AbfrageMethoden
        //6. DUMMYDATEN (können später gelösmcht werden!)
        dataSetDB.add( new Umfrage(
                        "https://www.royal-canin.de/fileadmin/_processed_/d/d/csm_Katze_Katzenerziehung_Wie_545552f538.png",
                        "title Katze",
                        "text Katze"
                ));

        dataSetDB.add( new Umfrage(
                "https://www.welt.de/kmpkt/article190350615/Katze-Lil-Bub-Grund-fuer-ihr-merkwuerdiges-Aussehen-gefunden.html#cs-Social-Media-Katze-Lil-Bub.jpg",
                "title Katze2",
                "text Katze2"
        ));

        dataSetDB.add( new Umfrage(
                "https://www.welt.de/kmpkt/article190350615/Katze-Lil-Bub-Grund-fuer-ihr-merkwuerdiges-Aussehen-gefunden.html#cs-Social-Media-Katze-Lil-Bub.jpg",
                "title Katze3",
                "text Katze3"
        ));
    }



}

