package cool.gis.modernartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;


public class MainActivity extends ActionBarActivity {
    private RelativeLayout palette;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        palette = (RelativeLayout) findViewById( R.id.palette );
        SeekBar seek = ( SeekBar ) findViewById( R.id.seekBar );

        seek.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {

            /**
             * Notification that the seek bar progress level has changed.
             *
             * When the seek bar state changes, loop through the child's of the palette view and
             * change each individuals view color based on the percentage that the seek bar is
             * currently at. Ignores views that are either white or some shade of gray.
             *
             * @param seekBar  The SeekBar whose progress has changed
             * @param progress The current progress level. This will be in the range 0..100
             * @param fromUser True if the progress change was initiated by the user.
             */
            @Override
            public void onProgressChanged ( SeekBar seekBar, int progress, boolean fromUser ) {

                for ( int i = 0; i < palette.getChildCount(); i++ ) {
                    View child = palette.getChildAt( i );

                    int originalColor = Color.parseColor( ( String ) child.getTag() );
                    int invertedColor = ( 0x00FFFFFF - ( originalColor | 0xFF000000 ) ) |  ( originalColor & 0xFF000000 );

                    if ( getResources().getColor( R.color.white ) != originalColor &&
                            getResources().getColor( R.color.lightgray ) != originalColor ) {

                        int origR = ( originalColor >> 16 ) & 0x000000FF;
                        int origG = ( originalColor >> 8 ) & 0x000000FF;
                        int origB = originalColor & 0x000000FF;

                        int invR = ( invertedColor >> 16 ) & 0x000000FF;
                        int invG = ( invertedColor >> 8 ) & 0x000000FF;
                        int invB = invertedColor & 0x000000FF;

                        child.setBackgroundColor( Color.rgb(
                                (int) (origR + (invR - origR) * (progress / 100f)),
                                (int) (origG + (invG - origG) * (progress / 100f)),
                                (int) (origB + (invB - origB) * (progress / 100f))) );
                        child.invalidate();
                    }
                }
            }

            /**
             * Currently used for nothing.
             *
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStartTrackingTouch ( SeekBar seekBar ) {

            }

            /**
             * Currently used for nothing.
             *
             * @param seekBar The SeekBar in which the touch gesture began
             */
            @Override
            public void onStopTrackingTouch ( SeekBar seekBar ) {

            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Evaluates when the user clicks the more information options menu.
     * <p/>
     * Shows the more information dialog fragment.
     *
     * @param item The menu item that was clicked
     */
    public void showDialog ( MenuItem item ) {

        //new MoreInformationDialog().show( getFragmentManager(), "ModernArtUI" );
        new AlertDialog.Builder(this)
                .setTitle("ModernArtUI")
                .setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.dialog_visit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent visit = new Intent( Intent.ACTION_VIEW,
                                Uri.parse("http://www.moma.org") );
                        Intent chooser = Intent.createChooser( visit,
                                getResources().getString( R.string.open_with ) );
                        startActivity( chooser );
                    }
                })
                .setNegativeButton(R.string.dialog_not_now, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
