package com.aip.practice_fragment.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @ColumnInfo(name = "word")
    @NonNull
    private String nWord;


    public Word(@NonNull String nWord) {
        this.nWord = nWord;
    }

    public String getnWord() {
        return nWord;
    }

    public void setnWord(String nWord) {
        this.nWord = nWord;
    }
}
