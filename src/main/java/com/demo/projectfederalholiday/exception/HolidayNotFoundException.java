/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.exception;

/**
 *
 * @author Prabhakaran
 */
public class HolidayNotFoundException extends RuntimeException {

    public HolidayNotFoundException(String message) {
        super(message);
    }
}
