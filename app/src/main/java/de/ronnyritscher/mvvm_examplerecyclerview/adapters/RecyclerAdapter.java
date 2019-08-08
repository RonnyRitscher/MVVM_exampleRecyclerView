package de.ronnyritscher.mvvm_examplerecyclerview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import de.ronnyritscher.mvvm_examplerecyclerview.R;
import de.ronnyritscher.mvvm_examplerecyclerview.models.Umfrage;

/**
 *
 *
 * Was will ich anzeigen lassen ??
 * -> Hier definiere ich die Liste
 * <p>

 * 1. ViewHolder (innerClass) erstellen und
 * 2. ItemMember definieren
 * 3.
 *
 *
 *
 *
 */

//4.  extends RecyclerView.Adapter< erstellte ViewHolder >
//5.   Methoden implementieren
// ! nicht RecyclerView.ViewHolder sondern die selbst erstelle ViewHolder !
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private static final String TAG = "RecyclerAdapter";

    //6. Member
    private List<Umfrage> mListUmfrage = new ArrayList<>();

    private ArrayList<String> mListTitles = new ArrayList<>();
    private ArrayList<String> mListTexts = new ArrayList<>();
    private ArrayList<String> mListImageUrls = new ArrayList<>();
    private Context mContext;

    //7. constructor mit Member
    public RecyclerAdapter(Context mContext, ArrayList<String> mListTitles, ArrayList<String> mListTexts, ArrayList<String> mListImages) {
        this.mListTitles = mListTitles;
        this.mListTexts = mListTexts;
        this.mListImageUrls = mListImages;
        this.mContext = mContext;
    }

    //7. constructor for ViewModel  (mListUmfrage)
    public RecyclerAdapter(Context mContext, List<Umfrage> mListUmfrage) {
        this.mListUmfrage = mListUmfrage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //8. Layout-Inflator einer neuen View übergeben
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem , viewGroup, false);

        //9. eigenes ViewHolder-Objekt erstellen, die erzeugte View übergeben und gebe eigene ViewHolder(holder) zurück
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //for Debug TAG
        Log.d(TAG, "onBindViewHolder: aufruf");


        //DATEN ÜBERGEBEN:


        //4Debug
        Log.d(TAG, "ImageUrl: " + mListUmfrage.get(position).getImageUrl());
        Log.d(TAG, "Title: "+ mListUmfrage.get(position).getTitle());
        Log.d(TAG, "Text: " + mListUmfrage.get(position).getText());

        //10. Erstellen und definieren von Glide und laden des Bildes
        Glide.with(mContext)
                .asBitmap() //Format
                //.load(R.mipmap.ic_launcher) //Was (ListElement)
                .load(mListUmfrage.get(position).getImageUrl()) //Was (welches ListElement)
                .into(viewHolder.mImage);  //Wohin (ImageView)


        //11. Text und Title übergeben
//        viewHolder.mText.setText(mListTexts.get(position));
//        viewHolder.mTitle.setText(mListTitles.get(position));
        viewHolder.mText.setText(mListUmfrage.get(position).getText());
        viewHolder.mTitle.setText(mListUmfrage.get(position).getTitle());

        //12. OnClickListener  (Click Item) über das Item-Layout
        viewHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debug Info
//                Log.d(TAG, "onClick: clicked on: " + mListTitles.get(position) );
//                Toast.makeText(mContext, "clicked on: "+ mListTitles.get(position), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: clicked on: " + mListUmfrage.get(position).getTitle() );
                Toast.makeText(mContext, "clicked on: "+ mListUmfrage.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        //13. Ändern des ItemCount (Wie viele Items sind in der List?), 0 ist keine Darstellung!
        return mListUmfrage.size();
    }

    //1. ViewHolderClass  *merkt/hält sich die Einträge der Liste
    public class ViewHolder extends RecyclerView.ViewHolder {

        //2. Members (der Items) definieren
        CircleImageView mImage;
        TextView mTitle;
        TextView mText;
        RelativeLayout mRelativeLayout;

        //3. ViewHolder erstellen
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //init
            mImage = itemView.findViewById(R.id.item_image);
            mTitle = itemView.findViewById(R.id.item_title);
            mText = itemView.findViewById(R.id.item_text);
            mRelativeLayout = itemView.findViewById(R.id.item_layout);
        }
    }



}
