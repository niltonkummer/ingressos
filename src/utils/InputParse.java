/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Scanner;

/**
 * Valida as entradas do JOptionPane de acordo com os tipos
 * @author niltonkummer
 */
public class InputParse {
    public static boolean validateInt(String option) {
        Scanner sc = new Scanner(option);
        return sc.hasNextInt();
    }
    public static boolean isNull(Object o) {
        return (o == null);
    }
}


