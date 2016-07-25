// ISocketService.aidl
package com.xinran.qxviewslib;
import com.xinran.qxviewslib.Fruit;
// Declare any non-default types here with import statements

interface ISocketService {
   boolean connectNet(String params);
   Fruit getFruit();

}
