package letsdecode.com.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;


public class EditActivity extends ActionBarActivity {
    EditText editItem_edittText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String textToBeEdited = getIntent().getStringExtra("selectedItemText");
        editItem_edittText = (EditText)findViewById(R.id.editText_editItem);
        editItem_edittText.setText(textToBeEdited);
        editItem_edittText.setSelection(editItem_edittText.getText().length());

    }


    public void submitMessage(View V)
    {
        // get the Entered  message
        String textFromEditText=editItem_edittText.getText().toString();
        Intent intentForItemListActivity=new Intent();

        // put the message in Intent
        intentForItemListActivity.putExtra("textFromEditText",textFromEditText);
        // Set The Result in Intent
        setResult(1,intentForItemListActivity);
        // finish The activity
        finish();
    }
}
