/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ohba.autumn.sample.service;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author kradolferap
 */
@Slf4j
@ToString
@NoArgsConstructor
public class BogusService {
    
    public void message(String text){
        log.info(text);
    }
}
