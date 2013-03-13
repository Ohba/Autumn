/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ohba.autumn.sample.service;

import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author kradolferap
 */
@ToString
@NoArgsConstructor
public class BogusService {
    
    public void printToConsole(String text){
        System.out.println(text);
    }
}
