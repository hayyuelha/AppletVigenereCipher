
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.*;
import javax.imageio.ImageIO;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HP
 */
public class vigenere {
    //Non Swing part
    private String pesan;
    private String kunci;
    private String cipher;
    private File inFile;
    
    public void setPesan (String p){
        this.pesan = p;
    }
    
    public void setKunci (String k){
        this.kunci = k;
    }
    
    public void setCipher (String c){
        this.cipher = c;
    }
        
    public String getPesan (){
        return this.pesan;
    }
    
    public String getKunci () {
        return this.kunci;
    }
    
    public String getCipher (){
        return this.cipher;
    }
    
    public int getShift (String k, int i){
//        if (this.pesan.charAt(i-1) != ' '){
            return ((int)k.charAt(i % k.length())) - 65;
//        } else {
//            return ((int)k.charAt((i-1) % k.length())) - 65;
//        }
    }
    
    public int getShiftExtended (String k, int i){
//        if (this.pesan.charAt(i-1) != ' '){
            return ((int)k.charAt(i % k.length()));
//        } else {
//            return ((int)k.charAt((i-1) % k.length())) - 65;
//        }
    }
    
    public void encryptStandard (){
        String ciphertext = "";
        char c;
        String p = this.pesan.toUpperCase();
        String k = this.kunci.toUpperCase();
        int i;
        int j = 0;
        for (i = 0 ; i < p.length() ; i++){
            if ((p.charAt(i) > 'Z' || p.charAt(i) < 'A') && p.charAt(i)!= ' ') continue;
            else {
                if (p.charAt(i) == ' ') {
                    c = ' ';  
                } else if (p.charAt(i) == '\n') {
                    c = ' ';
                } else if (p.charAt(i) + getShift (k,j) > 90) {
                    c = (char)(p.charAt(i) + getShift (k,j) - 26);
                    j++;
                } else {
                    c = (char)(p.charAt(i) + getShift (k,j));
                    j++;
                }
                ciphertext += c;
            }
        }
        this.setCipher(ciphertext);
    }
    
    public void decryptStandard () {
        String plaintext = "";
        char c;
        String cipher = this.cipher.toUpperCase();
        String k = this.kunci.toUpperCase();
        int i;
        int j = 0;
        for (i = 0 ; i < cipher.length() ; i++){
            if ((cipher.charAt(i) > 'Z' || cipher.charAt(i) < 'A') && cipher.charAt(i)!= ' ') continue;
            if (cipher.charAt(i) == ' ') {
                c = ' ';
            } else if (cipher.charAt(i) == '\n') {
                    c = ' ';
            } else if (cipher.charAt(i) - getShift (k,j) < 65) {
                c = (char)(cipher.charAt(i) - getShift (k,j) + 26);
                j++;
            } else {
                c = (char)(cipher.charAt(i) - getShift (k,j));
                j++;
            }
            plaintext += c;
        }
        this.setPesan(plaintext);
    }
    
    public void setAutokey (){
        String k = this.kunci;
        String p = this.pesan;
        k += p;
        k = this.tanpaSpasi(k);
        int i = 0;
        String autokey = "";
        if (k.length() > 25){
            do{
                if ((k.charAt(i) >= 'a' && k.charAt(i) <= 'z') || (k.charAt(i) >= 'A' && k.charAt(i) <= 'Z')){
                    autokey += k.charAt(i);
                }
                i++;
            } while (autokey.length() < 25);
        }
        this.setKunci(autokey);
    }
    
    public void decryptAutokey (){
        String plaintext = "";
        String cipherIn = this.cipher.toUpperCase();
        String k = this.kunci.toUpperCase();
        char c;
        if (k.length() < cipherIn.length()){
            int i;
            int j = 0;
            for (i = 0 ; i < cipherIn.length() ; i++){
                //spasi tetap ditampilkan sebagai spasi
                if (cipherIn.charAt(i) == ' ') {
                    c = ' ';
                } else if (cipherIn.charAt(i) == '\n') {
                    c = ' ';
                } else if (cipherIn.charAt(i) - getShift (k,j) < 65) {
                    c = (char)(cipherIn.charAt(i) - getShift (k,j) + 26);
                    if (k.length() < 25) k += c;
                    j++;    
                } else {
                    c = (char)(cipherIn.charAt(i) - getShift (k,j));
                    if (k.length() < 25) k += c;
                    j++;
                }
                plaintext += c;
            }
            this.setPesan(plaintext);
        }
    }
    
    public void decryptAutokeyExt (){
        String plaintext = "";
        String cipherIn = this.cipher;
        String k = this.kunci;
        char c;
        if (k.length() < cipherIn.length()){
            int i;
            int j = 0;
            for (i = 0 ; i < cipherIn.length() ; i++){
                //spasi tetap ditampilkan sebagai spasi
                if (cipherIn.charAt(i) == ' ') {
                    c = ' ';
                } else if (cipherIn.charAt(i) == '\n') {
                    c = ' ';
                } else if (cipherIn.charAt(i) - getShiftExtended (k,j) < 0) {
                    c = (char)(cipherIn.charAt(i) - getShiftExtended (k,j) + 256);
                    if (k.length() < 25 && ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) k += c;
                    j++;    
                } else {
                    c = (char)(cipherIn.charAt(i) - getShiftExtended (k,j));
                    if (k.length() < 25 && ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) k += c;
                    j++;
                }
                plaintext += c;
            }
            this.setPesan(plaintext);
        }
    }
    
    public void encryptExtended (){
        String ciphertext = "";
        char c;
        String p = this.pesan;
        String k = this.kunci;
        int i;
        int j = 0;
        for (i = 0 ; i < p.length() ; i++){
            if (p.charAt(i)=='.' || p.charAt(i)==',' || p.charAt(i)=='?' || p.charAt(i)=='!') continue;
            //spasi tetap ditampilkan sebagai spasi, tidak dienkripsi
            if (p.charAt(i) == ' ') {
                c = ' ';
            } else if (p.charAt(i) == '\n') {
                    c = ' ';
            } else if (p.charAt(i) + getShiftExtended (k,j) > 255) {
                c = (char)(p.charAt(i) + getShiftExtended (k,j) - 256);
                j++;
            } else {
                c = (char)(p.charAt(i) + getShiftExtended (k,j));
                j++;
            }
            ciphertext += c;
        }
        this.setCipher(ciphertext);
    }
    
    public void decryptExtended () {
        String plaintext = "";
        char c;
        String cipher = this.cipher;
        String k = this.kunci;
        int i;
        int j = 0;
        for (i = 0 ; i < cipher.length() ; i++){
            //spasi tetap ditampilkan sebagai spasi
            if (cipher.charAt(i) == ' ') {
                c = ' ';
            } else if (cipher.charAt(i) == '\n') {
                    c = ' ';
            } else if (cipher.charAt(i) - getShiftExtended (k,j) < 0) {
                c = (char)(cipher.charAt(i) - getShiftExtended (k,j) + 256);
                j++;    
            } else {
                c = (char)(cipher.charAt(i) - getShiftExtended (k,j));
                j++;
            }
            plaintext += c;
        }
        this.setPesan(plaintext);
    }
    
    public String tanpaSpasi (String in){
        String out = "";
        for (int i =0 ; i < in.length() ; i++){
            if (in.charAt(i) != ' ') out += in.charAt(i);
        }
        return out;
    }
    
    public String limaHuruf (String in){
        String out = "";
        String in2 = this.tanpaSpasi(in);
        char spasi = ' ';
        for (int i = 0 ; i < in2.length() ; i++){
            if (i%5 != 0 || i == 0) out += in2.charAt(i);
            else{
                out += spasi;
                out += in2.charAt(i);
            }
        }
        return out;
    }
    
    public String readFile(String fileInput) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileInput));
    try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
//    public void readImage(String fileInput) throws IOException {
//        String imgTxt = "";
//        File fnew=new File(fileInput);
//        BufferedImage originalImage=ImageIO.read(fnew);
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        ImageIO.write(originalImage, "jpg", baos );
//        byte[] imageInByte=baos.toByteArray();
////        imgTxt += imageInByte.toString();
////        System.out.println(new String(imageInByte),StandardCharsets.US_ASCII);
//    }
}
