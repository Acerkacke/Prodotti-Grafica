import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import Cramest.Prodotti.*;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;


public class Supermercato {

	protected Shell shlSupermercato;
	private static Text text_prezzo;
	private static Text text_codice;
	private static ListaSpesa carrello;
	private static Button btnNo;
	private static Button btnSi;
	private static List list;
	private static ArrayList<Prodotto> inventario;
	private Text text_totale;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text text_scontrino;
	private boolean isUscito = false;
	
	public static void main(String[] args) {
		try {
			CaricaFileProdotti cf = new CaricaFileProdotti(System.getProperty("user.dir")+"\\salvataggioProdotti.txt");
			carrello = new ListaSpesa();
			inventario = cf.caricaListaProdotti();
			Supermercato window = new Supermercato();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSupermercato.open();
		shlSupermercato.layout();
		while (!shlSupermercato.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void aggiornaLista(){
		String[] listaAggiornata = new String[carrello.size()];
		for(int i=0;i<carrello.size();i++){
			listaAggiornata[i] = carrello.getProdotto(i).toString();
		}
		list.setItems(listaAggiornata);
		text_totale.setText(String.valueOf(Math.floor(carrello.calcolaTOT()*1000)/1000));
		
	}
	
	private void vaiAllaCassa(){
		shlSupermercato.setSize(shlSupermercato.getSize().x,400);
	}
	
	private void paga(boolean haTessera){
		if(!isUscito){
			shlSupermercato.setSize(shlSupermercato.getSize().x,450);
			if(haTessera)
				carrello.applicaSconti();
			text_scontrino.setText(String.valueOf(carrello.calcolaTOT()));
			isUscito = true;
		}
	}
	
	private void rimuoviDalCarrello(){
		list.remove(list.getSelectionIndex());
	}

	protected void createContents() {
		shlSupermercato = new Shell();
		shlSupermercato.setMinimumSize(new Point(460, 305));
		shlSupermercato.setSize(460, 310);
		shlSupermercato.setText("Supermercato");
		
		Label lblSupermercato = new Label(shlSupermercato, SWT.CENTER);
		lblSupermercato.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 13, SWT.BOLD));
		lblSupermercato.setAlignment(SWT.CENTER);
		lblSupermercato.setBounds(0, 0, 434, 23);
		lblSupermercato.setText("SUPERMERCATO");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shlSupermercato, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setTouchEnabled(true);
		scrolledComposite.setBounds(0, 29, 195, 170);
		
		list = new List(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(list);
		scrolledComposite.setMinSize(list.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Label lblNewLabel = new Label(shlSupermercato, SWT.NONE);
		lblNewLabel.setBounds(221, 26, 55, 21);
		lblNewLabel.setText("Prezzo");
		
		text_prezzo = new Text(shlSupermercato, SWT.BORDER);
		text_prezzo.setEditable(false);
		text_prezzo.setBounds(348, 23, 76, 21);
		
		Label lblCodice = new Label(shlSupermercato, SWT.NONE);
		lblCodice.setBounds(221, 53, 55, 15);
		lblCodice.setText("Codice");
		
		text_codice = new Text(shlSupermercato, SWT.BORDER);
		text_codice.setEditable(false);
		text_codice.setBounds(348, 50, 76, 21);
		
		Button btnTotale = new Button(shlSupermercato, SWT.NONE);
		btnTotale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				vaiAllaCassa();
			}
		});
		btnTotale.setBounds(221, 236, 203, 25);
		btnTotale.setText("Vai alla cassa");
		
		Label lblTotale = new Label(shlSupermercato, SWT.NONE);
		lblTotale.setBounds(10, 241, 55, 13);
		lblTotale.setText("Totale : ");
		
		text_totale = new Text(shlSupermercato, SWT.BORDER);
		text_totale.setEditable(false);
		text_totale.setBounds(71, 238, 124, 21);
		
		btnNo = new Button(shlSupermercato, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paga(false);
			}
		});
		btnNo.setBounds(120, 332, 75, 25);
		formToolkit.adapt(btnNo, true, true);
		btnNo.setText("No");
		
		btnSi = new Button(shlSupermercato, SWT.NONE);
		btnSi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paga(true);
			}
		});
		btnSi.setBounds(282, 332, 75, 25);
		formToolkit.adapt(btnSi, true, true);
		btnSi.setText("Si");
		
		Label lblHaiLaCarta = new Label(shlSupermercato, SWT.NONE);
		lblHaiLaCarta.setBounds(179, 300, 110, 15);
		formToolkit.adapt(lblHaiLaCarta, true, true);
		lblHaiLaCarta.setText("Hai la carta fedelt\u00E0?");
		
		Label lblAlloraFanno = new Label(shlSupermercato, SWT.NONE);
		lblAlloraFanno.setBounds(120, 383, 75, 15);
		formToolkit.adapt(lblAlloraFanno, true, true);
		lblAlloraFanno.setText("Allora fanno :");
		
		text_scontrino = new Text(shlSupermercato, SWT.BORDER);
		text_scontrino.setEditable(false);
		text_scontrino.setBounds(248, 380, 76, 21);
		formToolkit.adapt(text_scontrino, true, true);
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(shlSupermercato, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(221, 72, 213, 158);
		formToolkit.adapt(scrolledComposite_1);
		formToolkit.paintBordersFor(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite_1, SWT.NONE);
		scrolledComposite_1.setContent(composite);
		composite.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				for(int i=0; i<  inventario.size(); i++){
					Prodotto corrente = inventario.get(i);
					
					Button btnNewButton = new Button(composite, SWT.NONE);
					btnNewButton.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							carrello.aggiungiProdotto(corrente);
							aggiornaLista();
						}
					});
					btnNewButton.setBounds(0, i*25, 209, 25);
					formToolkit.adapt(btnNewButton, true, true);
					btnNewButton.setText(inventario.get(i).getDescr());
					scrolledComposite_1.setContent(composite);
					scrolledComposite_1.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
			}
		});
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		
		Button btnEliminaSelezionato = new Button(shlSupermercato, SWT.NONE);
		btnEliminaSelezionato.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rimuoviDalCarrello();
			}
		});
		btnEliminaSelezionato.setBounds(0, 205, 195, 25);
		formToolkit.adapt(btnEliminaSelezionato, true, true);
		btnEliminaSelezionato.setText("Elimina selezionato");

	}
}
