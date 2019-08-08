package de.ronnyritscher.mvvm_examplerecyclerview;

/**
 * MVVM  ->  Model-View-ViewModel
 *
 * ACTIVITY - Anzeige der Daten in der App
 * durch MVVM ist der Code cleaner und die LifeCircle-Elemente aufgeräumter
 * über einen Observer kann eine änderung der Daten sofort angezeigt werden
 *
 * VIEWMODEL - das VM erhält die Daten nur von der REPOSIORY und übergibt sie der Activity
 * dem VM ist es somit egal woher die Daten stammen (ob vom webservice oder einer SQLite)
 *
 * REPOSITORY - Datenbank-Abfragen und weitergabe an das VIEWMODEL
 * Hier werden die DB-Abfragen umgesetzt und der VM übergeben
 *
 *
 *
 The UI components are kept away from the business logic
 The business logic is kept away from the database operations
 It's easy to read (because everything has specific places to live)
 And if done correctly, you have a lot less to worry about when it comes to lifecycle events (ex: screen rotations)

 *CODING:
 *  * 1. Models erstellen: Umfrage
 *  * 2. ItemLayout: Layout für die ListItems erstellen
 *  * 3. RecyclerViewAdapter erstellen: RecyclerAdapter.java
 *  * 4. MainActivity anpassen (initRecyclerView() )
 *  5. ViewModel: erstellen der MainActicvityViewModel
 *
 */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.ronnyritscher.mvvm_examplerecyclerview.adapters.RecyclerAdapter;
import de.ronnyritscher.mvvm_examplerecyclerview.models.Umfrage;
import de.ronnyritscher.mvvm_examplerecyclerview.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //1. Member / var
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;

    private ArrayList<String> mListTitles = new ArrayList<>();
    private ArrayList<String> mListTexts = new ArrayList<>();
    private ArrayList<String> mListImages = new ArrayList<>();

    //7. ViewModelObjekt
    private MainActivityViewModel mMainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for Debug
        Log.d(TAG, "onCreate: started.");

        //Init
        //3. RecyclerView initialisieren
        mRecyclerView = findViewById(R.id.main_recyclerview);
        mFab = findViewById(R.id.main_fab);
        mProgressBar = findViewById(R.id.main_progressBar);

        //8. initialisieren des ViewModels
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //10. erhalten der Daten vom Repository
        mMainActivityViewModel.init();

        //9. Observe-Method -> überwachen wenn sich die Daten ändern
        mMainActivityViewModel.getUmfrageListe().observe(this, new Observer<List<Umfrage>>() {
            @Override
            public void onChanged(@Nullable List<Umfrage> umfrages) {
                //wenn sich Daten ändern -> Adapter benachrichtigen
                mAdapter.notifyDataSetChanged();

            }
        });

        //11. Observer-Methode -> überwachen wenn sich der UpdateStatus ändernt
        mMainActivityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    //12. wenn geladen wird:
                    showProgressBar();
                }else{
                    //12. wenn nicht mehr geladen wird:
                    hideProgressBar();
                    //13.Scrollen zu dem letzten Item / ebend hinzugefügtem item
                    mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getUmfrageListe().getValue().size() -1 );
                }
            }
        });

        // FAB - Neue Umfrage erstellen
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked new Umfrage");
                Toast.makeText(MainActivity.this, "clicked new Umfrage", Toast.LENGTH_SHORT).show();

                //Erstellen einer neuen Umfrage TODO - hier bitte die CreateUmfrage verwenden/einbauen um UserInput zu nutzen
                Umfrage newUmfrage = new Umfrage("imageUrl" , "TmageTitle" , "ImageText");

                //Übergebe neue Umfrage an ViewModel
                mMainActivityViewModel.addNewUmfrage(newUmfrage);
            }
        });

        //start initRecyclerView
        initRecyclerView();

        //start init ImageBitmap
        //initImageBitmap();

    }

//    private void initImageBitmap(){
//        //for Debug
//        Log.d(TAG, "initImageBitmap: preparing bitmap");
//
//        //2. Hier können die Elemente geladen werden . idb werden hier SurveyEinträge erstellt
//        mListImages.add("kategorie1");
//        mListTitles.add("BeispieTitle 1 ");
//        mListTexts.add("Beispiel 1 ");
//
//        mListImages.add("kategorie1");
//        mListTitles.add("BeispieTitle 2 ");
//        mListTexts.add("Beispiel 2 ");
//
//        mListImages.add("kategorie2");
//        mListTitles.add("BeispieTitle 4 ");
//        mListTexts.add("Beispiel 4 ");
//
//        mListImages.add("kategorie2");
//        mListTitles.add("BeispieTitle 3 ");
//        mListTexts.add("Beispiel 3 ");
//
//        //start initRecyclerView in der initImageBitmap-Methode
//        //* hier haben wir bereits die Daten für das bestücken der RecyclerView
//
//
//    }


    private void initRecyclerView() {
        //4. eigenes RecyclerAdapter-Objekt erstellen - hier übergeben wir den Inhalt
        //RecyclerAdapter mAdapter = new RecyclerAdapter(this, mListTitles, mListTexts, mListImages);

        //10 ändern des Adapters und Angabe des ViewModels (mMainActivityViewModels)   [->commentOut old Adapter]
        mAdapter = new RecyclerAdapter(this, mMainActivityViewModel.getUmfrageListe().getValue());

        //5. Adapter übergeben
        mRecyclerView.setAdapter(mAdapter);

        //6. Layout-Manager - hier übergeben wir das Layout **eigener Layoutmanager vorteilhaft...
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));





        //mAdapter = new RecyclerAdapter(this , new ArrayList<Umfrage>());

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
