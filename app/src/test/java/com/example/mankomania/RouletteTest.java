package com.example.mankomania;

import com.example.mankomania.roulette.ColorEnum;
import com.example.mankomania.roulette.FieldClass;
import com.example.mankomania.roulette.RouletteLogic;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RouletteTest {

    private RouletteLogic roulette;
    private FieldClass[] fieldClassArray;
    private int randomNumber;

    @Before
    public void setUp() {
        roulette = new RouletteLogic();
        fieldClassArray = roulette.getRoulette().getFieldClassArray();
        roulette.spinRoulette();
    }

    @Test
    public void testSetUpRoulette() {
        assertEquals(37, roulette.getRoulette().getFieldArrayLength());
    }

    @Test
    public void testFields() {
        assertEquals(ColorEnum.RED, fieldClassArray[1].getColor());
        assertEquals(32, fieldClassArray[1].getValue());
        assertEquals(ColorEnum.BLACK, fieldClassArray[14].getColor());
        assertEquals(11, fieldClassArray[14].getValue());
    }

    @Test
    public void testNumberConstructor(){
        RouletteLogic numberRoulette = new RouletteLogic(15);

        if(numberRoulette.getRandomNumberFromRoulette() == 15){
            assertEquals(numberRoulette.getWonMoney(), 145000);
        }
        else{
            assertEquals(numberRoulette.getWonMoney(), -50000);
        }
    }


    @Test
    public void testNumberWin() {
        roulette.spinRoulette();
        randomNumber = roulette.getRandomNumberFromRoulette();
        roulette.checkNumber(randomNumber);
        assertEquals(145000, roulette.getMoney());
    }

    @Test
    public void testNumberLost() {
        roulette.spinRoulette();
        randomNumber = roulette.getRandomNumberFromRoulette();
        if (randomNumber != 3) {
            roulette.checkNumber(3);
        } else {
            roulette.checkNumber(4);
        }
        assertEquals(-50000, roulette.getMoney());
    }

    @Test
    public void testColorConstructor(){
        RouletteLogic colorRoulette = new RouletteLogic(ColorEnum.BLACK);

        if(colorRoulette.getColorFromRoulette().equals(ColorEnum.BLACK)){
            assertEquals(colorRoulette.getWonMoney(), 30000);
        }
        else{
            assertEquals(colorRoulette.getWonMoney(), -5000);
        }
    }

    @Test
    public void testColorWin(){
        roulette.spinRoulette();
        ColorEnum color = roulette.getColorFromRoulette();
        roulette.checkColor(color);
        assertEquals(30000, roulette.getMoney());
    }

    @Test
    public void testColorLost(){
        roulette.spinRoulette();
        if(roulette.getColorFromRoulette()== ColorEnum.BLACK|| roulette.getColorFromRoulette()== ColorEnum.GREEN){
            roulette.checkColor(ColorEnum.RED);
        }
        else{
            roulette.checkColor(ColorEnum.BLACK);
        }
        assertEquals(-5000, roulette.getMoney());
    }

    @Test
    public void testDozenConstructor(){
        RouletteLogic dozenRoulette = new RouletteLogic("2");

        if(dozenRoulette.getRandomNumberFromRoulette() <= 24 && dozenRoulette.getRandomNumberFromRoulette() > 12){
            assertEquals(dozenRoulette.getWonMoney(), 80000);
        }
        else {
            assertEquals(dozenRoulette.getWonMoney(), -20000);
        }
    }

    @Test
    public void testDozenWin(){
        int actualDozen;

        if(roulette.getRandomNumberFromRoulette() <= 12){
            actualDozen = 1;
        }
        else if(roulette.getRandomNumberFromRoulette() <= 24){
            actualDozen = 2;
        }
        else {
            actualDozen = 3;
        }

        roulette.checkDozen(actualDozen);
        assertEquals(80000, roulette.getMoney());
    }

    @Test
    public void testDozenLost(){
        int actualDozen;
        int choosenDozen;
        if(roulette.getRandomNumberFromRoulette() <= 12){
            actualDozen = 1;
        }
        else if(roulette.getRandomNumberFromRoulette()<= 24 && roulette.getRandomNumberFromRoulette() > 12){
            actualDozen = 2;
        }
        else {
            actualDozen = 3;
        }

        if(actualDozen != 3){
            choosenDozen = 3;
        }
        else{
            choosenDozen = 2;
        }

        roulette.checkDozen(choosenDozen);
        assertEquals(- 20000, roulette.getMoney());
    }

    @Test
    public void testGetColorString(){
        String cs = roulette.getColorFromRoulette().toString();
        assertEquals(roulette.getColorString(), cs);
    }

    @Test
    public void testGetDegreeFromRoulette(){
        float dfr = roulette.getRoulette().getTheField().getDegree();
        assertEquals(roulette.getDegreeFromRoulette(), dfr, 0);
    }
}


