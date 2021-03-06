package com.surtymar.vbadge.Services;

import android.app.Service;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.IBinder;

import java.util.Arrays;

public class HceService extends HostApduService {
    public HceService() {
    }

    /*  Il s’agit en réalité ici de la commande SELECT AID + la taille en octet de l’AID + l’AID.
    Dans une application en production, il est préférable de déclarer la commande
    SELECT AID = {0x00, (byte) 0xA4, 0x04,0x00}
    et de déclarer l’AID séparément : AID = {0xF0, 0x46, 0x52, 0x55, 0x47, 0x41, 0x4c}.
    Sans oublier d’ajouter la taille de l’AID à la commande SELECT AID*/
    private static final byte[] SELECT_AID = {0x00, (byte) 0xA4, 0x04,0x00,0x09, (byte) 0xF0, 0x53, 0x55, 0x52, 0x54, 0x59, 0x4D, 0x41, 0x52};

    /* L'identifiant que nous allons retourner suivi d'un code qui
    indique le bon déroulé de l'échange (selon la norme ISO 7816-4). */
    private static final byte[] MY_UID = {0x01, 0x02, 0x03, 0x04, (byte) 0x90, 0x00};

    /* code d'erreur si l'échange n'est pas conforme*/
    private static final byte[] MY_ERROR = {0x6F, 0x00};

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle bundle) {
        /* Si le message reçu est égal à ce que nous attendons*/
        if (Arrays.equals(SELECT_AID, apdu))
        {
            /* Nous retournons un identifiant pour permettre l'accès à un bâtiment par exemple */
            return MY_UID;

        }

        /* Si le message n'est pas celui escompté, alors on retourne un code d’erreur */
        else return MY_ERROR;

    }

    @Override
    public void onDeactivated(int i) {

    }


}
