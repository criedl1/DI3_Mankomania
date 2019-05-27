package com.example.mankomania.roulette;
import android.support.v7.app.AppCompatActivity;
import com.example.mankomania.slotmachine.SlotMachineActivity;

public class RouletteLogic extends AppCompatActivity {

    private RouletteClass roulette;
    private FieldClass[] fieldArray;
    private String returnString;
    private int money;
    private int wonMoney;
    int rouletteNumber;
    private static final String you_have = "Du hast ";
    private static final String won = " gewonnen!";
    private static final String lost = " verloren";

    public RouletteLogic(){
        spinRoulette();
    }

    public RouletteLogic(int choosenNumber) {
        spinRoulette();
        setMoney(SlotMachineActivity.getMoneyamout());
        checkNumber(choosenNumber);
    }

    public RouletteLogic(ColorEnum choosenColor) {
        spinRoulette();
        setMoney(SlotMachineActivity.getMoneyamout());
        checkColor(choosenColor);
    }

    public RouletteLogic(String choosenDozen) {
        spinRoulette();
        setMoney(SlotMachineActivity.getMoneyamout());
        checkDozen(Integer.parseInt(choosenDozen));

    }

    public void spinRoulette() {
        roulette = new RouletteClass();
        roulette.setUpFields();
        fieldArray = roulette.getFieldClassArray();
        rouletteNumber = roulette.spinIt();
    }

    public void checkColor(ColorEnum choosenColor) {
        for (FieldClass anArray : fieldArray) {
            if (rouletteNumber == anArray.getValue()) {
                if (anArray.getColor() == choosenColor) {
                    setWonMoney(30000);
                    setMoney(getWonMoney() + getMoney()); //--> 80000-50000 Einsatz
                    setReturnString(you_have + getWonMoney() + won);

                } else {
                    setWonMoney(-5000);
                    setMoney(getWonMoney() + getMoney());
                    setReturnString(you_have + getWonMoney() * -1 + lost);
                }
            }
        }
    }

    public int checkNumber(int choosenNumber) {
        if (rouletteNumber == choosenNumber) {
            setWonMoney(145000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(you_have + getWonMoney() + won);
        } else {
            setWonMoney(-50000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(you_have + getWonMoney() * -1 + lost);
        }
        return money;
    }

    public void checkDozen(int choosenDozen) {
        int dozen = 0;

        for (FieldClass fieldClass : fieldArray) {
            if (fieldClass.getValue() == rouletteNumber) {
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
            setReturnString(you_have + getWonMoney() + won);
        } else {
            setWonMoney(-20000);
            setMoney(getWonMoney() + getMoney());
            setReturnString(you_have + getWonMoney() * -1 + lost);
        }
    }

    public int getRandomNumberFromRoulette() {
        return roulette.getRandomNumber();
    }

    public float getDegreeFromRoulette() {
        return roulette.getTheField().getDegree();
    }

    public ColorEnum getColorFromRoulette() {
        return roulette.getTheField().getColor();
    }

    public String getColorString() {
        return roulette.getTheField().getColor().toString();
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

    public RouletteClass getRoulette() {
        return roulette;
    }

    public int getWonMoney() {
        return wonMoney;
    }

    public void setWonMoney(int wonMoney) {
        this.wonMoney = wonMoney;
    }
}
