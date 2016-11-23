package com.HyKj.UKeBao.util;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MapZoomUtils {
    //比例尺值（单位：米）
    public static  int zoomValue;
    public static int getZoomMap( float zoomLevel){
        if (zoomLevel == 21){
            zoomValue = 100 ;
        }else if (zoomLevel<21 && zoomLevel>= 20){
            zoomValue = 1000 ;
        }else if (zoomLevel<20 && zoomLevel>= 19){
            zoomValue = 2000 ;
        }else if (zoomLevel<19 && zoomLevel>= 18){
            zoomValue = 3000 ;
        }else if (zoomLevel<18 && zoomLevel>= 17){
            zoomValue = 5000 ;
        }else if (zoomLevel<17 && zoomLevel>= 16){
            zoomValue = 10000 ;
        }else if (zoomLevel<16 && zoomLevel>= 15){
            zoomValue = 20000 ;
        }else if (zoomLevel<15 && zoomLevel>= 14){
            zoomValue = 25000 ;
        }else if (zoomLevel<14 && zoomLevel>=13){
            zoomValue = 50000 ;
        }else if (zoomLevel<13 && zoomLevel>= 12){
            zoomValue = 100000 ;
        }else if (zoomLevel<12 && zoomLevel>= 11){
            zoomValue = 150000 ;
        }else if (zoomLevel<11 && zoomLevel>= 10){
            zoomValue = 200000 ;
        }else if (zoomLevel<10 && zoomLevel>= 9){
            zoomValue = 250000 ;
        }else if (zoomLevel<9 && zoomLevel>= 8){
            zoomValue = 500000 ;
        }else if (zoomLevel<8 && zoomLevel>= 7){
            zoomValue = 1000000 ;
        }else if (zoomLevel<7 && zoomLevel>= 6){
            zoomValue = 2000000 ;
        }else if (zoomLevel<6 && zoomLevel>= 5){
            zoomValue = 5000000 ;
        }else if (zoomLevel<5 && zoomLevel>= 4){
            zoomValue = 10000000 ;
        }else if (zoomLevel<4 && zoomLevel>= 3){
            zoomValue = 20000000 ;
        }else{
            zoomValue = 50000000 ;
        }
        return zoomValue ;

    }
}
