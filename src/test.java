
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HP
 */


public class test {
    public static void main (String args[]) throws IOException{
        vigenere v = new vigenere();
        String s = "negara penghasil minyak";
        String p = "indo";
        v.setPesan(s);
        System.out.println(v.tanpaSpasi(v.getPesan()));
        System.out.println(v.limaHuruf(v.getPesan()));
//        v.readImage("C:/Users/HP/Pictures/dear hayyu.jpg");
    }
}
