package com.example.masionclarifiant;

import android.os.RemoteException;

public interface IconnectionService {
    int getStatus() throws RemoteException;
    void setSocket(String ip) throws RemoteException;
    void connect() throws RemoteException;
    void disconnect() throws RemoteException;
    void send();
    void receive();
}
