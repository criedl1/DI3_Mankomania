package com.example.mankomania.roulette;
import android.support.v7.app.AppCompatActivity;

import java.security.SecureRandom;

public class RouletteLogic extends AppCompatActivity {

    private FieldClass[] fieldArray;
    private String returnString;
    private int money;
    private int wonMoney;
    private int randomNumber;
    private static final String YOU_HAVE = "Du hast ";
    private static final String WON = " gewonnen!";
    private static final String LOST = " verloren";
    private FieldClass[] fieldClassArray = new FieldClass[36];

    //need this for testing
    public RouletteLogic(){
        spinRoulette();
    }

    public RouletteLogic(int choosenNumber, int slotMoney) {
        spinRoulette();
        setMoney(slotMoney);
        checkNumber(choosenNumber);
    }

    public RouletteLogic(ColorEnum choosenColor, int slotMoney) {
        spinRoulette();
        setMoney(slotMoney);
        checkColor(choosenColor);
    }

    public RouletteLogic(String choosenDozen, int slotMoney) {
        spinRoulette();
        setMoney(slotMoney);
        checkDozen(Integer.parseInt(choosenDozen));
    }

    /*
    I hardcoded this, because the degrees aren't linear.
    I adjusted them from time to time because it looks prettier.
    Also there isn't a real pattern for the order of the numbers or colors.
    */
    public FieldClass[] setUpFields() {
        FieldClass fieldClass0 = new FieldClass(ColorEnum.GREEN, 0, 0f);
        FieldClass fieldClass32 = new FieldClass(ColorEnum.RED, 32, 9.73f);
        FieldClass fieldClass15 = new FieldClass(ColorEnum.BLACK, 15, 19.46f);
        FieldClass fieldClass19 = new FieldClass(ColorEnum.RED, 19, 29.19f);
        FieldClass fieldClass4 = new FieldClass(ColorEnum.BLACK, 4, 38.92f);
        FieldClass fieldClass21 = new FieldClass(ColorEnum.RED, 21, 48.65f);
        FieldClass fieldClass2 = new FieldClass(ColorEnum.BLACK, 2, 58.38f);
        FieldClass fieldClass25 = new FieldClass(ColorEnum.RED, 25, 68.11f);
        FieldClass fieldClass17 = new FieldClass(ColorEnum.BLACK, 17, 77.84f);
        FieldClass fieldClass34 = new FieldClass(ColorEnum.RED, 34, 87.57f);
        FieldClass fieldClass6 = new FieldClass(ColorEnum.BLACK, 6, 97.30f);
        FieldClass fieldClass27 = new FieldClass(ColorEnum.RED, 27, 107.03f);
        FieldClass fieldClass13 = new FieldClass(ColorEnum.BLACK, 13, 116.76f);
        FieldClass fieldClass36 = new FieldClass(ColorEnum.RED, 36, 126.49f);
        FieldClass fieldClass11 = new FieldClass(ColorEnum.BLACK, 11, 136.22f);
        FieldClass fieldClass30 = new FieldClass(ColorEnum.RED, 30, 145.95f);
        FieldClass fieldClass8 = new FieldClass(ColorEnum.BLACK, 8, 155.68f);
        FieldClass fieldClass23 = new FieldClass(ColorEnum.RED, 23, 165.41f);
        FieldClass fieldClass10 = new FieldClass(ColorEnum.BLACK, 10, 175.14f);
        FieldClass fieldClass5 = new FieldClass(ColorEnum.RED, 5, 184.87f);
        FieldClass fieldClass24 = new FieldClass(ColorEnum.BLACK, 24, 194.60f);
        FieldClass fieldClass16 = new FieldClass(ColorEnum.RED, 16, 204.33f);
        FieldClass fieldClass33 = new FieldClass(ColorEnum.BLACK, 33, 214.06f);
        FieldClass fieldClass1 = new FieldClass(ColorEnum.RED, 1, 223.79f);
        FieldClass fieldClass20 = new FieldClass(ColorEnum.BLACK, 20, 233.52f);
        FieldClass fieldClass14 = new FieldClass(ColorEnum.RED, 14, 243.25f);
        FieldClass fieldClass31 = new FieldClass(ColorEnum.BLACK, 31, 252.98f);
        FieldClass fieldClass9 = new FieldClass(ColorEnum.RED, 9, 262.71f);
        FieldClass fieldClass22 = new FieldClass(ColorEnum.BLACK, 22, 272.44f);
        FieldClass fieldClass18 = new FieldClass(ColorEnum.RED, 18, 282.17f);
        FieldClass fieldClass29 = new FieldClass(ColorEnum.BLACK, 29, 291.90f);
        FieldClass fieldClass7 = new FieldClass(ColorEnum.RED, 7, 301.63f);
        FieldClass fieldClass28 = new FieldClass(ColorEnum.BLACK, 28, 311.36f);
        FieldClass fieldClass12 = new FieldClass(ColorEnum.RED, 12, 321.09f);
        FieldClass fieldClass35 = new FieldClass(ColorEnum.BLACK, 35, 330.82f);
        FieldClass fieldClass3 = new FieldClass(ColorEnum.RED, 3, 340.55f);
        FieldClass fieldClass26 = new FieldClass(ColorEnum.BLACK, 26, 350.28f);


        FieldClass[] tempArray = {fieldClass0, fieldClass32, fieldClass15, fieldClass19, fieldClass4, fieldClass21, fieldClass2, fieldClass25, fieldClass17,
                fieldClass34, fieldClass6, fieldClass27, fieldClass13, fieldClass36, fieldClass11, fieldClass30, fieldClass8, fieldClass23,
                fieldClass10, fieldClass5, fieldClass24, fieldClass16, fieldClass33, fieldClass1, fieldClass20, fieldClass14, fieldClass31,
                fieldClass9, fieldClass22, fieldClass18, fieldClass29, fieldClass7, fieldClass28, fieldClass12, fieldClass35, fieldClass3,
                fieldClass26};

        this.fieldClassArray = tempArray;
        return tempArray;
    }

    public FieldClass getTheField() {
        FieldClass field = null;
        for (int i = 0; i < fieldClassArray.length; i++) {
            if (fieldClassArray[i].getValue() == getRandomNumber()) {
                field = fieldClassArray[i];
            }
        } return field;
    }

    public int spinRoulette() {
        setUpFields();
        SecureRandom random = new SecureRandom();
        fieldArray = getFieldClassArray();
        randomNumber = random.nextInt(36);
        return randomNumber;
    }

    public void checkColor(ColorEnum choosenColor) {
        for (FieldClass fc : fieldArray) {
            if (randomNumber == fc.getValue()) {
                if (fc.getColor() == choosenColor) {
                    setWonMoney(30000);
                    setMoney(getWonMoney() + getMoney()); //--> 80000-50000 Einsatz
                    setReturnString(YOU_HAVE + getWonMoney() + WON);

                } else {
                    setWonMoney(-50000);
                    setMoney(getWonMoney() + getMoney());
                    setReturnString(YOU_HAVE + getWonMoney() * -1 + LOST);
                }
            }
        }
    }

    public int checkNumber(int choosenNumber) {
        if (randomNumber == choosenNumber) {
            setWonMoney(145000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(YOU_HAVE + getWonMoney() + WON);
        } else {
            setWonMoney(-5000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(YOU_HAVE + getWonMoney() * -1 + LOST);
        }
        return money;
    }

    public void checkDozen(int choosenDozen) {
        int dozen = 0;

        for (FieldClass fieldClass : fieldArray) {
            if (fieldClass.getValue() == randomNumber) {
                if (fieldClass.getValue() <= 12) {
                    dozen = 1;
                } else if (fieldClass.getValue() <= 24 && fieldClass.getValue() > 12) {
                    dozen = 2;
                } else {
                    dozen = 3;
                }
            }
        }

        if (choosenDozen == dozen) {
            setWonMoney(80000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(YOU_HAVE + getWonMoney() + WON);
        } else {
            setWonMoney(-20000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(YOU_HAVE + getWonMoney() * -1 + LOST);
        }
    }

    protected int getRandomNumber(){
        return randomNumber;
    }

    public int getFieldArrayLength(){
        return fieldClassArray.length;
    }

    public FieldClass[] getFieldClassArray() {
        return fieldClassArray;
    }

    public int getRandomNumberFromRoulette() {
        return getRandomNumber();
    }

    public float getDegreeFromRoulette() {
        return getTheField().getDegree();
    }

    public ColorEnum getColorFromRoulette() {
        return getTheField().getColor();
    }

    public String getColorString() {
        return getTheField().getColor().toString();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int newMoney) {
        money = newMoney;
    }

    public String getReturnString() {
        return returnString;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public int getWonMoney() {
        return wonMoney;
    }

    public void setWonMoney(int wonMoney) {
        this.wonMoney = wonMoney;
    }


}
