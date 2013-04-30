package br.ufghomework.control;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import br.ufghomework.R;

public class PrincipalMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal_menu, menu);
		return true;
	}

}
