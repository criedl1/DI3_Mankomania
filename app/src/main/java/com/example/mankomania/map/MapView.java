package com.example.mankomania.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mankomania.R;
import com.example.mankomania.dice.Dice;
import com.example.mankomania.slotmachine.SlotMachineActivity;

import java.util.Arrays;

import static com.example.mankomania.map.Aktien.HYPO;
import static com.example.mankomania.map.Aktien.INFINEON;
import static com.example.mankomania.map.Aktien.STRABAG;
import static com.example.mankomania.map.Hotel.PLATTENWIRT;
import static com.example.mankomania.map.Hotel.SANDWIRTH;
import static com.example.mankomania.map.Hotel.SEEPARK;


public class MapView extends AppCompatActivity {

    private ImageView imgview1;
    private ImageView imgview2;


    //Screen Size
    private int screenWidth;

    //Images
    private ImageView[] figures = new ImageView[4];

    private TextView[] moneyFields = new TextView[4];


    private float field1;
    private float field2;
    private float field0;

    BroadcastReceiver resultReceiver;

    private GameController gameController;
    private int currentField = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        FloatingActionButton fab = findViewById(R.id.miniMap);
        fab.setImageDrawable(getDrawable(R.drawable.game_board_icon));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment miniMapDialog = new MiniMapDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable("PLAYERS", gameController);
                miniMapDialog.setArguments(args);
                miniMapDialog.show(getSupportFragmentManager(), "mini_map");
            }
        });

        initButtons();
        Log.d("xxx", "llll" + Arrays.toString(GameController.allfields));


        //Get Intent and start client
        Intent intent = getIntent();

        figures[0] = findViewById(R.id.figure1);
        figures[1] = findViewById(R.id.figure2);
        figures[2] = findViewById(R.id.figure3);
        figures[3] = findViewById(R.id.figure4);

        moneyFields[0] = findViewById(R.id.currentmoney);
        moneyFields[1] = findViewById(R.id.currentmoney2);
        moneyFields[2] = findViewById(R.id.currentmoney3);
        moneyFields[3] = findViewById(R.id.currentmoney4);


        //Position on fields for figures
        field1 = 300;
        field2 = 1000;
        field0 = -50;

        //Get screen size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        //Start Position of figures
        figures[0].setX(field1);
        figures[0].setY(60);
        figures[1].setX(field1);
        figures[1].setY(300);
        figures[2].setX(field1);
        figures[2].setY(510);
        figures[3].setX(field1);
        figures[3].setY(710);

        // create Receiver
        resultReceiver = createBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                resultReceiver,
                new IntentFilter("client.update"));

        this.gameController = new GameController(intent.getStringExtra("IP"), this);

        this.gameController.startClient();
    }

    //Broadcast Receiver to get Messages from the Client Thread
    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                gameController.handleMessage(intent.getStringExtra("result"));
            }
        };
    }


    private void closeWaitFragment() {
        findViewById(R.id.waitContainer).setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onDestroy() {
        if (resultReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(
                    resultReceiver);
        }
        super.onDestroy();
    }

    private static final String translationX = "translationX";

    public void movePlayerOut(final Player player) {
        float distance;
        distance = screenWidth;
        ObjectAnimator animation = ObjectAnimator.ofFloat(player.getFigure(), translationX, distance);
        animation.setDuration(1000);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                step2();
            }
        });
        animation.start();
    }

    public void movePlayerOverScreen(final Player player, final boolean movingOverLottery) {
        float distance;
        distance = screenWidth - field0;
        player.getFigure().setX(field0);
        player.getFigure().setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(player.getFigure(), translationX, distance);
        animation.setDuration(1000);
        animation.start();
        final MapView _this = this;
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (movingOverLottery) {
                    //   Toast.makeText(_this, getString(R.string.lottery_toast), Toast.LENGTH_LONG).show();
                    gameController.sendMoveOverLotto();
                }
                step2();
            }
        });
    }

    public void movePlayerIn(final Player player) {
        float distance;
        if ((player.getCurrentField() & 1) != 0) {
            distance = field2;
        } else {
            distance = field1 - field0;
        }

        player.getFigure().setX(field0);
        ObjectAnimator animation = ObjectAnimator.ofFloat(player.getFigure(), translationX, distance);
        animation.setDuration(1000);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                runFieldAction(player.getCurrentField());
            }
        });
    }


    private void initButtons() {
        imgview1 = findViewById(R.id.imageViewStart);
        imgview2 = findViewById(R.id.imageView2);

        ImageView wuerfeln = findViewById(R.id.wuerfeln); // button fürs würfeln
        wuerfeln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiceFragment();
            }
        });
    }

    public void showDiceFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Dice fragment = new Dice();
        transaction.add(R.id.diceContainer, fragment);
        transaction.commit();

    }

    public void step1() {
        Player cPlayer = gameController.currentPlayer();
        cPlayer.setTemporaryField(cPlayer.getCurrentField());
        Log.d("xxx", "step1 currentfield: " + cPlayer.getCurrentField());
        displayField(cPlayer.getCurrentField());
        movePlayerOut(cPlayer);
    }

    public void step2() {
        Player cPlayer = gameController.currentPlayer();
        boolean movingOverLottery = false;
        if (gameController.isMyTurn() && (GameController.allfields[cPlayer.getTemporaryField()] == R.drawable.field_lottery || GameController.allfields[(cPlayer.getTemporaryField() + 1) % GameController.allfields.length] == R.drawable.field_lottery)) {
            movingOverLottery = true;
        }
        cPlayer.setTemporaryField(cPlayer.getTemporaryField() + 2);
        Log.d("xxx", "step2 currentfield: " + cPlayer.getCurrentField());
        if (cPlayer.getTemporaryField() / 2 < cPlayer.getCurrentField() / 2) {
            displayField(cPlayer.getTemporaryField());
            movePlayerOverScreen(cPlayer, movingOverLottery);
        } else {
            step3();
        }
    }

    public void step3() {

        Player cPlayer = gameController.currentPlayer();
        Log.d("xxx", "step3 currentfield: " + cPlayer.getCurrentField());
        movePlayerIn(cPlayer);
        displayField(cPlayer.getCurrentField());
    }


    public void nextSideofMap(View view) {
        currentField += 2;
        currentField = currentField % GameController.allfields.length;
        updateField();
    }

    public void furtherSideofMap(View view) {
        currentField -= 2;
        if (currentField < 0) {
            currentField = GameController.allfields.length + currentField;
        }
        updateField();
    }

    public void displayField(int field) {
        currentField = field;

        if ((currentField & 1) != 0) {
            currentField--;
        }
        currentField = currentField % GameController.allfields.length;
        updateField();
    }


    public void updateField() {
        updatePlayers();
        imgview1.setImageResource(GameController.allfields[currentField]);
        imgview2.setImageResource(GameController.allfields[currentField + 1]);
    }

    public void updatePlayers() {
        for (Player player : gameController.players) {
            if (player.getCurrentField() == currentField) {
                player.getFigure().setX(field1);
                player.getFigure().setVisibility(View.VISIBLE);
            } else if (player.getCurrentField() == currentField + 1) {
                player.getFigure().setX(field2);
                player.getFigure().setVisibility(View.VISIBLE);
            } else {
                player.getFigure().setVisibility(View.INVISIBLE);
            }
        }
    }

    private void runFieldAction(int currentField) {
        if (gameController.isMyTurn()) {
            int fieldID = GameController.allfields[currentField];
            switch (fieldID) {
                case R.drawable.field_casino:
                    startCasino();
                    break;
                case R.drawable.field_getsomemoney:
                    showMoneyUpdate(10000);
                    break;
                case R.drawable.field_lindwurm:
                    showMoneyUpdate(-100000);
                    break;
                case R.drawable.field_stadium:
                    showMoneyUpdate(-5000);
                    break;
                case R.drawable.field_zoo:
                    showMoneyUpdate(-50000);
                    break;
                case R.drawable.field_alterplatz:
                    showMoneyUpdate(10000);
                    break;
                case R.drawable.field_klage:
                    showMoneyUpdate(25000);
                    break;
                case R.drawable.field_woerthersee:
                    showMoneyUpdate(-10000);
                    break;
                case R.drawable.field_minimundus:
                    showMoneyUpdate(-30000);
                    break;
                case R.drawable.field_aktie1:
                    buyAktie(HYPO);
                    break;
                case R.drawable.field_aktie2:
                    buyAktie(INFINEON);
                    break;
                case R.drawable.field_aktie3:
                    buyAktie(STRABAG);
                    break;
                case R.drawable.field_horserace:
                    // TODO - change method signature if needed and then do your stuff
                    startHorseRace();
                    break;
                case R.drawable.field_hotelsandwirth:
                    buyHotel(SANDWIRTH);
                    break;
                case R.drawable.field_plattenwirt:
                    buyHotel(PLATTENWIRT);
                    break;
                case R.drawable.field_seeparkhotel:
                    buyHotel(SEEPARK);
                    break;
                default:
                    return;
            }
        }

    }



    private void startHorseRace() {
        gameController.startHorseRace();
    }

    public int showMoneyUpdate(int amount) {
        Player cPlayer = gameController.currentPlayer();
        int playerIdx = gameController.getPlayerIndex(cPlayer);
        if (playerIdx >= 0) {
          //  gameController.setMoney(playerIdx, cPlayer.getMoney() + amount);
            gameController.updateMoney(playerIdx, amount);

        }
        return amount;
    }

    public void startCasino() {
        Intent it = new Intent(this, SlotMachineActivity.class);
        startActivity(it);
    }

    public void sendRollDice() {
        gameController.rollTheDice();
    }

    public void rollForce(View view) {
        gameController.rollTheDice();
    }

    public void closeDiceFragment(View view) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.diceContainer)).commit();
    }


    public void showMyDiceResult(int outcome) {
        Dice fragment = ((Dice) getSupportFragmentManager().findFragmentById(R.id.diceContainer));
        if (fragment != null) {
            fragment.showResult(outcome);
        } else {
            //should not happen because it is my turn
            throw new IllegalStateException();
        }
    }

    public void showMyAccountBalance(int outcome) {
        Toast.makeText(this, "Dein Kontostand ändert sich auf " + outcome, Toast.LENGTH_LONG).show();
    }

    public void showMyAktienkauf(Aktien aktien) {
        Toast.makeText(this, "Du erhälst Aktie  " + aktien, Toast.LENGTH_LONG).show();
    }

    public void showMyHotelkauf(Hotel hotel) {
        Toast.makeText(this, "Du erhälst das Hotel  " + hotel, Toast.LENGTH_LONG).show();
    }

    public void showSomeonesHotelkauf(int player, Hotel hotel) {
        Toast.makeText(this, "Spieler  " + (player +1) + "erhält das Hotel " + hotel, Toast.LENGTH_LONG).show();
    }

    public void showSomeonesDiceResult(int player, int outcome) {
        Toast.makeText(this, "Player " + (player + 1) + " diced " + outcome, Toast.LENGTH_LONG).show();
    }

    public void showSomeonesAccountBalance(int player, int outcome) {
        Toast.makeText(this, "Der Kontostand von Player " + (player + 1) + " ändert sich auf " + outcome, Toast.LENGTH_LONG).show();
    }

    public void showSomeonesAktienkauf(int player, Aktien aktien) {
        Toast.makeText(this, "Spieler" + (+player + 1) + " erhält Aktie  " + aktien, Toast.LENGTH_LONG).show();
    }


    public void initPlayerFields() {
        for (int i = 0; i < this.gameController.players.size(); i++) {
            gameController.players.get(i).initFields(figures[i], moneyFields[i]);
        }
        updatePlayers();
        closeWaitFragment();
    }

    public void startMyTurn() {
        ImageView wuerfeln = findViewById(R.id.wuerfeln); // button fürs würfeln
        wuerfeln.setVisibility(View.VISIBLE);
    }

    public void endMyTurn() {
        ImageView wuerfeln = findViewById(R.id.wuerfeln); // button fürs würfeln
        wuerfeln.setVisibility(View.INVISIBLE);
    }

    public void setLotto(int lotto) {
        Toast.makeText(this, "Lotto ändert sich auf " + lotto, Toast.LENGTH_LONG).show();
    }

    private void setAktie(Aktien aktie) {
        Player cPlayer = gameController.currentPlayer();
        showAktieUpdate(cPlayer, aktie);

    }
    private void setHotel(Hotel hotel) {
        Player cPlayer = gameController.currentPlayer();
        showHotelUpdate(cPlayer, hotel);
    }

    private void showAktieUpdate(Player cPlayer, Aktien aktien) {
        int playerIdx = gameController.getPlayerIndex(cPlayer);
        if (playerIdx >= 0) {
            switch (aktien) {
                case HYPO:
                    gameController.setHypoAktie(playerIdx, cPlayer.getAktien()[0] + 1);
                    break;
                case STRABAG:
                    gameController.setStrabagAktie(playerIdx, cPlayer.getAktien()[1] + 1);
                    break;
                case INFINEON:
                    gameController.setInfineonAktie(playerIdx, cPlayer.getAktien()[2] + 1);
                    break;
                default:
                    throw new IllegalStateException("Aktie does not exist");
            }
        }
    }
    private void showHotelUpdate(Player cPlayer, Hotel hotel) {
        int playerIdx = gameController.getPlayerIndex(cPlayer);
        if (playerIdx >= 0) {
            switch (hotel) {
                case SANDWIRTH:
                    gameController.setSandwirtHotel(playerIdx, cPlayer.getHotel()[0] + 1);
                    break;
                case PLATTENWIRT:
                    gameController.setPlattenwirtHotel(playerIdx, cPlayer.getHotel()[1] + 1);
                    break;
                case SEEPARK:
                    gameController.setSeeparkHotel(playerIdx, cPlayer.getHotel()[2] + 1);
                    break;
                default:
                    throw new IllegalStateException("Hotel does not exist");
            }
        }
    }

    public void buyAktie(final Aktien aktie) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MapView.this);

        a_builder.setMessage("Wollen sie eine Aktie der Firma " + aktie + " kaufen?")
                .setCancelable(false)
                .setPositiveButton("JA!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAktie(aktie);
                    }
                })
                .setNegativeButton("NEIN!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameController.justEndTurn();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();


    }

    public void buyHotel(final Hotel hotel) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MapView.this);

        a_builder.setMessage("Wollen sie das Hotel " + hotel + " kaufen?")
                .setCancelable(false)
                .setPositiveButton("JA!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setHotel(hotel);
                        showMoneyUpdate(-150000);
                    }
                })
                .setNegativeButton("NEIN!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameController.justEndTurn();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();
    }
}
