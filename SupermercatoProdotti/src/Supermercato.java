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
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;


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
	
	public static void main(String[] args) {
		try {
			CaricaFileProdotti cf = new CaricaFileProdotti(System.getProperty("user.dir")+"\\salvataggioProdotti.txt");
			inventario = cf.caricaListaProdotti();
			caricaCarrello();
			Supermercato window = new Supermercato();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void caricaCarrello(){
		carrello = new ListaSpesa();
		try{
		CaricaFileProdotti cf = new CaricaFileProdotti(System.getProperty("user.dir")+"\\salvataggioCarrello.txt");
		carrello = new ListaSpesa(cf.caricaListaProdotti());
		}catch(Exception e){
			System.out.println("Non esiste il file da caricare!");
		}
		sfp = new SalvaFileProdotti(System.getProperty("user.dir")+"\\salvataggioCarrello.txt");
	}
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSupermercato.open();
		shlSupermercato.layout();
		aggiornaLista();
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
		System.out.println(list.getSelectionIndex());
		carrello.eliminaProdotto(list.getSelectionIndex());
		list.remove(list.getSelectionIndex());
		aggiornaLista();
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
		list.setSize(185, 166);
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
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(shlSupermercato, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		scrolledComposite_1.setBounds(221, 72, 213, 158);
		formToolkit.adapt(scrolledComposite_1);
		formToolkit.paintBordersFor(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite_1, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
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
					btnNewButton.addMouseTrackListener(new MouseTrackAdapter() {
						@Override
						public void mouseHover(MouseEvent e) {
							text_prezzo.setText(corrente.getDescr());;
							text_codice.setText(corrente.getCod());;
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
		btnEliminaSelezionato.setBounds(0, 183, 195, 25);
		formToolkit.adapt(btnEliminaSelezionato, true, true);
		btnEliminaSelezionato.setText("Elimina selezionato");
		
		Button btnSalva = new Button(shlSupermercato, SWT.NONE);
		btnSalva.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sfp.salvaListaSpesa(carrello);
			}
		});
		btnSalva.setBounds(0, 210, 195, 25);
		formToolkit.adapt(btnSalva, true, true);
		btnSalva.setText("Salva ");

	}
}
