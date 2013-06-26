package com.appsolution.omegafi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.appsolution.layouts.RowInformation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class StatementsActivity extends OmegaFiActivity {

	private LinearLayout linearStatements;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statements);
		linearStatements=(LinearLayout)findViewById(R.id.linearStatements);
		this.completeStatements();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Statements");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeStatements(){
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		for (int i = 0; i < 16; i++) {
			RowInformation rowpdf=new RowInformation(this);
			rowpdf.setImageIconInfo(R.drawable.pdf_icon);
			rowpdf.setNameInfo("December 1, 2012");
			rowpdf.setVisibleArrow(true);
			rowpdf.setBorderBottom(true);
			rowpdf.setPaddingRow(padding, padding, padding, padding);
			rowpdf.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					openIntentPdf();
				}
			});
			linearStatements.addView(rowpdf);
		}
	}
	
	private void openIntentPdf(){
		copyReadAssets();
	}
	
	private void copyReadAssets()
    {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), "lorem.pdf");
        try
        {
            in = assetManager.open("pdf/lorem.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/lorem.pdf"),
                "application/pdf");

        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }


}
