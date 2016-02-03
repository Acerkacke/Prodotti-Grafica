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
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;

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
	private static SalvaFileProdotti sfp;
	private static ScrolledComposite scrolledComposite_dx;
	private static Composite composite_dx;

	public static void main(String[] args) {
		try {
			// prendiamo la lista dei prodotti
			CaricaFileProdotti cf = new CaricaFileProdotti(
					System.getProperty("user.dir") + "\\salvataggioProdotti.txt");
			// e mettiamola nell'inventario
			inventario = cf.caricaListaProdotti();
			// carichiamo il carrello precedente se esiste
			caricaCarrello();
			Supermercato window = new Supermercato();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore nel caricamento prodotti");
		}
	}

	public static void caricaCarrello() {
		// creiamo il carrello
		carrello = new ListaSpesa();
		try {
			// se esiste il file prendiamo i prodotti al suo interno
			CaricaFileProdotti cf = new CaricaFileProdotti(
					System.getProperty("user.dir") + "\\salvataggioCarrello.txt");
			// e mettiamoli nel carrello
			carrello = new ListaSpesa(cf.caricaListaProdotti());
		} catch (Exception e) {
			// nel caso non esista il file
			System.out.println("Non esiste il file da caricare!");
		}
		// impostiamo la directory del salvataggio
		sfp = new SalvaFileProdotti(System.getProperty("user.dir") + "\\salvataggioCarrello.txt");
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSupermercato.open();
		shlSupermercato.layout();
		// dopo aver caricato il carrello e creato la lista grafica aggiorniamo
		// i prodotti
		aggiornaLista();
		// e creiamo i bottoni con i prodotti comprabili
		creaBottoni();
		while (!shlSupermercato.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void creaBottoni() {
		// per ogni prodotto nell'inventario
		for (int i = 0; i < inventario.size(); i++) {
			Prodotto corrente = inventario.get(i);
			// crea il bottone
			Button btnNewButton = new Button(composite_dx, SWT.NONE);
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// quando il bottone viene premuto aggiungi il prodotto al
					// carrello, aggiorna la lista e salva automaticamente
					carrello.aggiungiProdotto(corrente);
					aggiornaLista();
					salva();
				}
			});
			btnNewButton.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseHover(MouseEvent e) {
					//quando il mouse e' sopra il bottone metti la descrizione
					text_prezzo.setText(corrente.getDescr());

					text_codice.setText(corrente.getCod());

				}
			});
			btnNewButton.setBounds(0, i * 25, 209, 25);
			formToolkit.adapt(btnNewButton, true, true);
			btnNewButton.setText(inventario.get(i).getDescr());
			scrolledComposite_dx.setContent(composite_dx);
			scrolledComposite_dx.setMinSize(composite_dx.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
	}
	/**
	 * 	Ridisegna la lista in base al carrello
	 */
	private void aggiornaLista() {
		String[] listaAggiornata = new String[carrello.size()];
		for (int i = 0; i < carrello.size(); i++) {
			listaAggiornata[i] = carrello.getProdotto(i).toString();
		}
		list.setItems(listaAggiornata);
		text_totale.setText(String.valueOf(Math.floor(carrello.calcolaTOT() * 1000) / 1000));

	}
	/**
	 * Finisci il programma e paga
	 */
	private void vaiAllaCassa() {
		if(carrello.count() > 0){
			shlSupermercato.setSize(shlSupermercato.getSize().x, 400);
		}else{
			System.out.println("Non hai niente nel carrello!");
		}
	}
	/**
	 * 
	 * @param haTessera
	 * 		  Se ha la tessera fai lo sconto
	 */

	private void paga(boolean haTessera) {
		if (!isUscito) {
			shlSupermercato.setSize(shlSupermercato.getSize().x, 450);
			if (haTessera)
				carrello.applicaSconti();
			text_scontrino.setText(String.valueOf(carrello.calcolaTOT()));
			sfp.CancellaFile();
			isUscito = true;
		}
	}
	/**
	 * Cancella il file e ricrealo con le nuove cose
	 */
	private void salva() {
		sfp.CancellaFile();
		sfp.salvaListaSpesa(carrello);
	}
	/**
	 * 	Rimuove il prodotto selezionato sulla lista dal carrello
	 */
	private void rimuoviDalCarrello() {
		if(list.getSelectionIndex() >= 0  && list.getSelectionIndex() < carrello.count()){
		System.out.println(list.getSelectionIndex());
		carrello.eliminaProdotto(list.getSelectionIndex());
		list.remove(list.getSelectionIndex());
		aggiornaLista();
		salva();
		}else{
			System.out.println("Non hai selezionato un ca**o");
		}
	}

	protected void createContents() {
		shlSupermercato = new Shell();
		shlSupermercato.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		shlSupermercato.setMinimumSize(new Point(460, 305));
		shlSupermercato.setSize(460, 302);
		shlSupermercato.setText("Supermercato");

		Label lblSupermercato = new Label(shlSupermercato, SWT.BORDER | SWT.CENTER);
		lblSupermercato.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		lblSupermercato.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 13, SWT.BOLD));
		lblSupermercato.setAlignment(SWT.CENTER);
		lblSupermercato.setBounds(0, 0, 444, 23);
		lblSupermercato.setText("SUPERMERCATO");

		ScrolledComposite scrolledComposite = new ScrolledComposite(shlSupermercato, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setTouchEnabled(true);
		scrolledComposite.setBounds(0, 29, 195, 148);

		Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);

		list = new List(composite_1, SWT.BORDER);
		list.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		list.setLocation(0, 0);
		list.setSize(185, 156);
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblNewLabel = new Label(shlSupermercato, SWT.BORDER);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		lblNewLabel.setBounds(221, 31, 55, 15);
		lblNewLabel.setText("Prezzo");

		text_prezzo = new Text(shlSupermercato, SWT.BORDER);
		text_prezzo.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		text_prezzo.setEditable(false);
		text_prezzo.setBounds(300, 29, 124, 17);

		Label lblCodice = new Label(shlSupermercato, SWT.BORDER);
		lblCodice.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		lblCodice.setBounds(221, 52, 55, 16);
		lblCodice.setText("Codice");

		text_codice = new Text(shlSupermercato, SWT.BORDER);
		text_codice.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		text_codice.setEditable(false);
		text_codice.setBounds(300, 50, 124, 18);

		Button btnTotale = new Button(shlSupermercato, SWT.FLAT);
		btnTotale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				vaiAllaCassa();
			}
		});
		btnTotale.setBounds(221, 236, 203, 25);
		btnTotale.setText("Vai alla cassa");

		Label lblTotale = new Label(shlSupermercato, SWT.BORDER);
		lblTotale.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		lblTotale.setBounds(10, 241, 55, 15);
		lblTotale.setText("Totale : ");

		text_totale = new Text(shlSupermercato, SWT.BORDER);
		text_totale.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		text_totale.setEditable(false);
		text_totale.setBounds(71, 238, 124, 23);

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

		Label lblHaiLaCarta = new Label(shlSupermercato, SWT.BORDER | SWT.CENTER);
		lblHaiLaCarta.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblHaiLaCarta.setBounds(165, 300, 139, 15);
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

		scrolledComposite_dx = new ScrolledComposite(shlSupermercato, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_dx.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		scrolledComposite_dx.setBounds(221, 72, 213, 158);
		formToolkit.adapt(scrolledComposite_dx);
		formToolkit.paintBordersFor(scrolledComposite_dx);
		scrolledComposite_dx.setExpandHorizontal(true);
		scrolledComposite_dx.setExpandVertical(true);

		composite_dx = new Composite(scrolledComposite_dx, SWT.NONE);
		composite_dx.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		scrolledComposite_dx.setContent(composite_dx);
		formToolkit.adapt(composite_dx);
		formToolkit.paintBordersFor(composite_dx);

		Button btnEliminaSelezionato = new Button(shlSupermercato, SWT.NONE);
		btnEliminaSelezionato.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rimuoviDalCarrello();
			}
		});
		btnEliminaSelezionato.setBounds(10, 183, 185, 25);
		formToolkit.adapt(btnEliminaSelezionato, true, true);
		btnEliminaSelezionato.setText("Elimina selezione");

		Button buttonEliminaTutto = new Button(shlSupermercato, SWT.NONE);
		buttonEliminaTutto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				carrello.svuota();
				salva();
				aggiornaLista();
			}
		});
		buttonEliminaTutto.setText("Svuota lista");
		buttonEliminaTutto.setBounds(10, 210, 185, 25);
		formToolkit.adapt(buttonEliminaTutto, true, true);

	}
}
