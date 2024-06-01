package com.gps.camera.timestamp.photo.geotag.map.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.DataBase.DatabaseHelper;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.NotesModal;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.NoteAdapterGps;
import com.gps.camera.timestamp.photo.geotag.map.data.database.DateTimeDB;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;

public class BottomSheetFragmentNote extends BottomSheetDialogFragment {
    public static final String TAG = "Note_bottom_sheetfragment";
    ArrayList<NotesModal> Note_list = new ArrayList<>();
    TextView btn_add;
    ImageView btn_clearnote;
    ImageView btn_cleartitle;
    Context context;
    DateTimeDB dateTimeDB;
    EditText mEd_notes;
    EditText mEd_title;
    C1281SP mSP;
    NoteAdapterGps noteAdapterGps;
    Onselectitem onselectitem;
    RecyclerView rv_notes;

    public interface Onselectitem {
        void Onselect();
    }

    public void setOnselectitem(Onselectitem onselectitem2) {
        this.onselectitem = onselectitem2;
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("RestrictedApi")
    @Override 
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.car_note_bottom_sheet_fragment, null);
        this.btn_add = (TextView) inflate.findViewById(R.id.btn_add);
        this.btn_clearnote = (ImageView) inflate.findViewById(R.id.btn_clearnote);
        this.btn_cleartitle = (ImageView) inflate.findViewById(R.id.btn_cleartitle);
        this.mEd_notes = (EditText) inflate.findViewById(R.id.mEd_notes);
        this.mEd_title = (EditText) inflate.findViewById(R.id.mEd_title);
        this.rv_notes = (RecyclerView) inflate.findViewById(R.id.rv_notes);
        this.context = inflate.getContext();
        dialog.setContentView(inflate);
        this.dateTimeDB = new DateTimeDB(this.context);
        this.mSP = new C1281SP(getContext());
        listener();
        setRecyclerview();
    }

    private void setRecyclerview() {
        this.rv_notes.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true));
        NoteAdapterGps noteAdapterGps2 = new NoteAdapterGps(getContext(), this.Note_list, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnClick_(int i, View view) {
                NotesModal notesModal = BottomSheetFragmentNote.this.Note_list.get(i);
                BottomSheetFragmentNote.this.mSP.setString(BottomSheetFragmentNote.this.getContext(), DatabaseHelper.TABLE_NOTE, notesModal.getTiitle() + "" + notesModal.getNotes());
                BottomSheetFragmentNote.this.onselectitem.Onselect();
                BottomSheetFragmentNote.this.dismiss();
            }

            @Override 
            public void OnLongClick_(int i, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BottomSheetFragmentNote.this.getActivity());
                builder.setTitle(BottomSheetFragmentNote.this.getString(R.string.delete));
                builder.setMessage(BottomSheetFragmentNote.this.getString(R.string.delete_note_msg));
                builder.setPositiveButton(BottomSheetFragmentNote.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        BottomSheetFragmentNote.this.dateTimeDB.deleteNote(BottomSheetFragmentNote.this.Note_list.get(i).getID());
                        BottomSheetFragmentNote.this.dataPageRefreces();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(BottomSheetFragmentNote.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        this.noteAdapterGps = noteAdapterGps2;
        this.rv_notes.setAdapter(noteAdapterGps2);
    }

    public void dataPageRefreces() {
        this.Note_list.clear();
        this.Note_list = this.dateTimeDB.getNotes();
        String string = this.mSP.getString(getContext(), DatabaseHelper.TABLE_NOTE, Default.notes);
        boolean z = true;
        for (int i = 0; i < this.Note_list.size(); i++) {
            NotesModal notesModal = this.Note_list.get(i);
            if (string.equals(notesModal.getTiitle() + " " + notesModal.getNotes())) {
                notesModal.setSelected(1);
                z = false;
            } else {
                notesModal.setSelected(0);
            }
        }
        if (z && this.Note_list.size() > 0) {
            this.Note_list.get(0).setSelected(1);
        }
        this.noteAdapterGps.refreceadapter(this.Note_list);
    }

    private void listener() {
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {
                if (!BottomSheetFragmentNote.this.mEd_title.getText().toString().trim().equals("") || !BottomSheetFragmentNote.this.mEd_notes.getText().toString().trim().equals("")) {
                    String trim = BottomSheetFragmentNote.this.mEd_notes.getText().toString().trim();
                    String trim2 = BottomSheetFragmentNote.this.mEd_title.getText().toString().trim();
                    for (int i = 0; i < BottomSheetFragmentNote.this.Note_list.size(); i++) {
                        if (BottomSheetFragmentNote.this.Note_list.get(i).getNotes().trim().equalsIgnoreCase(trim)) {
                            BottomSheetFragmentNote.this.mEd_notes.setFocusableInTouchMode(true);
                            BottomSheetFragmentNote.this.mEd_notes.setError(BottomSheetFragmentNote.this.getString(R.string.note_exist));
                            BottomSheetFragmentNote.this.mEd_notes.requestFocus();
                            return;
                        }
                    }
                    BottomSheetFragmentNote.this.dateTimeDB.insetNote(trim, trim2);
                    BottomSheetFragmentNote.this.Note_list.clear();
                    BottomSheetFragmentNote bottomSheetFragmentNote_ = BottomSheetFragmentNote.this;
                    bottomSheetFragmentNote_.Note_list = bottomSheetFragmentNote_.dateTimeDB.getNotes();
                    for (int i2 = 0; i2 < BottomSheetFragmentNote.this.Note_list.size(); i2++) {
                        if (BottomSheetFragmentNote.this.Note_list.size() - 1 == i2) {
                            BottomSheetFragmentNote.this.Note_list.get(i2).setSelected(1);
                        } else {
                            BottomSheetFragmentNote.this.Note_list.get(i2).setSelected(0);
                        }
                    }
                    NotesModal notesModal = BottomSheetFragmentNote.this.Note_list.get(BottomSheetFragmentNote.this.Note_list.size() - 1);
                    BottomSheetFragmentNote.this.mSP.setString(BottomSheetFragmentNote.this.getContext(), DatabaseHelper.TABLE_NOTE, notesModal.getTiitle() + " " + notesModal.getNotes());
                    BottomSheetFragmentNote.this.onselectitem.Onselect();
                    BottomSheetFragmentNote.this.dismiss();
                    return;
                }
                Toast.makeText(BottomSheetFragmentNote.this.getActivity(), "please enter any one from title or note", Toast.LENGTH_LONG).show();
            }
        });
        this.btn_clearnote.setOnClickListener(view -> BottomSheetFragmentNote.this.mEd_notes.setText(""));
        this.btn_cleartitle.setOnClickListener(view -> BottomSheetFragmentNote.this.mEd_title.setText(""));
        this.Note_list = this.dateTimeDB.getNotes();
        String string = this.mSP.getString(getContext(), DatabaseHelper.TABLE_NOTE, Default.notes);
        boolean z = true;
        for (int i = 0; i < this.Note_list.size(); i++) {
            NotesModal notesModal = this.Note_list.get(i);
            if (string.equals(notesModal.getTiitle() + " " + notesModal.getNotes())) {
                notesModal.setSelected(1);
                this.mEd_title.setText(notesModal.getTiitle());
                this.mEd_notes.setText(notesModal.getNotes());
                z = false;
            } else {
                notesModal.setSelected(0);
            }
        }
        if (z && this.Note_list.size() > 0) {
            this.Note_list.get(0).setSelected(1);
            this.mEd_title.setText(this.Note_list.get(0).getTiitle());
            this.mEd_notes.setText(this.Note_list.get(0).getNotes());
        }
    }
}
