package com.example.entregaempotrados;

import android.provider.BaseColumns;

public final class NotesContract {
    private NotesContract(){}

    public static abstract class NoteEntry implements BaseColumns{
        public static final String TABLE_NAME = "Notas2";
        public static final String COLUMN_NAME_KEY = "clave";
        public static final String COLUMN_NAME_VAL = "texto";
    }
}
