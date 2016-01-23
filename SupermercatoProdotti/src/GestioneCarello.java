import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import Cramest.Prodotti.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;

public class GestioneCarello {

	protected Shell shlAggiungiprodotti;
	private Text text_Nome;
	private Text text_Prezzo;
	private Text text_Codice;
	private Text textCodiceNonAlimentare;
	private Text textPrezzoNonAlimentare;
	private Text textNomeNonAlimentare;
	private Text textMateriale;
	private static SalvaFileProdotti sfp;

	public static void main(String[] args) {
		sfp = new SalvaFileProdotti(System.getProperty("user.dir")+"\\salvataggioProdotti.txt");
		try {
			GestioneCarello window = new GestioneCarello();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAggiungiprodotti.open();
		shlAggiungiprodotti.layout();
		while (!shlAggiungiprodotti.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shlAggiungiprodotti = new Shell();
		shlAggiungiprodotti.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		shlAggiungiprodotti.setSize(450, 260);
		shlAggiungiprodotti.setText("AggiungiProdotti");
		
		Label lblAggiungiProdotto = new Label(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		lblAggiungiProdotto.setAlignment(SWT.CENTER);
		lblAggiungiProdotto.setBounds(146, 10, 133, 15);
		lblAggiungiProdotto.setText("AGGIUNGI PRODOTTO");
		
		Label lblNomeProdotto = new Label(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		lblNomeProdotto.setBounds(10, 59, 67, 15);
		lblNomeProdotto.setText("Nome:");
		
		Label lblPrezzo = new Label(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		lblPrezzo.setBounds(10, 90, 67, 15);
		lblPrezzo.setText("Prezzo:");
		
		Label lblCodice = new Label(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		lblCodice.setBounds(10, 123, 67, 15);
		lblCodice.setText("Codice:");
		
		text_Nome = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		text_Nome.setBounds(83, 59, 117, 15);
		
		text_Prezzo = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		text_Prezzo.setBounds(83, 88, 117, 17);
		
		text_Codice = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		text_Codice.setBounds(83, 123, 117, 15);
		
		DateTime dateTime = new DateTime(shlAggiungiprodotti, SWT.BORDER);
		dateTime.setBounds(83, 154, 117, 15);
		
		Button btnAggiungiAlimentare = new Button(shlAggiungiprodotti, SWT.CENTER);
		btnAggiungiAlimentare.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cod = text_Codice.getText();
				String desc = text_Nome.getText();
				if (text_Prezzo.getText().matches(".*\\d+.*")){
					double prezzo = Double.parseDouble(text_Prezzo.getText());
					Data scadenza = new Data(dateTime.getDay(),dateTime.getMonth(),dateTime.getYear());
					Alimentare ali = new Alimentare(cod,desc,prezzo,scadenza);
					System.out.println(ali);
					sfp.salvaAlimentare(ali);
				}else{
					System.out.println("IL PREZZO CHE HAI INSERITO NON E' UN NUMERO");
				}
			}
		});
		btnAggiungiAlimentare.setBounds(69, 187, 75, 25);
		btnAggiungiAlimentare.setText("AGGIUNGI");
		
		
		
		Label lblData = new Label(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		lblData.setBounds(10, 154, 67, 15);
		lblData.setText("Data:");
		
		Label lblAlimentare = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblAlimentare.setBounds(69, 38, 75, 15);
		lblAlimentare.setText("ALIMENTARE:");
		
		Label lblNonAlimentare = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblNonAlimentare.setBounds(282, 38, 104, 15);
		lblNonAlimentare.setText("NON ALIMENTARE:");
		
		Label lblNomeNonAlimentare = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblNomeNonAlimentare.setAlignment(SWT.CENTER);
		lblNomeNonAlimentare.setBounds(236, 59, 55, 15);
		lblNomeNonAlimentare.setText("Nome:");
		
		Label lblPrezzoNonAlimentare = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblPrezzoNonAlimentare.setAlignment(SWT.CENTER);
		lblPrezzoNonAlimentare.setBounds(236, 90, 55, 15);
		lblPrezzoNonAlimentare.setText("Prezzo:");
		
		Label lblCodiceNonAlimentare = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblCodiceNonAlimentare.setBounds(236, 124, 55, 15);
		lblCodiceNonAlimentare.setText("Codice:");
		
		Label lblMateriale = new Label(shlAggiungiprodotti, SWT.BORDER);
		lblMateriale.setBounds(236, 154, 55, 15);
		lblMateriale.setText("Materiale:");
		
		textCodiceNonAlimentare = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		textCodiceNonAlimentare.setBounds(297, 123, 117, 15);
		
		textPrezzoNonAlimentare = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		textPrezzoNonAlimentare.setBounds(297, 88, 117, 17);
		
		textNomeNonAlimentare = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		textNomeNonAlimentare.setBounds(297, 59, 117, 15);
		
		textMateriale = new Text(shlAggiungiprodotti, SWT.BORDER | SWT.CENTER);
		textMateriale.setBounds(297, 154, 117, 15);
		
		Button btnAggiungiNonAlimentare = new Button(shlAggiungiprodotti, SWT.CENTER);
		btnAggiungiNonAlimentare.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cod = textCodiceNonAlimentare.getText();
				String desc = textNomeNonAlimentare.getText();
				if (textPrezzoNonAlimentare.getText().matches(".*\\d+.*")){
					double prezzo = Double.parseDouble(textPrezzoNonAlimentare.getText());
					String materiale = textMateriale.getText();
					sfp.salvaNonAlimentare(new NonAlimentare(cod,desc,prezzo,materiale));
				}else{
					System.out.println("IL PREZZO CHE HAI INSERITO NON E' UN NUMERO");
				}
			}
		});
		btnAggiungiNonAlimentare.setText("AGGIUNGI");
		btnAggiungiNonAlimentare.setBounds(282, 187, 75, 25);

	}
}
