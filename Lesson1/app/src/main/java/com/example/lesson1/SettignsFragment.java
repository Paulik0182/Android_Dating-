package com.example.lesson1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class SettignsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_settings, container, false );
        initView ( view );

        return view;
    }

    private void initView(View view) {
        initSwitchBackStack ( view );
        initRadioAdd ( view );
        initRadioReplace ( view );
        initSwitchBackAsRemove ( view );
        initSwitchDeleteBeforeAdd ( view );
    }

    private void initRadioReplace(View view) {
        RadioButton radioButtonReplace = view.findViewById ( R.id.radioButtonReplace );//находим кнопку
        radioButtonReplace.setChecked ( !Settings.isAddFragment );//назначить фрагмент
        radioButtonReplace.setOnCheckedChangeListener ( (buttonView, isChecked) -> {
            Settings.isAddFragmen = !isChecked;//сохраняем в isAddFragmen
            writeSettings ();

        } );


//        RadioButton radioButtonReplace =
//                view.findViewById ( R.id.radioButtonReplace );
//        radioButtonReplace.setChecked ( !Settings.isAddFragment );
//        radioButtonReplace.setOnCheckedChangeListener ( new
//                                                                CompoundButton.OnCheckedChangeListener () {
//                                                                    @Override
//                                                                    public void onCheckedChanged(CompoundButton buttonView, boolean
//                                                                            isChecked) {
//                                                                        Settings.isAddFragment = !isChecked;
//                                                                        writeSettings ();
//                                                                    }
//                                                                } );
    }

    //включанели
    private void initRadioAdd(View view) {
        RadioButton radioButtonReplace = view.findViewById ( R.id.radioButtonAdd );//находим кнопку
        radioButtonReplace.setChecked ( !Settings.isAddFragment );//назначить фрагмент
        radioButtonReplace.setOnCheckedChangeListener ( (buttonView, isChecked) -> {
            Settings.isAddFragmen = !isChecked;//сохраняем в isAddFragmen
            writeSettings ();

        } );
//        radioButtonReplace.setOnCheckedChangeListener ( new
//                                                            CompoundButton.OnCheckedChangeListener () {
//                                                                @Override
//                                                                public void onCheckedChanged(CompoundButton buttonView, boolean
//                                                                        isChecked) {
//                                                                    Settings.isAddFragment = isChecked;
//                                                                    writeSettings ();
//                                                                }
//                                                            } );
    }

    private void initSwitchBackStack(View view) {
        SwitchCompat switchCompat = view.findViewById ( R.id.switchBackStack );//найти кнопку
        switchCompat.setChecked ( Settings.isBackStack );//храним данные  отдельно от view
        switchCompat.setOnCheckedChangeListener ( (buttonView, isChecked) -> {
            Settings.isBackStack = isChecked;
            writeSettings ();

        } );
    }

    // Сохранение настроек приложения
    private void writeSettings() {
// Специальный класс для хранения настроек . создаем его. передаем имя и моде
        SharedPreferences sharedPreferences = requireActivity ()
                .getSharedPreferences ( Settings.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE );
// Настройки сохраняются посредством специального класса editor (патерн эбитерн)
//        SharedPreferences.Editor editor = sharedPref.edit ();
        sharedPreferences.edit ()
// Задаём значения настроек
                .putBoolean ( Settings.IS_BACK_STACK_USED, Settings.isBackStack )
                .putBoolean ( Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment )
                .putBoolean ( Settings.IS_BACK_AS_REMOVE_FRAGMENT, Settings.isBackAsRemove )
                .putBoolean ( Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, Settings.isDeleteBeforeAdd )
                .apply ();
        // Сохраняем значения настроек
//        editor.apply ();
    }

    private void initSwitchBackAsRemove(View view) {
        SwitchCompat switchBackAsRemove = view.findViewById ( R.id.switchBackAsRemove );//найти кнопку
        switchBackAsRemove.setChecked ( Settings.isBackAsRemove );//храним данные  отдельно от view
        switchBackAsRemove.setOnCheckedChangeListener ( (buttonView, isChecked) -> {
            Settings.isBackAsRemove = isChecked;//сохраняем настройку
            writeSettings ();

        } );


//        SwitchCompat switchBackAsRemove =
//                view.findViewById ( R.id.switchBackAsRemove );
//        switchBackAsRemove.setChecked ( Settings.isBackAsRemove );
//        switchBackAsRemove.setOnCheckedChangeListener ( new
//                                                                CompoundButton.OnCheckedChangeListener () {
//                                                                    @Override
//                                                                    public void onCheckedChanged(CompoundButton buttonView, boolean
//                                                                            isChecked) {
//                                                                        Settings.isBackAsRemove = isChecked;
//                                                                        writeSettings ();
//                                                                    }
//                                                                } );
    }

    private void initSwitchDeleteBeforeAdd(View view) {
        SwitchCompat switchDeleteBeforeAdd = view.findViewById ( R.id.switchDeleteBeforeAdd );//найти кнопку
        switchDeleteBeforeAdd.setChecked ( Settings.isDeleteBeforeAdd );//храним данные  отдельно от view
        switchDeleteBeforeAdd.setOnCheckedChangeListener ( (buttonView, isChecked) -> {
            Settings.isDeleteBeforeAdd = isChecked;//сохраняем настройку
            writeSettings ();

        } );


//        SwitchCompat switchDeleteBeforeAdd =
//                view.findViewById ( R.id.switchDeleteBeforeAdd );
//        switchDeleteBeforeAdd.setChecked ( Settings.isDeleteBeforeAdd );
//        switchDeleteBeforeAdd.setOnCheckedChangeListener ( new
//                                                                   CompoundButton.OnCheckedChangeListener () {
//                                                                       @Override
//                                                                       public void onCheckedChanged(CompoundButton buttonView, boolean
//                                                                               isChecked) {
//
//                                                                           Settings.isDeleteBeforeAdd = isChecked;
//                                                                           writeSettings ();
//                                                                       }
//                                                                   } );
    }


}
