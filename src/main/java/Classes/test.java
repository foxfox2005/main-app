/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import Daos.ThongKeDao;
import java.util.List;

/**
 *
 * @author DELL
 */
public class test {
    public static void main(String[] args){
        ThongKeDao tkdao = new ThongKeDao();
        List<Object[]> list = tkdao.getBangDiem(1);
        for(Object[] obj : list){
            System.out.println("=>" + obj[0]);
        }
    }
}
