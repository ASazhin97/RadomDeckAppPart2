/*
Alexandra Sazhin
Random Deck App
Main Activity: Main class, only activity all of the fragments are "flipped through" in this activity
takes care of drawer and menu
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements SettingsFragment.Changable{
    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;
    private DatabaseHandler db;
    ArrayList<String> deck;
    ArrayList<String> card;
    public static int _fontID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database;
       db = new DatabaseHandler(this);


        titles=getResources().getStringArray(R.array.titles);
        drawerList=(ListView)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        //disp correct fragment
        if(savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }

        //drawer Toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer){
            public void onDrawerClose(View view){
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if(fragment instanceof TopFragment){
                            currentPosition = 0;
                        }
                        if(fragment instanceof RandomFragment){
                            currentPosition = 1;
                        }
                        if(fragment instanceof FavoritesFragment){
                            currentPosition = 2;
                        }if(fragment instanceof SettingsFragment){
                            currentPosition=3;
                        }

                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }

    //drawer methods
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    //ACTION BAR
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_view_favorites:
                selectItem(2);
                return true;
            case R.id.action_settings:
                selectItem(3);
                return true;
            case R.id.action_random_card:
                Log.e("pressed", "random card");
                selectItem(1);
                return true;
            case R.id.action_add_favorites:
                FragmentManager fragMan = getFragmentManager();
                Fragment fragmentV = fragMan.findFragmentByTag("visible_fragment");

                //adding favorite to database //
                if(fragmentV instanceof RandomFragment){
                    try{
                        db.open();
                        ArrayList<String> card = ((RandomFragment) fragmentV).getCard();
                        db.addCard(card.get(0), card.get(1), card.get(2));
                        db.close();

                    }catch(SQLException e){
                        e.printStackTrace();
                        Toast toast = Toast.makeText(this, "Database no", Toast.LENGTH_LONG);
                        toast.show();
                    }



                }
                return true;
                //deleting favorite //
            case R.id.action_del_favorite:
                FragmentManager FM = getFragmentManager();
                Fragment fragmentVis = FM.findFragmentByTag("visible_fragment");

                //delteting from database
                if(fragmentVis instanceof CardDetailFragment){
                    try {
                        db.open();
                        Card c = ((CardDetailFragment) fragmentVis).getCard();
                        db.removeCard(c.getName(), c.getColors(), c.getText());
                        db.close();

                    } catch (SQLException e){
                        e.printStackTrace();
                    }
                    selectItem(2);

                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //deals with what to do for each fragment selection
    private void selectItem(int position){
        currentPosition = position;
        Fragment fragment;
        switch (position){
            case 1:
                fragment= new RandomFragment();
                CardAsync cardGen = new CardAsync();
                cardGen.execute();

                try {
                    ArrayList<String> cardInfoList = cardGen.get();

                    Bundle args = new Bundle();
                    args.putStringArrayList("card" , cardInfoList);

                    fragment.setArguments(args);

                } catch (InterruptedException e){
                    e.printStackTrace();
                } catch (ExecutionException e){
                    e.printStackTrace();
                }
                break;
            case 2:
                fragment= new FavoritesFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        //set action bar title
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    private void setActionBarTitle(int position){
        String title;
        if(position == 0){
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }

        getActionBar().setTitle(title);
    }

    //method from interface
    @Override
    public void changeFont(int fontId) {
        _fontID = fontId;

    }

    //drawer listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //when item is clicked
            selectItem(position);
            setActionBarTitle(position);
        }

    }
}
